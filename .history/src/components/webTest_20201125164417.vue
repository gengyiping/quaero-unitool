<template>
  <div class="" > 
    <el-dialog
  title="提示"
  :visible.sync="centerDialogVisible"
  width="30%"
  center>
  <span v-html="coord"></span>
  <span slot="footer" class="dialog-footer">
       <el-button @click="centerDialogVisible = false" >取 消</el-button>
  </span>
</el-dialog>
     <div style="width:100%;background:#40E0D0">
       <span style="font-size: 38px;color: floralwhite;font-family: 'Helvetica Neue',Helvetica,'PingFang SC','Hiragino Sans GB','Microsoft YaHei','微软雅黑',Arial,sans-serif;">
         简易Qcit
         </span>
       <i class="el-icon-video-play" @click="init" style="font-size: 45px; color: #7FFF00" v-if="initshow"></i>
       <i class="el-icon-video-pause" style="font-size: 45px; color: #DC143C" v-if="closeshow"></i>
      <!--  <el-button @click="init">初始化</el-button>
         <el-button @click="allReset">整机复位</el-button>
        <el-button @click="senMessage">测试发送消息</el-button> -->
    </div>
     <div class="tabBox" >
        <el-tabs :tab-position="tabPosition" v-model="activeName" @tab-click="handleClick" style="height: 708px;">
            <el-tab-pane label="应用" name="0">
               <Qcitsoft height="100%" width="100%" />
            </el-tab-pane>
            <el-tab-pane label="调试" name="1">
               <Qcitdebug height="100%" width="100%"/>
            </el-tab-pane>
            <el-tab-pane label="Demo" name="2">
                <Demo height="10000px" width="100%"/>
            </el-tab-pane>
        </el-tabs>
        
     </div>
     <div>
       <el-divider content-position="left"></el-divider>
          <el-row :gutter="20" style="">
             <el-col :span="16"><div class="grid-content bg-purple"></div></el-col>
             <el-col :span="8"><div class="grid-content bg-purple">版本信息</div></el-col>
          </el-row>
     </div>
     <div id='ip' ></div>
     <h1>{{ ip }}</h1>
     <div>
     <!-- <iframe ref="iframe" name = "child" id = "child" src='/static/IP.html' width="500" height="100" frameborder="0" scrolling="no" style="position:related;top: 2.8px;left: 0px;">
     </iframe> -->
     <iframe ref="iframe" name = "child" id="iframeBox" :src="html" width="100%" height="100%"></iframe>
     </div>

  </div>
  
</template>
<script event="OnObjectReady(objObject,objAsyncContext)" for="foo">   
         if(objObject.IPEnabled != null && objObject.IPEnabled != "undefined" && objObject.IPEnabled == true) {   
             if(objObject.MACAddress != null && objObject.MACAddress != "undefined" && objObject.DNSServerSearchOrder!=null)   
                 MACAddr = objObject.MACAddress;   
             if(objObject.IPEnabled && objObject.IPAddress(0) != null && objObject.IPAddress(0) != "undefined" && objObject.DNSServerSearchOrder!=null)   
                 IPAddr = objObject.IPAddress(0);   
             if(objObject.DNSHostName != null && objObject.DNSHostName != "undefined")   
                 sDNSName = objObject.DNSHostName;   
         }   
     </script>  
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Qcitdebug from '@/view/debugger/Qcitdebug'
import Qcitsoft from '@/view/soft/Qcitsoft'
import Demo from '@/view/demo/divMove'
import Bus from './bus'
import {getNetworkIp} from './ip'
  var MACAddr   
         var IPAddr  
         var DomainAddr  
         var sDNSName
