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
                    <el-input></el-input>
                </el-form-item>
               <el-form-item label="密码">
                     <el-input placeholder="请输入密码" v-model="loginform.password" show-password></el-input>
               </el-form-item>
               <el-form-item label="项目">
                  <el-select v-model="loginform.project" placeholder="请选择项目">
                      <el-option label="SmallQCIT" value="1"></el-option>
                  </el-select>
              </el-form-item>
              <el-form-item>
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
        password:'',
        project:''
      },
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
       pageSate:''

    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo,
     
    },

  mounted() {
   //  this.ready() //外网ip
    this.initWebSocket()
     this.getUserIP()
    
    this.$store.state.stompClient=this.stompClient
      //this.ip=this.$store.state.ip
  },
  methods: {
    init(){
       console.log('init')
      this.stompClient.send('/app/init')
      this.initshow=false
      this.closeshow=true
    },
    initstate(){
    //  this.ip=document.getElementById("iploc").defaultValue
       console.log('initstate'+this.ip)
        console.log('initstate'+localStorage.getItem('ip_addr'))
       this.stompClient.send('/app/initstate/'+this.ip,{})
    },
     ready:function(){
                //获取本地IP地址
                //js 引入 <!-- 获取本机ip  -->
                //<script src="http://pv.sohu.com/cityjson?ie=utf-8">
            var cip = returnCitySN["cip"];
        },
      getUserIP(){
      var RTCPeerConnection = window.RTCPeerConnection || window.webkitRTCPeerConnection || window.mozRTCPeerConnection;
      if (RTCPeerConnection) (()=>{
      var rtc = new RTCPeerConnection()
      rtc.createDataChannel(''); //创建一个可以发送任意数据的数据通道
      rtc.createOffer( offerDesc => { //创建并存储一个sdp数据 
      rtc.setLocalDescription(offerDesc)
       }, e => { console.log(e)})
       rtc.onicecandidate =(evt) => { //监听candidate事件
       if (evt.candidate) {
        var ip_addr = evt.candidate.address
         //let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g
         //var ips=ipRegex.exec(evt.candidate.candidate)
        localStorage.setItem('ip_addr',ip_addr)
        var xip=localStorage.getItem('ip_addr')
       // this.$store.state.ip=ip_addr
       this.ip=ip_addr
        console.log('ip='+this.ip)
         var _this = this;// `这一步很重要`
        setTimeout(function() { //防止报连接yet
          _this.initstate()//获取上一次连接状态
        }, 2000);
        console.log("目前仅测试了chrome浏览器OK"+ip_addr)
       }}
        })()
      else{console.log("目前仅测试了chrome浏览器OK")}
     },
      handleClick(tab, event) {
        debugger
        console.log(tab, event)
        var page='0_'+tab.name+'_#_#'
        this.stompClient.send('/app/page/'+this.ip+'/'+page,{})
        localStorage.setItem('page',page)
      },
      
    // allReset(){
    //    console.log('allReset')
    //   this.stompClient.send('/app/allreset')
    // },
    // senMessage() {
    //   console.log('senMessage')
    //   this.stompClient.send('/app/test')
    // },
    initWebSocket() {
       console.log('initWebSocket')
    //  this.connection()
      // 需要有一个失败重连得到问题
      this.socket = new SockJS(this.socketUrl)// 连接服务端
      this.stompClient = Stomp.over(this.socket)
      this.stompClient.connect({}, (frame) => {
        this.successCallback()
      }, () => {
        this.reconnect(this.socketUrl, this.successCallback)
      })
    },
    // 断开重连使用定时器定时连接服务器
    reconnect(socketUrl, successCallback) {
      console.info('in reconnect function')
      let connected = false
      const reconInv = setInterval(() => {
        console.info('in interval' + Math.random())
        this.socket = new SockJS(socketUrl)
        this.stompClient = Stomp.over(this.socket)
        this.stompClient.connect({}, (frame) => {
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
      }, 2000)
    },
    // 接收到消息并对消息做处理
    onMessageReceived(payload) {
      var message = JSON.parse(payload.body)
      console.info('Message', message)
    },
    // 连接成功
    successCallback() {
       var _this = this;// `这一步很重要`
      console.info('onConnected')
      // this.stompClient.subscribe('/topic/topicid', this.onMessageReceived)
      // this.stompClient.send('/app/msg',
      //   {},
      //   JSON.stringify({ sender: 'sender', type: 'JOIN' })
      // )
        this.stompClient.subscribe('/topic/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          console.log('-------++++++++++++++++++++++++++++++------------')
//下面会报错，应该是json的问题，但是数据可以接收到
          console.log(val.body)
        });
        this.stompClient.subscribe('/user/initstate/alone/getResponse', (val) => { //获取上一次页面状态
          // this.list1 = JSON.parse(val.body)
          console.log(val.body)
          debugger
          //if(this.pagestate!=val.body){
              var pagesindexs=val.body.split("_")
              this.$store.state.rootPage=val.body
              this.$store.state.firstPage=val.body
              // this.$store.state.secondPage=pagesindexs[2],
              // this.$store.state.thirdPage=pagesindexs[3]
          //}
        });
    }
  },
  computed: {
    firstPage(){
         debugger
        return  this.$store.state.firstPage;
       },  
  },
  watch:{
    firstPage(newVal,oldVal){
      debugger
        console.log("newVal="+newVal)
         console.log("oldVal="+oldVal)
             if(newVal!==oldVal){
          var pagesindexs=newVal.split("_")
           this.activeName=pagesindexs[1]
           this.$store.state.secondPage=newVal
          }
    },
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