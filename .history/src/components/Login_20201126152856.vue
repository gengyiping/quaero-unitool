<template>
  <div class="" style="background:#f2f2f2">
        <form>
            <div class="login_avatar dropdown" >
                <img src="../../static/images/0.jpeg" id="avatarList" data-toggle="dropdown"/>
                <div class="dropdown-menu avatar_list_div" aria-labelledby="avatarList">
                </div>
            </div>
            <div class="login-name"></div>
            <input type="text" id="username" placeholder="请在此处输入您的昵称！" class="form-control">
            <button type="button" class="btn btn-primary" id="joinChat" data-loading-text="连接中...">登入&nbsp;<i
                    class="glyphicon glyphicon-log-in"></i></button>
            &nbsp;<button type="button" class="btn btn-primary" id="logout" style="display: none">注销&nbsp;
            <i class="glyphicon glyphicon-log-out"></i></button>
        </form>
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
        this.stompClient.subscribe('/user/page/alone/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          console.log(val.body)
        });
        
         this.stompClient.subscribe('/user/allmotor/alone/getResponse', function (res) {
            console.log("----allmotor----")
            _this.allmotor = res.body
            Bus.$emit('allmotor',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
        
         this.stompClient.subscribe('/user/chip/alone/getResponse', function (res) {//获取芯片ID
              _this.allmotor = res.body
              Bus.$emit('chipID',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/openTftp/alone/getResponse', function (res) {//开关tftp
            _this.allmotor = res.body
            Bus.$emit('opentftp',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/openRemote/alone/getResponse', function (res) {//开关远程服务
            _this.allmotor = res.body
            _this.coord= _this.allmotor;
            _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/sensorSearch/alone/getResponse', function (res) {//查询所有传感器
            _this.allmotor = res.body
            Bus.$emit('allsensor',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/readCoord/alone/getResponse', function (res) {//读取定标参数
            _this.allmotor = res.body
            Bus.$emit('readCoord',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/writeCoord/alone/getResponse', function (res) {//保存定标参数
            _this.allmotor = res.body
             _this.coord= _this.allmotor;
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/searchMotor/alone/getResponse', function (res) {//查询所有电机信息
            _this.allmotor = res.body
            Bus.$emit('searchMotor',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/motorOpt/alone/getResponse', function (res) {//查询所有电机信息控制
            _this.allmotor = res.body
            _this.coord= _this.allmotor
              _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/transferMove/alone/getResponse', function (res) {//区域转移
             _this.allmotor = res.body
              _this.coord= _this.allmotor
              _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/firupload/alone/getResponse', function (res) {//监听上传文件
             _this.allmotor = res.body
             Bus.$emit('firupload',res.body)
        });
      

         this.stompClient.subscribe('/user/motorcontrol/alone/getResponse', function (res) {
            console.log("----motorcontrol----")
            _this.allmotor = res.body
            // Bus.$emit('allmotor',_this.allmotor);
            // console.log("----allmotor----"+_this.allmotor);
        });
        this.stompClient.subscribe('/user/getCoord/alone/getResponse', function (res) {
            console.log("----getCoord----")
            _this.coord="当前坐标："+JSON.parse(res.body)
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/steplost/alone/getResponse', function (res) {
            console.log("----steplost----");
          let reg=new RegExp(';','g')//g代表全部
                let newMsg=res.body.replace(reg,'<br/>')
                 console.log("----steplost----"+newMsg)
                 _this.coord=newMsg;
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/motorFlag/alone/getResponse', function (res) {
            console.log("----motorFlag----");
             let reg=new RegExp(';','g')//g代表全部
                let newMsg=res.body.replace(reg,'<br/>')
                 console.log("----steplost----"+newMsg)
                 _this.coord=newMsg
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/sensorStop/alone/getResponse', function (res) {
            console.log("----sensorStop----")
                 _this.coord=res.body
            _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (res) {
            console.log("----grooveMove----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (res) {
            console.log("----grooveMove----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/searchInlineopt/alone/getResponse', function (res) {
            console.log("----searchInlineopt----")
                 _this.coord=res.body
                  Bus.$emit('searchInlineopt',_this.coord)
               //  _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/writeInlineopt/alone/getResponse', function (res) {
            console.log("----writeInlineopt----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/coordLimit/alone/getResponse', function (res) {
            console.log("----coordLimit----")
                var op= res.body.split(';')
                if(op[2]=='read'){
                    Bus.$emit('phymax',res.body)
                }else{
                  _this.coord=res.body
                  _this.centerDialogVisible =true
                }
        });
         this.stompClient.subscribe('/user/outStepForm/alone/getResponse', function (res) {
            console.log("----outStepForm----")
                var op= res.body.split(';')
                if(op[2]=='read'){
                    Bus.$emit('outMove',res.body)
                }else{
                  _this.coord=res.body
                  _this.centerDialogVisible =true
                }
        });
        this.stompClient.subscribe('/user/zeroOpt/alone/getResponse', function (res) {
            console.log("----zeroOpt----")
                var op= res.body.split(';')
                if(op[1]=='read'){
                    Bus.$emit('zeroOpt',res.body)
                }else{
                  _this.coord=res.body
                  _this.centerDialogVisible =true
                }
        });
        this.stompClient.subscribe('/user/createMotorFile/alone/getResponse', function (res) {
            console.log("----createMotorFile----")
                  _this.coord=res.body
                  _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/speedAcc/alone/getResponse', function (res) {
            console.log("----speedAcc----")
            var op= res.body.split(';')
              if(op[4]=='read'){
                    Bus.$emit('speedAccList',res.body)
                }else{
                  _this.coord=res.body
                  _this.centerDialogVisible =true
                }
        });
         this.stompClient.subscribe('/user/upload/alone/getResponse', function (res) {
                  _this.coord=res.body
                  _this.centerDialogVisible =true
        });
    },
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