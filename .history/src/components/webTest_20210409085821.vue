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
     <div style="width:100%;background:#40E0D0;min-width:1080px;border-radius:8px;">
       <span style="font-size: 38px;color: floralwhite;font-family: 'Helvetica Neue',Helvetica,'PingFang SC','Hiragino Sans GB','Microsoft YaHei','微软雅黑',Arial,sans-serif;border-radius:8px;">
         Qcit-S(SAM100)
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
     <div class="tabBox" style="min-width:1080px;min-height:500px;background: rgb(238, 255, 247);border-radius:8px;">
        <el-tabs :tab-position="tabPosition" v-model="activeName" @tab-click="handleClick" style="height: 708px;">
             <el-tab-pane label="应用" name="0">
               <Qcitsoft height="100%" width="100%" />
            </el-tab-pane>
            <el-tab-pane label="调试" name="1">
               <Qcitdebug height="100%" width="100%"/>
            </el-tab-pane>
           
            <!-- <el-tab-pane label="Demo" name="2">
                <Demo height="10000px" width="100%"/>
            </el-tab-pane> -->
        </el-tabs>
        
     </div>
     <div>
       <el-divider content-position="left"></el-divider>
          <el-row :gutter="20" style="">
             <el-col :span="16"><div class="grid-content bg-purple"></div></el-col>
             <el-col :span="8"><div class="grid-content bg-purple">版本信息：1.0.0.278</div></el-col>
          </el-row>
     </div>
     <el-drawer
  title="我是信息栏"
  :visible.sync="drawer"
  :with-header="false">
  <!-- <span>信息栏</span> -->
              <el-tabs type="border-card">
  <el-tab-pane>
    <span slot="label"><i class="el-icon-info"></i> 消息中心</span>
     <el-input type="textarea" style="width:100%; height:100%;" v-model="infodesc" :rows="30" ></el-input>
     <!-- <el-scrollbar
            style="height: 100%"
            wrap-class="scrollbar-wrapper"  >
            <div
              class="infinite-list"
              style="overflow:auto;width:100%;height:700px;"
              v-infinite-scroll="infolabel"
              infinite-scroll-delay="600">
          <ul id="example-1">
                   <li v-for="item in infolabel" :key="item" style="float:left">
                        {{ item }}
                   </li>
              </ul>
               </div>
          </el-scrollbar> -->
  </el-tab-pane>
  <el-tab-pane label="系统消息">
     <span slot="label"  @click="systemClick()"><i class="el-icon-tickets"></i> 系统消息</span>
           <el-input type="textarea"  id="system_id" style="width:100%; height:100%; " v-model="systemdesc" :rows="30" ></el-input>
    </el-tab-pane>
</el-tabs>
             
           
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
        ],
        infodesc:'',
        systemdesc:''

    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo,
    },

  mounted() {
     this.stompClient= this.$store.state.stompClient;
       Bus.$on('ifInit',function(val){//监听fi
            if(val=='true'){
               _this.initshow=false
               _this.closeshow=true
            }
         })
     this.ifinit()
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
                    _this.waitsl(200)
                   }
              }).then(function () {
              });
           
            }
         })
  },
  methods: {
    systemClick(){
      var _this=this
      this.$axios({
        method:'post',
        url: '/api/upload/sysinfo',
      }).then(function (response) {
        _this.systemdesc= response.data.data.path
        
        _this.$nextTick(() => {
   setTimeout(() => {
      const textarea = document.getElementById('system_id');
      textarea.scrollTop = textarea.scrollHeight;
   }, 13)
})
         
        })

    },
    ifinit(){
       var _this = this;
       this.stompClient.send('/app/ifInit')
       
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
    getTime: function () {
      var _this = this;
      let yy = new Date().getFullYear();
      var mm =
        new Date().getMonth() < 10
          ? "0" + (new Date().getMonth() + 1)
          : new Date().getMonth() + 1;
      var dd =
        new Date().getDate() < 10
          ? "0" + new Date().getDate()
          : new Date().getDate();
      let hh = new Date().getHours();
      let mf =
        new Date().getMinutes() < 10
          ? "0" + new Date().getMinutes()
          : new Date().getMinutes();
      let ss =
        new Date().getSeconds() < 10
          ? "0" + new Date().getSeconds()
          : new Date().getSeconds();
       return _this.gettime = yy + "-" + mm + "-" + dd + " " + hh + ":" + mf + ":" + ss;
    },
    initstate(){
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
         var page='#-'+tab.name+'-#-#'
         this.stompClient.send('/app/page/'+page,{})
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
         // this.infolabel.push('<br/>'+newVal)
         this.infodesc=this.infodesc+this.getTime()+' '+newVal+"\n"

     },
     
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
 .el-divider--horizontal{
    margin:  5px 0px 24px 0;
  }
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