<template>
  <div class="" style="background:#f2f2f2;height:100%">
        <!-- <form > -->
              <el-form ref="loginform"  label-width="80px" style="">
            <div class="login_avatar dropdown" style="margin-top: 50px;">
                <img src="../../static/images/0.jpeg" id="avatarList" data-toggle="dropdown"/>
                <div class="dropdown-menu avatar_list_div" aria-labelledby="avatarList">
                </div>
            </div>
            <div style="width:350px;margin:0 auto;">
               <el-form-item label="账户">
                    <el-input  placeholder="请输入账户名" v-model="loginform.userName"></el-input>
                </el-form-item>
               <el-form-item label="密码">
                     <el-input placeholder="请输入密码" v-model="loginform.password" show-password></el-input>
               </el-form-item>
               <el-form-item label="项目">
                  <el-select v-model="loginform.projectName" placeholder="请选择项目">
                      <el-option label="SmallQCIT" value="SmallQCIT"></el-option>
                  </el-select>
              </el-form-item>
              <el-form-item>
                <!-- <router-link to='webTest'>
                       <el-button type="primary" >登录</el-button>
               </router-link> -->
                     <el-button type="primary" @click="loginSubmit">登录</el-button>
                     <!-- <el-button>取消</el-button> -->
              </el-form-item>
            </div>
             </el-form>
             <!-- <input type="text" id='connected' v-model="connectstate" > -->
              <!-- <input type="text" id='connected' value='false' > -->
  </div>
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Qcitdebug from '@/view/debugger/Qcitdebug'
import Qcitsoft from '@/view/soft/Qcitsoft'
import Demo from '@/view/demo/divMove'
import Bus from './bus'
import {getNetworkIp} from './ip'
import WebTest from './webTest'

