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
         <span>
       <i class="el-icon-video-play" @click="init" style="font-size: 45px; color: #7FFF00" v-if="initshow"></i>
       <i class="el-icon-video-pause" style="font-size: 45px; color: #DC143C" v-if="closeshow"></i>
        </span>
         <span style="float: right;" v-if="infoshow">
         <i class="el-icon-switch-button" style="font-size: 45px; color: #7FFF00;"  @click="backLogin"></i> 
        </span>
        <span style="float: right;" v-if="infoshow">
         <i class="el-icon-d-arrow-left" style="font-size: 45px; color: #7FFF00;"  @click="drawer = true"></i> 
        </span>
       
       
      <!--  <el-button @click="init">初始化</el-button>
         <el-button @click="allReset">整机复位</el-button>
        <el-button @click="senMessage">测试发送消息</el-button> -->
    </div>
    <div v-if="progressshow">
      <el-progress :percentage="percentage" :color="customColorMethod"></el-progress>
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
     <el-drawer
  title="我是信息栏"
  :visible.sync="drawer"
  :with-header="false">
  <span>信息栏</span>
    <el-scrollbar
            style="height: 100%"
            wrap-class="scrollbar-wrapper"  >
            <div
           
              class="infinite-list"
              style="overflow:auto;width:100%;height:700px;"
              v-infinite-scroll="infolabel"
              infinite-scroll-delay="600">
              <!-- <div v-for="info in infolabel" :key="info" style="float: left;" class="infinite-list-item">{{ info }}</div> -->
              <ul id="example-1">
  <li v-for="item in infolabel" :key="item" style="float:left">
    {{ item }}
  </li>
</ul>
            </div>
          </el-scrollbar>
</el-drawer>
    
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
//import {getNetworkIp} from './ip'

