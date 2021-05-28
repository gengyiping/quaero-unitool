<template>
  <div class="" style="background:#f2f2f2;height:100%">
        <!-- <form > -->
                 <!-- <h1>{{ iplocal }}</h1> -->
              <el-form ref="loginform"  label-width="80px" style="" v-if="loginshow">
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
                     <el-button type="primary" @click="login()">登录</el-button>
                     <!-- <el-button>取消</el-button> -->
              </el-form-item>
            </div>
             </el-form>
              <WebTest height="100%" width="100%" v-if="mainshow"/>
             <!-- <input type="text" id='connected' v-model="connectstate" > -->
              <!-- <input type="text" id='connected' value='false' > -->
  </div>
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
// import Qcitdebug from '@/view//Qcitdebug'
// import Qcitsoft from '@/view/soft/Qcitsoft'
// import Demo from '@/view/demo/divMove'
import Bus from './bus'
//import {getNetworkIp} from './ip'
 import WebTest from './webTest'

const addresso = require('address')
const localhost = address0.ip() || 'localhost'
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
       socketUrl: '127.0.0.1:8080',
       pageSate:'',
       encodeval:'',
       uid:'',
       connectstate:false,
       returnobj:'',
       loginshow:true,
       mainshow:false,
       iplocal:''
    }
  },
  components: {
     WebTest,
    },
  mounted() {
    alert("ip=")

    var that = this
     this.getUserIP(function (ip) { // 调用ip接口
            that.iplocal = String(ip)
            that.getIpPort('./../static/Text/Ip.text') //获取ip内容
            that.loaduser() //加载cookie中的用户信息
       })
var _this = this
        Bus.$on('connectstate',function(val){//连接成功直接跳转主页面
        debugger
              if(val==true){
                 _this.loginshow=false
                 _this.mainshow=true
                 _this.cacheUser() //user放入cookie
                 if(indexid==0){//点击登录时，保存用户在哪个界面
                     _this.stompClient.send('/app/page/0-#-#-#',{})
                 }else{//获取上一次登录的界面
                     _this.stompClient.send('/app/initstate')
                 }
              }
         })
  },
  methods: {
getUserIP (onNewIP) { // 获取ip地址

    let MyPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection
    let pc = new MyPeerConnection({iceServers: []})
    let noop = function () {}
    let localIPs = {}
    let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g
    function iterateIP (ip) {
        if (!localIPs[ip]) onNewIP(ip)
        localIPs[ip] = true
    }
    pc.createDataChannel('')
    pc.createOffer().then(function (sdp) {
        sdp.sdp.split('\n').forEach(function (line) {
        if (line.indexOf('candidate') < 0) return
        line.match(ipRegex).forEach(iterateIP)
        })
        pc.setLocalDescription(sdp, noop, noop)
    }).catch(function (reason) {
    // An error occurred, so handle the failure to connect
    })
    // seen for candidate events
    pc.onicecandidate = function (ice) {
        if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return
        ice.candidate.candidate.match(ipRegex).forEach(iterateIP)
    }
},

    getIpPort(filePath){
      
    // 创建一个新的xhr对象
    let xhr = null
    if (window.XMLHttpRequest) {
      xhr = new XMLHttpRequest()
    } else {
      // eslint-disable-next-line
      xhr = new ActiveXObject('Microsoft.XMLHTTP')
    }
    const okStatus = document.location.protocol === 'file' ? 0 : 200
    xhr.open('GET', filePath, false)
    xhr.overrideMimeType('text/html;charset=utf-8')
    xhr.send(null)
     var xfil=xhr.status === okStatus ? xhr.responseText : null
     this.socketUrl=xfil
     console.log("获取本地的ip.text文件"+xfil)
   },
    login(){
       this.loginSubmit(0)
    },
    loaduser(){
      var _this=this
       var data =this.$cookieStore.getCookie('user')
       
       if (data!== null && data!='') {//若不为空 直接连接后台
          var datas= data.split("-")
            _this.loginform.userName= datas[0]
             _this.loginform.password= datas[1]
            _this.loginform.projectName=datas[2]
            _this.loginSubmit(1)
        }
    },
    loginSubmit(indexid){
       
       var _this=this
       
      if(this.$store.state.stompClient==null){//没有连接创建连接
         const socket = new SockJS('http://'+this.socketUrl+'/webSocket') //初始化weosocket
         this.stompClient = Stomp.over(socket) //获取STOMP子协议的客户端对象
         this.$store.state.stompClient=this.stompClient
         this.config()// 配置stomp
         this.sub()//订阅地址
      }else{ //当连接不为空时比较用户，不同时先关闭连接创建连接，相同时忽略直接跳过直接登录界面
         var data =this.$cookieStore.getCookie('user')
         if(data==null||data==''){
             _this.stompClient=null
            _this.$store.state.stompClient=null
            _this.loginSubmit(0)
         }else{
          var datas= data.split("-")
         if( _this.loginform.userName!==datas[0] || _this.loginform.password!==datas[1] ||_this.loginform.projectName !==datas[2]){
            
           // _this.stompClient.disconnect();
            _this.stompClient=null
            _this.$store.state.stompClient=null
            _this.loginSubmit(0)
         }else if( _this.loginform.userName==datas[0] && _this.loginform.password==datas[1] && _this.loginform.projectName ==datas[2]){
           
            _this.loginshow=false
             _this.mainshow=true
              _this.cacheUser() //user放入cookie
              _this.stompClient.send('/app/initstate')
         }
       }
         
         
      }
     },
    sendLogin(){
      while(true){
        
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
       
       var _this=this
        this.createUser();
           var userinfo= {
            userName:this.loginform.userName,
            //passWord:this.loginform.password,
            passWord:this.$md5(this.loginform.password),
            projectName:this.loginform.projectName,
            ip:this.iplocal,
            initflag:1
          }
          
          
        this.stompClient.connect(userinfo, function (frame) {// 向服务器发起websocket连接
            _this.uid = frame.headers['user-name']
            _this.$store.state.uid=_this.uid
            console.log('-------连接成功------------'+_this.uid)
            console.log("获取本地iplocal:"+_this.iplocal)

           _this.stompClient.subscribe('/topic/status', function (data) {//// 订阅服务端提供的某个topic stompClient连接状态
            var obj = _this.getData(data.body)
            _this.handleMessage(obj);
            if(_this.returnobj.message.indexOf("登入界面登入") != -1){
               console.log('-------用户连接成功------------')
              // _this.$message('连接后台成功')
              //   _this.setConnected(true)
                 //Bus.$emit('connectstate',true)
                  _this.loginshow=false
                 _this.mainshow=true
                 _this.cacheUser() //user放入cookie
                 if(indexid==0){//点击登录时，保存用户在哪个界面
                     _this.stompClient.send('/app/page/0-#-#-#',{})
                 }else{//获取上一次登录的界面
                     _this.stompClient.send('/app/initstate')
                 } 
            }
          });
           _this.stompClient.subscribe('/topic/getResponse', (val) => {//初始话连接
               console.log('------初始话------------')
                _this.getData(val.body)
           });
          _this.stompClient.subscribe('/user/'+_this.iplocal+'/initInfo/alone/getResponse', (val) => {//连接信息
              _this.getData(val.body)
              _this.$store.state.initInfo=_this.returnobj.message
          });
           _this.stompClient.subscribe('/user/'+_this.iplocal+'/setbarCode/alone/getResponse', (val) => {//设置条码仪参数
              _this.getData(val.body)
          });
           
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/ifInit/alone/getResponse', (val) => { //获取是否初始化连接设备
          console.log(val.body)
               var obj = JSON.parse(val.body);
                 Bus.$emit('ifInit',obj.data.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/initstate/alone/getResponse', (val) => { //获取上一次页面状态
          console.log(val.body)
          
              var pagesindexs=val.body.split("-")
               _this.getData(val.body)
              _this.$store.state.rootPage=_this.returnobj.message
              _this.$store.state.firstPage=_this.returnobj.message
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/readloacl/alone/getResponse', (val) => {//读取本地Ip信息
              _this.getData(val.body)
             Bus.$emit('readloacl',_this.returnobj.message)
        });
          _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeloacl/alone/getResponse', (val) => {//写入本地Ip信息
             console.log(val.body)
              _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeEquipment/alone/getResponse', (val) => {//写入设备Ip信息
             console.log(val.body)
             _this.getData(val.body)
        });
          _this.stompClient.subscribe('/user/'+_this.iplocal+'/readEquipment/alone/getResponse', (val) => {//读取设备Ip信息
             console.log(val.body)
             _this.getData(val.body)
             Bus.$emit('readEquipment',_this.returnobj.message)
        });
          _this.stompClient.subscribe('/user/'+_this.iplocal+'/serverLog/getResponse', (val) => {//开启服务端日志
             console.log(val.body)
             _this.getData(val.body)
        });
          _this.stompClient.subscribe('/user/'+_this.iplocal+'/eventLog/getResponse', (val) => {//开启事件日志
             console.log(val.body)
             _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/oneToconf/getResponse', (val) => {//一键改配置
         
             console.log(val.body)
             _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/openscan/alone/getResponse', (val) => {//
             console.log(val.body)
             _this.getData(val.body)
              Bus.$emit('openscan',_this.returnobj.message)
        });
       
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/sensorSearch/alone/getResponse', function (val) {//查询所有传感器
            _this.getData(val.body)
            Bus.$emit('allsensor',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/reset/alone/getResponse', function (val) {//基本操作电机复位
            _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/basket/alone/getResponse', function (val) {//提篮在线
            _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/chip/alone/getResponse', function (val) {//获取芯片ID
              _this.getData(val.body)
              Bus.$emit('chipID',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/openTftp/alone/getResponse', function (val) {//开关tftp
            _this.getData(val.body)
            Bus.$emit('opentftp',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/openRemote/alone/getResponse', function (val) {//开关远程服务
           _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/transferMove/alone/getResponse', function (val) {//区域转移
             _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/openBoot/alone/getResponse', function (val) {//开启固件更新
             _this.getData(val.body)
        });
         _this.stompClient.subscribe(' /user/updateBoot/alone/getResponse', function (val) {//固件更新
             _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/firupload/alone/getResponse', function (val) {//监听上传文件
              _this.getData(val.body)
               Bus.$emit('firupload',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/deleteCoord/alone/getRespons', function (val) {//删除设备参数
              _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/loadCoord/alone/getRespons', function (val) {//上传参数到设备、下载参数到设备
              _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/readCoord/alone/getResponse', function (val) {//读取参数
              _this.getData(val.body)
                Bus.$emit('readCoord',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeCoord/alone/getResponse', function (val) {//保存定标参数
              _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/allmotor/alone/getResponse', function (val) {//查询所有电机
             _this.getData(val.body)
            Bus.$emit('allmotor',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/searchMotor/alone/getResponse', function (val) {//查询所有电机信息
            _this.getData(val.body)
            Bus.$emit('searchMotor',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/motorOpt/alone/getResponse', function (val) {//电机信息电机控制
         
              _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/motorcontrol/alone/getResponse', function (val) {//电机界面电机控制
            _this.getData(val.body)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/getCoord/alone/getResponse', function (val) {//获取当前坐标
           _this.getData(val.body)
             _this.$message("当前坐标："+JSON.parse(_this.returnobj.message))
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/steplost/alone/getResponse', function (val) {//获取失步数
             _this.getData(val.body)
              var resval=_this.returnobj.message.split(';')
                var newMsg=resval.join('<br/>')
                 _this.$message({
                 dangerouslyUseHTMLString: true,
                message: newMsg,
                 type: 'warning'
             });
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/motorFlag/alone/getResponse', function (val) {//获取电机传感器状态
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
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/sensorStop/alone/getResponse', function (val) {//根据传感器停
             _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/grooveMove/alone/getResponse', function (val) {//走计数凹槽
             _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/searchInlineopt/alone/getResponse', function (val) {//读取电机参数
            _this.getData(val.body)
                  Bus.$emit('searchInlineopt',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeInlineopt/alone/getResponse', function (val) {//写入电机参数
             _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/readCoordLimit/alone/getResponse', function (val) {//读取限制坐标
            console.log("----coordLimit----")
              _this.getData(val.body)
              Bus.$emit('phymax',_this.returnobj.message)
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeCoordLimit/alone/getResponse', function (val) {//写入限制坐标
            console.log("----coordLimit----")
              _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/readOutStepForm/alone/getResponse', function (val) {//读取失步数
            console.log("----outStepForm----")
               _this.getData(val.body)
                    Bus.$emit('outMove',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeOutStepForm/alone/getResponse', function (val) {//设置失步数
            console.log("----outStepForm----")
               _this.getData(val.body)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/readZeroOpt/alone/getResponse', function (val) {//读取零位
           
            console.log("----zeroOpt----")
             _this.getData(val.body)
                    Bus.$emit('zeroOpt',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeZeroOpt/alone/getResponse', function (val) {//设置零位
            console.log("----zeroOpt----")
             _this.getData(val.body)
             
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/createMotorFile/alone/getResponse', function (val) {//创建电机文件
            console.log("----createMotorFile----")
              _this.getData(val.body)
             
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/readSpeedAcc/alone/getResponse', function (val) {//读取速度、加速度
            console.log("----speedAcc----")
             _this.getData(val.body)
                    Bus.$emit('speedAccList',_this.returnobj.message)
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/writeSpeedAcc/alone/getResponse', function (val) {//写入速度、加速度
            console.log("----speedAcc----")
             _this.getData(val.body)
                 
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/upload/alone/getResponse', function (val) {//上传电机配置文件
                  _this.getData(val.body)
                 
        });
       _this.stompClient.subscribe('/user/'+_this.iplocal+'/testMoveone/alone/getResponse', function (val) {//测试流程
                   _this.getData(val.body)
                  Bus.$emit('testMoveone',_this.returnobj.message);
        });
         _this.stompClient.subscribe('/user/'+_this.iplocal+'/scanBarcode/alone/getResponse', function (val) {//监听扫码
                  
                   _this.getData(val.body)
                  Bus.$emit('scanBarcode',_this.returnobj.message);
        });
        _this.stompClient.subscribe('/user/'+_this.iplocal+'/initialUpdate/alone/getResponse', function (val) {//监听扫码
                   _this.getData(val.body)
                 // Bus.$emit('initialUpdate',_this.returnobj.message);
        });

          _this.stompClient.subscribe('/topic/chatRoom', function (val) {
            
            _this.handleMessage(getData(data.body));
            });
        
        // 本地订阅
          _this.stompClient.subscribe('/user/'+_this.iplocal+'/'+_this.uid+'/chat', function (data) {
           
              _this.handleMessage(getData(data.body));
          });

        // 错误信息订阅
           _this.stompClient.subscribe('/user/'+_this.iplocal+'/'+_this.uid+'/error', function (data) {
            _this.getData(data.body);

          });
        }, function (error) {
           console.log('-------请重新连接！------------')
            var userinfo= {
            userName:_this.loginform.userName,
            //passWord:_this.loginform.password,
            passWord:_this.$md5(_this.loginform.password),
            projectName:_this.loginform.projectName,
            ip:_this.iplocal,
            initflag:2
          }
           _this.reconnect(_this.socketUrl,userinfo, _this.successCallback)
           //_this.refresh()
        })
        // this.sendLogin()
     },
      reconnect(socketUrl,userinfo, successCallback) {
      console.info('in reconnect function')
      let connected = false
      const reconInv = setInterval(() => {
        console.info('in interval' + Math.random())
        this.socket = new SockJS(socketUrl)
        this.stompClient = Stomp.over(this.socket)
         this.$store.state.stompClient=this.stompClient
        this.stompClient.connect(userinfo, (frame) => {
          console.info('reconnected success')
          // 连接成功，清除定时器
          clearInterval(reconInv)
          connected = true
          successCallback()
        }, () => {
          console.info('reconnect failed')
          console.info('connected:' + connected)
          if (connected) {
            console.info('connect .... what?')
          }
        })
      }, 3000)
    },
     getData(data) {
        var obj = JSON.parse(data);
        this.codeMapping(obj);
        this.returnobj=obj.data
        // this.$store.state.resinfo=this.returnobj.message
          Bus.$emit('progres',false) 
     },
    codeMapping(date) {//响应码映射
     
    switch (date.code) {
        case 200:
          this.$message({
          message:date.data.message,
          type: 'success'
          });
           this.$store.state.resinfo=date.desc
            break;
        case 404:
            alert("404");
            break;
        default:
           this.$store.state.resinfo=date.desc
           // alert(date.desc);
             this.$message.error(date.desc);
            break;
    }
    },
    handleMessage(data) {
      
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
      
          if(this.loginform.userName!==''&&this.loginform.password!==''&&this.loginform.projectName!==''){
            this.user =this.loginform.userName+"-"+this.loginform.password+"-"+this.loginform.projectName
          }
     },
     cacheUser() {
       
        var _this=this
       _this.$cookieStore.setCookie('user', _this.user)
        console.log('-------++++++++++++++++++++++++++++++------------')
         
      },
     setConnected(connected) {//设置连接状态
        // this.connectstate=connected
          Bus.$emit('connectstate',connected)
         //this.$store.state.connectstate=connected
         //
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