export default {
  data() {
    return {
      loginform:{
        userName:'',
        password:'',
        projectName:''
      },
      user:'',
      password:'',
      tabPosition: 'left',
      list1: [],
      stompClient: null,
      initshow:true,
      closeshow:false,
      allmotor:null,
       centerDialogVisible: false,
       coord:null,
       ip:'',
       activeName: '0',
       pagestate:'',
       socketUrl: 'http://127.0.0.1:8080/webSocket',
       pageSate:'',
       encodeval:'',
       uid:'',
       connectstate:false,
       returnobj:''
    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo,
     
    },

  mounted() {
     this.loaduser()
    //this.init()//初始化连接
    var that = this;
    Bus.$off('loadinit')
    Bus.$on('loadinit', function (msg) {
      debugger
        console.log(msg);
        that.loginSubmit();
    })
  },
  methods: {
    
    //  loginSubmit(){//router的index.js中要配置
    //     this.$cookieStore.setCookie('name', 'aaaa')
    //     this.$router.push({ path:'/webTest'})
    //     var ssm=this.$cookieStore.getCookie( 'name')
    //      console.log('---用户名---'+ssm)
    //  },
    loaduser(){
      //this.$cookieStore.delCookie('user')
debugger
      var _this=this
       var data =this.$cookieStore.getCookie('user')
       if (data!== null) {
          var datas= data.split("-")
          _this.loginform.userName= datas[0]
             _this.loginform.password= datas[1]
            _this.loginform.projectName=datas[2]
        }
        // this.getUser()
        //   if(this.user.username!==undefined){//cookie中有user填充输入框
            
        //      loginform.username= this.user.username
        //      loginform.password= this.user.password
        //      loginform.projectName=this.user.projectName
        //   }
    },
    loginSubmit(){//
        const socket = new SockJS('http://127.0.0.1:8080/webSocket')
         this.stompClient = Stomp.over(socket)
         this.$store.state.stompClient=this.stompClient
         this.config()// 配置stomp
         this.sub()//订阅地址
     },
    sendLogin(){
      while(true){
        debugger
        //  var  connectval=document.getElementById("connected").defaultValue
          var connectval=this.connectstate
        if(connectval===false | connectval===''){
           this.waitsl(200)
        }else{
           this.waitsl(500)
          break
        }
      }
      console.log('---connectval--'+connectval)
      var data = {
        "message": "11111"
      } 
      debugger
       this.stompClient.send('/app/loginload')
    },
    waitsl(delay) {
       var start = (new Date()).getTime();
        while((new Date()).getTime() - start < delay) {
          continue;
       }
    },
    config() {
        // 每隔30秒做一次心跳检测
         this.stompClient.heartbeat.outgoing = 30000;
        // 客户端不接收服务器的心跳检测
         this.stompClient.heartbeat.incoming = 0;
     },
     sub(){
       debugger
       var _this=this
        this.createUser();
           var userinfo= {
            userName:this.loginform.userName,
            passWord:this.loginform.password,
            projectName:this.loginform.projectName,
            page:'0_#_#_#'

          };
        this.stompClient.connect(userinfo, function (frame) {
          debugger
            _this.uid = frame.headers['user-name']
            _this.$store.state.uid=_this.uid
            console.log('-------连接成功------------')

           _this.stompClient.subscribe('/topic/status', function (data) {//stompClient连接状态
            var obj = _this.getData(data.body)
            _this.handleMessage(obj);
            if(_this.returnobj.message.indexOf("登入界面登入") != -1){
              _this.$router.push({ path:'/webTest'})
              _this.cacheUser() //user放入cookie
            }
          });
           _this.stompClient.subscribe('/topic/getResponse', (val) => {//初始话连接
               console.log('------初始话------------')
                _this.getData(val.body)
                _this.$message('success:' + _this.returnobj.message)
           });
          _this.stompClient.subscribe('/user/initInfo/alone/getResponse', (val) => {//连接信息
              _this.getData(val.body)
              _this.$store.state.initInfo=_this.returnobj.message
          });
         _this.stompClient.subscribe('/user/readloacl/alone/getResponse', (val) => {//读取本地Ip信息
              _this.getData(val.body)
             Bus.$emit('readloacl',_this.returnobj.message)
        });
          _this.stompClient.subscribe('/user/writeloacl/alone/getResponse', (val) => {//写入本地Ip信息
             console.log(val.body)
              _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/writeEquipment/alone/getResponse', (val) => {//写入设备Ip信息
             console.log(val.body)
             _this.getData(val.body)
             _this.$message('success:' + _this.returnobj.message)
        });
          _this.stompClient.subscribe('/user/readEquipment/alone/getResponse', (val) => {//读取设备Ip信息
             console.log(val.body)
             _this.getData(val.body)
             Bus.$emit('readEquipment',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/sensorSearch/alone/getResponse', function (val) {//查询所有传感器
            _this.getData(val.body)
            Bus.$emit('allsensor',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/reset/alone/getResponse', function (val) {//基本操作电机复位
            _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
            NProgress.done();
        });
         _this.stompClient.subscribe('/user/basket/alone/getResponse', function (val) {//提篮在线
            _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/chip/alone/getResponse', function (val) {//获取芯片ID
              _this.getData(val.body)
              Bus.$emit('chipID',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/openTftp/alone/getResponse', function (val) {//开关tftp
            _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
            Bus.$emit('opentftp',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/openRemote/alone/getResponse', function (val) {//开关远程服务
        debugger
           _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/transferMove/alone/getResponse', function (val) {//区域转移
             _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/openBoot/alone/getResponse', function (val) {//开启固件更新
             _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe(' /user/updateBoot/alone/getResponse', function (val) {//固件更新
             _this.getData(val.body)
            _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/firupload/alone/getResponse', function (val) {//监听上传文件
              _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/deleteCoord/alone/getRespons', function (val) {//删除设备参数
              _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/loadCoord/alone/getRespons', function (val) {//上传参数到设备、下载参数到设备
              _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/readCoord/alone/getResponse', function (val) {//读取参数
              _this.getData(val.body)
                Bus.$emit('readCoord',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/writeCoord/alone/getResponse', function (val) {//保存定标参数
              _this.getData(val.body)
             _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/allmotor/alone/getResponse', function (val) {//查询所有电机
             _this.getData(val.body)
            Bus.$emit('allmotor',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/searchMotor/alone/getResponse', function (val) {//查询所有电机信息
            _this.getData(val.body)
            Bus.$emit('searchMotor',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/motorOpt/alone/getResponse', function (val) {//电机信息电机控制
         debugger
              _this.getData(val.body)
             _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/motorcontrol/alone/getResponse', function (val) {//电机界面电机控制
            _this.getData(val.body)
             _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/getCoord/alone/getResponse', function (val) {//获取当前坐标
           _this.getData(val.body)
             _this.$message("当前坐标："+JSON.parse(_this.returnobj.message))
        });
         _this.stompClient.subscribe('/user/steplost/alone/getResponse', function (val) {//获取失步数
             _this.getData(val.body)
              var resval=_this.returnobj.message.split(';')
                var newMsg=resval.join('<br/>')
                 _this.$message({
                 dangerouslyUseHTMLString: true,
                message: newMsg,
                 type: 'warning'
             });
        });
        _this.stompClient.subscribe('/user/motorFlag/alone/getResponse', function (val) {//获取电机传感器状态
           _this.getData(val.body)
           //   let reg=new RegExp(';','g')//g代表全部
             var resval=_this.returnobj.message.split(';')
                var newMsg=resval.join('<br/>')
                 _this.$message({
                 dangerouslyUseHTMLString: true,
                message: newMsg,
                 type: 'warning'
             });
                  //_this.$message(newMsg)
        });
         _this.stompClient.subscribe('/user/sensorStop/alone/getResponse', function (val) {//根据传感器停
             _this.getData(val.body)
               _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (val) {//走计数凹槽
             _this.getData(val.body)
               _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/searchInlineopt/alone/getResponse', function (val) {//读取电机参数
            _this.getData(val.body)
                  Bus.$emit('searchInlineopt',_this.returnobj.message)
               //  _this.centerDialogVisible =true
        });
         _this.stompClient.subscribe('/user/writeInlineopt/alone/getResponse', function (val) {//写入电机参数
             _this.getData(val.body)
               _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/readCoordLimit/alone/getResponse', function (val) {//读取限制坐标
            console.log("----coordLimit----")
           
              _this.getData(val.body)
              Bus.$emit('phymax',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/writeCoordLimit/alone/getResponse', function (val) {//写入限制坐标
            console.log("----coordLimit----")
              _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/readOutStepForm/alone/getResponse', function (val) {//读取失步数
            console.log("----outStepForm----")
               _this.getData(val.body)
                    Bus.$emit('outMove',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/writeOutStepForm/alone/getResponse', function (val) {//设置失步数
            console.log("----outStepForm----")
               _this.getData(val.body)
                 _this.$message('success:' + _this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/readZeroOpt/alone/getResponse', function (val) {
           debugger
            console.log("----zeroOpt----")
             _this.getData(val.body)
                    Bus.$emit('zeroOpt',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/writeZeroOpt/alone/getResponse', function (val) {
            console.log("----zeroOpt----")
             _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/createMotorFile/alone/getResponse', function (val) {
            console.log("----createMotorFile----")
              _this.getData(val.body)
              _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/readSpeedAcc/alone/getResponse', function (val) {
            console.log("----speedAcc----")
             _this.getData(val.body)
                    Bus.$emit('speedAccList',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/writeSpeedAcc/alone/getResponse', function (val) {
            console.log("----speedAcc----")
             _this.getData(val.body)
                  _this.$message('success:' + _this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/upload/alone/getResponse', function (val) {
                  _this.getData(val.body)
                  _this.$message('success:' + _this.returnobj.message)
        });
       _this.stompClient.subscribe('/user/testMoveone/alone/getResponse', function (val) {//测试流程
                   _this.getData(val.body)
                  Bus.$emit('testMoveone',_this.returnobj.message);
        });
         _this.stompClient.subscribe('/user/scanBarcode/alone/getResponse', function (val) {//监听扫码
                   _this.getData(val.body)
                  Bus.$emit('scanBarcode',_this.returnobj.message);
        });

          _this.stompClient.subscribe('/topic/chatRoom', function (val) {
            DEBUGGER
            _this.handleMessage(getData(data.body));
            });
        
        // 本地订阅
          _this.stompClient.subscribe('/user/' + _this.uid + '/chat', function (data) {
           DEBUGGER
              _this.handleMessage(getData(data.body));
          });

        // 错误信息订阅
           _this.stompClient.subscribe('/user/' + _this.uid + '/error', function (data) {
             debugger
            _this.getData(data.body);
          });

         
           
        //  _this.setConnected(true)
        }, function (error) {
           console.log('-------请重新连接！------------')
           //_this.refresh()
        })
        // this.sendLogin()
     },
     getData(data) {
        var obj = JSON.parse(data);
        this.codeMapping(obj);
        this.returnobj=obj.data
     },
    codeMapping(date) {//响应码映射
     debugger
    switch (date.code) {
        case 200:
            break;
        case 404:
            alert("404");
            break;
        default:
            alert(date.desc);
            break;
    }
    },
    handleMessage(data) {
      debugger
    //var msg = data.message;
    var data=this.returnobj
    var msg =data.message
    switch (data.type) {
        case 'USER':
            this.showUserMsg(data);//显示用户消息
            break;
        case 'SYSTEM':  
            this.showSystemMsg(msg);//系统消息
            break;
        // case 'REVOKE':
        //     showRevokeMsg(data);
        //     break;
        // case 'ROBOT':
        //     showRobotMsg(data);
            break;
        default:
            break;
    }
    // 消息通知
   // msgNotice(data);
},
showUserMsg(data){

},
showSystemMsg(){

},
     refresh(){
        this.init()
     },
     createUser(){//创建用户
      debugger
          if(this.loginform.userName!==''&&this.loginform.password!==''&&this.loginform.projectName!==''){
            this.user =this.loginform.userName+"-"+this.loginform.password+"-"+this.loginform.projectName
          }
     },
     cacheUser() {
       debugger
        var _this=this
       _this.$cookieStore.setCookie('user', _this.user)
        console.log('-------++++++++++++++++++++++++++++++------------')
         
      },
     setConnected(connected) {//设置连接状态
         if(connected==true){
             this.stompClient.send('/app/loginload')
         }
         //this.$store.state.connectstate=connected
         //this.connectstate=connected
        // document.getElementById("connected").defaultValue=connected
        // document.getElementById("conflag").innerHTML += "<input id='connected' value='"+connected+"'>"
       // this.sendLogin()
     },
      htmlEncode(str) {//转义 防止html注入
        this.encodeval=''
        var ele = document.createElement("span");
         ele.appendChild(document.createTextNode(str));
        this.encodeval=ele.innerHTML;
      }

    
  },
 computed: {
    stompinit(){
       return this.$store.state.stompClient
    },
     stompuid(){
       return this.$store.state.uid
     }
  },
  watch:{
    stompinit(newVal,oldVal){
      this.stompClient=newVal
    },
     stompuid(newVal,oldVal){
       this.uid=this.$store.state.uid
     }
  },
}
</script>
<style rel="stylesheet/scss" lang="scss">
.login_avatar {
    cursor: pointer;
}
.dropdown {
    position: relative;
}
#avatarList{
 border-radius: 50%;
    width: 80px;
    height: 80px;
    box-shadow: 0 0 5px #999;
}
.form-control {
    display: block;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.428571429;
    color: #555;
    //vertical-align: middle;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
}
#username {
    width: 180px;
    margin: 10px auto;
}
.btn-primary {
    color: #fff;
    background-color: #428bca;
    border-color: #357ebd;
}
.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: normal;
    line-height: 1.428571429;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    user-select: none;
}
.glyphicon:empty {
    width: 1em;
}
.glyphicon {
    position: relative;
    top: 1px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    -webkit-font-smoothing: antialiased;
    font-style: normal;
    font-weight: normal;
    line-height: 1;
    -moz-osx-font-smoothing: grayscale;
}
.btn-primary {
    color: #fff;
    background-color: #428bca;
    border-color: #357ebd;
}
button, select {
    text-transform: none;
}
button, input, select, textarea {
    margin: 0;
    font-family: inherit;
    font-size: 100%;
}
button {
    text-rendering: auto;
    color: -internal-light-dark(black, white);
    letter-spacing: normal;
    word-spacing: normal;
    text-transform: none;
    text-indent: 0px;
    text-shadow: none;
    text-align: center;
    cursor: default;
    font: 400 13.3333px Arial;
}
.login_fm {
    width: 100%;
    height: 100%;
    text-align: center;
    margin-top: 50px;
}

.tabBox{
  margin-top: 20px;
  .el-tabs--left{
    height: auto !important;
    .el-tabs__item{
      width: 60px;
      line-height: 30px;
      height: auto;
      word-wrap: break-word;
      white-space: pre-line;
      margin-bottom: 25px;
    };
  
  };
};
.el-tab-pane{
margin-top:0px !important;
}
.el-row {
    margin-bottom: 20px;
    margin-top: -23px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

</style>