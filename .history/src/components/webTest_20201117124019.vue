<template>
  <div class="" > 
    <el-dialog
  title="提示"
  :visible.sync="centerDialogVisible"
  width="30%"
  center>
  <span v-html="coord"></span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
  </span>
</el-dialog>
     <div>
       <el-button @click="init">初始化</el-button>
        <el-button @click="reset">进样复位</el-button>
         <el-button @click="allReset">整机复位</el-button>
        <el-button @click="senMessage">测试发送消息</el-button>
    </div>
     <div class="tabBox" >
        <el-tabs :tab-position="tabPosition" v-model="activeName" @tab-click="handleClick" style="height: 708px;">
            <el-tab-pane label="应用软件" name="0">
               <Qcitsoft height="100%" width="100%" />
            </el-tab-pane>
            <el-tab-pane label="调试软件" name="1">
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
      allmotor:null,
       centerDialogVisible: false,
       coord:null,
       ip:'',
       activeName: '0',
       pagestate:''
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
  },
  methods: {
    init(){
       console.log('init')
      this.stompClient.send('/app/init')
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

         //let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g
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
            let _this = this;
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
                    console.log('内网ip',displayAddrs[0]);      //打印出内网ip
                    _this.user.ip_in = displayAddrs[0];//获取内网ip
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
                console.log("请使用主流浏览器：chrome,firefox,opera,safari");
            }
 },

     reset(){
       console.log('reset')
      this.stompClient.send('/app/reset')
    },
handleClick(tab, event) {
  debugger
        console.log(tab, event);
      //  this.stompClient.send('/app/allreset')
        var page=tab.name+'_#_#'
        this.stompClient.send('/app/page/'+this.ip+'/'+page,{})
      },
    // allReset(){
    //    console.log('allReset')
    //   this.stompClient.send('/app/allreset')
    // },
    senMessage() {
      console.log('senMessage')
      this.stompClient.send('/app/test')
    },
    initWebSocket() {
       console.log('initWebSocket')
      this.connection()
      // 需要有一个失败重连得到问题
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
         this.stompClient.subscribe('/user/sensorSearch/alone/getResponse', function (res) {
            console.log("----allmotor----")
            debugger
            _this.allmotor = res.body
            Bus.$emit('allmotor',_this.allmotor)
             console.log("----allmotor----"+_this.allmotor)
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