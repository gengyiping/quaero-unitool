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
         <i class="el-icon-d-arrow-left" style="font-size: 45px; color: #7FFF00;"  @click="drawer = true"></i> 
        </span>
       
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
     <el-drawer
  title="我是信息栏"
  :visible.sync="drawer"
  :with-header="false">
  <span>信息栏</span>
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
       <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>
    </ul>
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
import {getNetworkIp} from './ip'
import sProgress from './SProgress'
export default {
  data() {
    return {
      count:0,
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

    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo,
    },

  mounted() {
     this.stompClient= this.$store.state.stompClient;
      debugger
     if(this.stompClient==null){
       this.loadinit()
     }
     this.uid= this.$store.state.uid;
   //  this.ready() //外网ip
    //this.initWebSocket()
    // this.getUserIP()
      //this.ip=this.$store.state.ip
      
     
  },
  methods: {
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

      handleClick(tab, event) {
        // console.log(tab, event)
        // var page='0_'+tab.name+'_#_#'
        // this.stompClient.send('/app/page/'+this.ip+'/'+page,{})
        // localStorage.setItem('page',page)
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
     }
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
     stompinit(newVal,oldVal){
      this.stompClient=newVal
    },
     stompuid(newVal,oldVal){
       this.uid=this.$store.state.uid
     },
     resinfo(newVal,oldVal){
       debugger
        for(var i = 0;i<5;i++){
            $(".infinite-list").append("<li id=li"+i+">"+i+"</li>"); //在ul标签上动态添加li标签
            $("#li"+i).attr("class",'list-group-item');     //为li标签添加class属性
        }
     }
},
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