export default {
  data() {
    return {
      html: "static/IP.html",
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
       iframeWin:''
    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo
    },
  mounted() {
    //window.history.go(-1);
   //  this.ready() //外网ip
   this.iframeWin = this.$refs.iframe.contentWindow
    this.initWebSocket()
    // this.getUserIPs((ip) => {
    //   debugger;
    //     this.ip = ip;
    //   });
     //this.getUserIP()
    
   // console.log("xxixiii"+getNetworkIp.getNetworkIp())
    //  debugger
    //  console.log("xxixiii"+Ip)
    // let _this = this;
    //   _this.getIPxs();//获取内网ip
    // this.getIPs(function(ip){
    //   debugger;
    //   console.log("ohohohohoohouh"+ip);
    //   });
    this.$store.state.stompClient=this.stompClient
      //this.ip=this.$store.state.ip
   //   this.callchild()
   
if(this.$store.state.stompClient.handshake.headers['x-forwarded-for'] != null){  
   ip = this.$store.state.stompClient.handshake.headers['x-forwarded-for']; 
     console.log('init:'+ip)
}else{  
   ip = this.$store.state.stompClient.handshake.address;
   console.log('init:'+ip)
 }

  },
  methods: {
    callchild(){
      debugger
      let obj1=window.frames["child"];//获得对应iframe的window对象
      alert(obj1.uip);
    },
    init(){
      debugger
       console.log('init')
      this.stompClient.send('/app/init')
      this.initshow=false
      this.closeshow=true
    },
    initstate(){
      debugger
    //  this.ip=document.getElementById("iploc").defaultValue
       console.log('initstate'+this.ip)
       debugger
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
         debugger
        var ip_addr = evt.candidate.address

        let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g;
         //var ips=ipRegex.exec(evt.candidate.candidate)
        localStorage.setItem('ip_addr',ip_addr)
        var xip=localStorage.getItem('ip_addr')
       // this.$store.state.ip=ip_addr
       debugger
       this.ip=ip_addr
        console.log('ip='+this.ip)
         var _this = this;// `这一步很重要`
        setTimeout(function() { //防止报连接yet
          _this.initstate()//获取上一次连接状态
        }, 2000);
     //  document.getElementById("ip").innerHTML += "<input id='iploc' value='"+ip_addr+"'>"
        console.log("目前仅测试了chrome浏览器OK"+ip_addr)
       }}
        })()
      else{console.log("目前仅测试了chrome浏览器OK")}
     },
     getUserIPs(onNewIP) {
        let MyPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
        let pc = new MyPeerConnection({
            iceServers: []
          });
        let noop = () => {
          };
        let localIPs = {};
        let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g;
        let iterateIP = (ip) => {
          if (!localIPs[ip]) onNewIP(ip);
          localIPs[ip] = true;
        };
        pc.createDataChannel('');
        pc.createOffer().then((sdp) => {
          sdp.sdp.split('\n').forEach(function (line) {
            if (line.indexOf('candidate') < 0) return;
            line.match(ipRegex).forEach(iterateIP);
          });
          pc.setLocalDescription(sdp, noop, noop);
        }).catch((reason) => {
        });
        pc.onicecandidate = (ice) => {
          if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return;
          ice.candidate.candidate.match(ipRegex).forEach(iterateIP);
        };
      },
      getIPs(callback){
    var ip_dups = {};

    //compatibility for firefox and chrome
    var RTCPeerConnection = window.RTCPeerConnection
        || window.mozRTCPeerConnection
        || window.webkitRTCPeerConnection;
    var useWebKit = !!window.webkitRTCPeerConnection;

    //bypass naive webrtc blocking using an iframe
    if(!RTCPeerConnection){
        //NOTE: you need to have an iframe in the page right above the script tag
        //
        //<iframe id="iframe" sandbox="allow-same-origin" style="display: none"></iframe>
        //<script>...getIPs called in here...
        //
        var win = iframe.contentWindow;
        RTCPeerConnection = win.RTCPeerConnection
            || win.mozRTCPeerConnection
            || win.webkitRTCPeerConnection;
        useWebKit = !!win.webkitRTCPeerConnection;
    }

    //minimal requirements for data connection
    var mediaConstraints = {
        optional: [{RtpDataChannels: true}]
    };

    var servers = {iceServers: [{urls: "stun:stun.services.mozilla.com"}]};

    //construct a new RTCPeerConnection
    var pc = new RTCPeerConnection(servers, mediaConstraints);

    function handleCandidate(candidate){
        //match just the IP address
        var ip_regex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/
        var ip_addr = ip_regex.exec(candidate)[1];

        //remove duplicates
        if(ip_dups[ip_addr] === undefined)
            callback(ip_addr);

        ip_dups[ip_addr] = true;
    }

    //listen for candidate events
    pc.onicecandidate = function(ice){

        //skip non-candidate events
        if(ice.candidate)
            handleCandidate(ice.candidate.candidate);
    };

    //create a bogus data channel
    pc.createDataChannel("");

    //create an offer sdp
    pc.createOffer(function(result){

        //trigger the stun server request
        pc.setLocalDescription(result, function(){}, function(){});

    }, function(){});

    //wait for a while to let everything done
    setTimeout(function(){
        //read candidate info from local description
        var lines = pc.localDescription.sdp.split('\n');

        lines.forEach(function(line){
            if(line.indexOf('a=candidate:') === 0)
                handleCandidate(line);
        });
    }, 1000);
},
 getIPxs(){
   debugger
        var RTCPeerConnection = window.RTCPeerConnection || window.webkitRTCPeerConnection || window.mozRTCPeerConnection;
            if (RTCPeerConnection) (function () {
                var rtc = new RTCPeerConnection({iceServers:[]});
                if (1 || window.mozRTCPeerConnection) {     
                    rtc.createDataChannel('', {reliable:false});
                };
                
                rtc.onicecandidate = function (evt) {
                    if (evt.candidate) grepSDP("a="+evt.candidate.candidate);
                };
                rtc.createOffer(function (offerDesc) {
                    grepSDP(offerDesc.sdp);
                    rtc.setLocalDescription(offerDesc);
                }, function (e) { console.warn("offer failed", e); });
                
                
                var addrs = Object.create(null);
                addrs["0.0.0.0"] = false;
                function updateDisplay(newAddr) {
                    if (newAddr in addrs) return;
                    else addrs[newAddr] = true;
                    var displayAddrs = Object.keys(addrs).filter(function (k) { return addrs[k]; });
                    for(var i = 0; i < displayAddrs.length; i++){
                        if(displayAddrs[i].length > 16){
                            displayAddrs.splice(i, 1);
                            i--;
                        }
                    }
                    document.getElementById('list').textContent = displayAddrs[0];
                }
                
                function grepSDP(sdp) {
                    var hosts = [];
                    sdp.split('\r\n').forEach(function (line, index, arr) {
                       if (~line.indexOf("a=candidate")) {    
                            var parts = line.split(' '),       
                                addr = parts[4],
                                type = parts[7];
                            if (type === 'host') updateDisplay(addr);
                        } else if (~line.indexOf("c=")) {       
                            var parts = line.split(' '),
                                addr = parts[2];
                            updateDisplay(addr);
                        }
                    });
                }
            })();
            else{
                document.getElementById('list').textContent = "请使用主流浏览器：chrome,firefox,opera,safari";
            }
 },