export default {
  data() {
    return {
      infolabel:[],
      count:'',
       infoOptions:[{
          label: 'tftp服务'
        },{
          label: ' 开启轨道远程'
        }
       ],
      infoshow:true,
      drawer:false,
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
       uid:'',
       percentage: 0,//进度条
       progressshow:false,
        customColor: '#409eff',
        customColors: [
          {color: '#f56c6c', percentage: 20},
          {color: '#e6a23c', percentage: 40},
          {color: '#5cb87a', percentage: 60},
          {color: '#1989fa', percentage: 80},
          {color: '#6f7ad3', percentage: 100}
        ]

    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo,
    },

  mounted() {
     this.stompClient= this.$store.state.stompClient;
     this.ifinit()
      debugger
    //  if(this.stompClient==null){
    //    this.loadinit()
    //  }
    // this.uid= this.$store.state.uid;
   //  this.ready() //外网ip
    //this.initWebSocket()
    // this.getUserIP()
      //this.ip=this.$store.state.ip
       var _this = this
       Bus.$on('progres',function(val){//监听fi
            if(val==false){
              _this.increase()
               _this.progressshow=val 
            }else{
              _this.percentage=0
              _this.progressshow=val 
               new Promise(function (resolve,reject) {
                   for(var i=0;i<19;i++){
                    _this.increase()
                    _this.waitsl(100)
                   }
              }).then(function () {
              });
           
            }
         })
     
  },
  methods: {
    ifinit(){
       this.stompClient.send('/app/ifInit')
        Bus.$on('ifInit',function(val){//监听fi
            if(val=='true'){
              this.initshow=false
              this.closeshow=true
            }
         })
    },
     waitsl(delay) {
       var start = (new Date()).getTime();
        while((new Date()).getTime() - start < delay) {
          continue;
       }
    },
     customColorMethod(percentage) {//进度条颜色
        if (percentage < 30) {
          return '#909399';
        } else if (percentage < 70) {
          return '#e6a23c';
        } else {
          return '#67c23a';
        }
      },
      increase() {//进度条变化
        this.percentage += 5;
        if (this.percentage > 100) {
          this.percentage = 100;
        }
      },
    backLogin(){
       this.$parent.loginshow=true
       this.$parent.mainshow=false
    },
    loadinit(){
      
      debugger
      Bus.$emit('loadinit');
    },
    init(){
      this.$store.state.resinfo=1
      debugger
       console.log('init')
       var data = {
        "message": ''
        }
         var toUser = [this.uid];
       if (toUser.length > 1) {
          data.receiver = toUser;
        }
       data = JSON.stringify(data);
      this.stompClient.send('/app/init',{},data)
      this.initshow=false
      this.closeshow=true
       debugger
      // 

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
        // console.log(tab, event)
         var page='#-'+tab.name+'-#-#'
         this.stompClient.send('/app/page/'+page,{})
        // localStorage.setItem('page',page)
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
      // this.socket = new SockJS(this.socketUrl)// 连接服务端
      // this.stompClient = Stomp.over(this.socket)
      // this.stompClient.connect({}, (frame) => {
      //   this.successCallback()
      // }, () => {
      //   this.reconnect(this.socketUrl, this.successCallback)
      // })
      this.connection()
    },
    connection() {
      // 更换that指针
      debugger
      var _this = this;// `这一步很重要`
      const socket = new SockJS('http://127.0.0.1:8080/webSocket')
     // const socket = new SockJS('http://192.168.2.105:8080/webSocket')
      this.stompClient = Stomp.over(socket)
      this.$store.state.stompClient=this.stompClient
//建立连接，订阅主题
      this.stompClient.connect({}, (frame) => {
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
         this.stompClient.subscribe('/user/initInfo/alone/getResponse', (val) => {//连接信息
          // this.list1 = JSON.parse(val.body)
          debugger
             console.log(val.body)
              this.$store.state.initInfo=val.body
        });
        this.stompClient.subscribe('/user/writeloacl/alone/getResponse', (val) => {//写入本地Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
              _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/writeEquipment/alone/getResponse', (val) => {//写入设备Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
              _this.centerDialogVisible =true
        });
        
         this.stompClient.subscribe('/user/readloacl/alone/getResponse', (val) => {//读取本地Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
             Bus.$emit('readloacl',_this.allmotor)
        });
          this.stompClient.subscribe('/user/readEquipment/alone/getResponse', (val) => {//读取设备Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
             Bus.$emit('readEquipment',_this.allmotor)
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
         this.stompClient.subscribe('/user/testMoveone/alone/getResponse', function (res) {//测试流程
         debugger
                  _this.coord=res.body
                  Bus.$emit('testMoveone',_this.coord);
        });
         this.stompClient.subscribe('/user/scanBarcode/alone/getResponse', function (res) {//监听扫码
         debugger
                  _this.coord=res.body
                  Bus.$emit('scanBarcode',_this.coord);
        });
       
        
        
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
         this.stompClient.subscribe('/user/initInfo/alone/getResponse', (val) => {//连接信息
          // this.list1 = JSON.parse(val.body)
          debugger
             console.log(val.body)
              this.$store.state.initInfo=val.body
        });
        this.stompClient.subscribe('/user/writeloacl/alone/getResponse', (val) => {//写入本地Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
              _this.centerDialogVisible =true
        });
        this.stompClient.subscribe('/user/writeEquipment/alone/getResponse', (val) => {//写入设备Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
              _this.centerDialogVisible =true
        });
        
         this.stompClient.subscribe('/user/readloacl/alone/getResponse', (val) => {//读取本地Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
             Bus.$emit('readloacl',_this.allmotor)
        });
          this.stompClient.subscribe('/user/readEquipment/alone/getResponse', (val) => {//读取设备Ip信息
          // this.list1 = JSON.parse(val.body)
            debugger
             console.log(val.body)
             _this.allmotor = val.body
             Bus.$emit('readEquipment',_this.allmotor)
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
         this.stompClient.subscribe('/user/testMoveone/alone/getResponse', function (res) {//测试流程
                  _this.coord=res.body
                  Bus.$emit('testMoveone',coord);
        });
        
    },
  },
  computed: {
     stompinit(){
       return this.$store.state.stompClient
    },
    firstPage(){
         debugger
        return  this.$store.state.firstPage;
       },  
        stompuid(){
       return this.$store.state.uid
     },
     resinfo(){
       return  this.$store.state.resinfo
     },
  },
  watch:{
    firstPage(newVal,oldVal){
      debugger
        console.log("newVal="+newVal)
         console.log("oldVal="+oldVal)
             if(newVal!==oldVal){
          var pagesindexs=newVal.split("-")
           this.activeName=pagesindexs[1]
           this.$store.state.secondPage=newVal
          }
    },
     stompinit(newVal,oldVal){
      this.stompClient=newVal
    },
     stompuid(newVal,oldVal){
       this.uid=this.$store.state.uid
     },
     resinfo(newVal,oldVal){
       debugger
        //   var obj = {}
        //  obj.label='1111'
        //   this.infoOptions.push(obj)
          this.infolabel.push(newVal)
    //       this.$nextTick(() => {
 
    //          var div = document.getElementById('data-list-content')
 
    //          div.scrollTop = div.scrollHeight
     
    //  })

          //document.getElementsByName(".infinite-list").append("<li id=li"+i+">"+i+"</li>"); //在ul标签上动态添加li标签
           //document.getElementById("li"+i).attr("class",'list-group-item');     //为li标签添加class属性
          //  $(".infinite-list").append("<li id=li"+i+">"+i+"</li>"); //在ul标签上动态添加li标签
           // $("#li"+i).attr("class",'list-group-item');     //为li标签添加class属性
     }
},
}
</script>
<style rel="stylesheet/scss" lang="scss">
.scrollbar-wrapper {
    overflow-x: hidden !important;
  }
.tabBox{
  margin-top: 5px;
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