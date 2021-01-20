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
         this.sendLogin() //发送登录信息到后端
     },
    sendLogin(){
      debugger
       this.stompClient.send('/app/loginload')
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
            console.log('-------连接成功------------')
           _this.stompClient.subscribe('/topic/getResponse', (val) => {
             // this.list1 = JSON.parse(val.body)
               console.log('-------++++++++++++++++++++++++++++++------------')
                         //下面会报错，应该是json的问题，但是数据可以接收到
                console.log(val.body)
           });

           _this.cacheUser() //user放入cookie
           _this.setConnected(true) //连接状态
        }, function (error) {
           console.log('-------请重新连接！------------')
           _this.refresh()
        });
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
         console.log('-------++++++++++++++++++++++++++++++------------')
      },
     setConnected(connected) {//设置连接状态
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
    }
  },
  watch:{
    stompinit(newVal,oldVal){
      this.stompClient=newVal
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
    vertical-align: middle;
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