handleClick(tab, event) {
  debugger
        console.log(tab, event);
        var page=tab.name+'_#_#'
        this.stompClient.send('/app/page/'+this.ip+'/'+page,{})
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
      debugger
     this.getUserIP()
       this.getUserIPs((ip) => {
        debugger;
          this.ip = ip;
         console.log('ipXXXXXX='+ip)
        });
      this.initSocket()
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
        this.stompClient.subscribe('/user/initstate/alone/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          debugger
          console.log(val.body)
          var pagesindexs=val.body.split("_")
          if(pagesindexs[0]!='#') this.activeName=pagesindexs[0]
          this.activeName="1"
        });
        this.stompClient.subscribe('/user/page/alone/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          debugger
          console.log(val.body)
        });
        
         this.stompClient.subscribe('/user/allmotor/alone/getResponse', function (res) {
            console.log("----allmotor----")
            debugger
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
         this.stompClient.subscribe('  /user/openRemote/alone/getResponse', function (res) {//开关远程服务
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
         debugger
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
        debugger
            _this.allmotor = res.body
            Bus.$emit('searchMotor',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/motorOpt/alone/getResponse', function (res) {//查询所有电机信息控制
            debugger
            _this.allmotor = res.body
            _this.coord= _this.allmotor
              _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/transferMove/alone/getResponse', function (res) {//区域转移
            debugger
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
          debugger;
            console.log("----getCoord----")
            _this.coord="当前坐标："+JSON.parse(res.body)
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/steplost/alone/getResponse', function (res) {
          debugger;
            console.log("----steplost----");
          let reg=new RegExp(';','g')//g代表全部
                let newMsg=res.body.replace(reg,'<br/>')
                 console.log("----steplost----"+newMsg)
                 _this.coord=newMsg;
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/motorFlag/alone/getResponse', function (res) {
          debugger;
            console.log("----motorFlag----");
             let reg=new RegExp(';','g')//g代表全部
                let newMsg=res.body.replace(reg,'<br/>')
                 console.log("----steplost----"+newMsg)
                 _this.coord=newMsg
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/sensorStop/alone/getResponse', function (res) {
          debugger;
            console.log("----sensorStop----")
                 _this.coord=res.body
            _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (res) {
          debugger;
            console.log("----grooveMove----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (res) {
          debugger;
            console.log("----grooveMove----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/searchInlineopt/alone/getResponse', function (res) {
          debugger;
            console.log("----searchInlineopt----")
                 _this.coord=res.body
                  Bus.$emit('searchInlineopt',_this.coord)
               //  _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/writeInlineopt/alone/getResponse', function (res) {
          debugger;
            console.log("----writeInlineopt----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/coordLimit/alone/getResponse', function (res) {
          debugger;
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
          debugger;
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
          debugger;
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
          debugger;
            console.log("----createMotorFile----")
                  _this.coord=res.body
                  _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/speedAcc/alone/getResponse', function (res) {
          debugger;
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
          debugger;
                  _this.coord=res.body
                  _this.centerDialogVisible =true
        });
    },
     initSocket() {
      this.socket = new SockJS(this.socketUrl, null,{ timeout: 50000 })// 连接服务端
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
        this.socket = new SockJS(socketUrl, null,{ timeout: 50000 })
        this.stompClient = Stomp.over(this.socket)
         this.$store.state.stompClient=this.stompClient
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
    connection() {
      // 更换that指针
      debugger
      var _this = this;// `这一步很重要`
      const socket = new SockJS('http://127.0.0.1:8080/webSocket')
      this.stompClient = Stomp.over(socket)
//建立连接，订阅主题
      this.stompClient.connect({}, (frame) => {
       // console.log(frame)
        this.stompClient.subscribe('/topic/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          console.log('-------++++++++++++++++++++++++++++++------------')
//下面会报错，应该是json的问题，但是数据可以接收到
          console.log(val.body)
        });
        this.stompClient.subscribe('/user/initstate/alone/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          debugger
          console.log(val.body)
          var pagesindexs=val.body.split("_")
          if(pagesindexs[0]!='#') this.activeName=pagesindexs[0]
          this.activeName="1"
        });
        this.stompClient.subscribe('/user/page/alone/getResponse', (val) => {
          // this.list1 = JSON.parse(val.body)
          debugger
          console.log(val.body)
        });
        
         this.stompClient.subscribe('/user/allmotor/alone/getResponse', function (res) {
            console.log("----allmotor----")
            debugger
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
         this.stompClient.subscribe('  /user/openRemote/alone/getResponse', function (res) {//开关远程服务
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
         debugger
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
        debugger
            _this.allmotor = res.body
            Bus.$emit('searchMotor',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
        });
         this.stompClient.subscribe('/user/motorOpt/alone/getResponse', function (res) {//查询所有电机信息控制
            debugger
            _this.allmotor = res.body
            _this.coord= _this.allmotor
              _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/transferMove/alone/getResponse', function (res) {//区域转移
            debugger
             _this.allmotor = res.body
              _this.coord= _this.allmotor
              _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/firupload/alone/getResponse', function (res) {//监听上传文件
            debugger
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
          debugger;
            console.log("----getCoord----")
            _this.coord="当前坐标："+JSON.parse(res.body)
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/steplost/alone/getResponse', function (res) {
          debugger;
            console.log("----steplost----");
          let reg=new RegExp(';','g')//g代表全部
                let newMsg=res.body.replace(reg,'<br/>')
                 console.log("----steplost----"+newMsg)
                 _this.coord=newMsg;
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/motorFlag/alone/getResponse', function (res) {
          debugger;
            console.log("----motorFlag----");
             let reg=new RegExp(';','g')//g代表全部
                let newMsg=res.body.replace(reg,'<br/>')
                 console.log("----steplost----"+newMsg)
                 _this.coord=newMsg
            _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/sensorStop/alone/getResponse', function (res) {
          debugger;
            console.log("----sensorStop----")
                 _this.coord=res.body
            _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (res) {
          debugger;
            console.log("----grooveMove----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/grooveMove/alone/getResponse', function (res) {
          debugger;
            console.log("----grooveMove----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/searchInlineopt/alone/getResponse', function (res) {
          debugger;
            console.log("----searchInlineopt----")
                 _this.coord=res.body
                  Bus.$emit('searchInlineopt',_this.coord)
               //  _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/writeInlineopt/alone/getResponse', function (res) {
          debugger;
            console.log("----writeInlineopt----")
                 _this.coord=res.body
                 _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/coordLimit/alone/getResponse', function (res) {
          debugger;
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
          debugger;
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
          debugger;
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
          debugger;
            console.log("----createMotorFile----")
                  _this.coord=res.body
                  _this.centerDialogVisible =true
        });
         this.stompClient.subscribe('/user/speedAcc/alone/getResponse', function (res) {
          debugger;
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
          debugger;
                  _this.coord=res.body
                  _this.centerDialogVisible =true
        });
        
        
      })
    }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
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