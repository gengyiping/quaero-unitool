<template>
  <div class="" > 
     <div>
       <el-button @click="init">初始化</el-button>
        <el-button @click="reset">进样复位</el-button>
         <el-button @click="allReset">整机复位</el-button>
        <el-button @click="senMessage">测试发送消息</el-button>
    </div>
     <div class="tabBox" >
        <el-tabs :tab-position="tabPosition" style="height: 708px;">
            <el-tab-pane label="应用软件">
               <Qcitsoft height="100%" width="100%"/>
            </el-tab-pane>
            <el-tab-pane label="调试软件">
               <Qcitdebug height="100%" width="100%"/>
            </el-tab-pane>
            <el-tab-pane label="Demo">
                <Demo height="10000px" width="100%"/>
            </el-tab-pane>
        </el-tabs>
        <el-divider content-position="left"></el-divider>
          <el-row :gutter="20" style="">
             <el-col :span="16"><div class="grid-content bg-purple"></div></el-col>
             <el-col :span="8"><div class="grid-content bg-purple">版本信息</div></el-col>
          </el-row>
     </div>
  </div>
  
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Qcitdebug from '@/view/debugger/Qcitdebug'
import Qcitsoft from '@/view/soft/Qcitsoft'
import Demo from '@/view/demo/divMove'
import Bus from './bus'
export default {
  data() {
    return {
      tabPosition: 'left',
      list1: [],
      stompClient: null,
      allmotor:null
    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo
    },
  mounted() {
    this.initWebSocket()
    this.$store.state.stompClient=this.stompClient;
  },
  methods: {
    init(){
       console.log('init')
      this.stompClient.send('/app/init')
    },
     reset(){
       console.log('reset')
      this.stompClient.send('/app/reset')
    },
    allReset(){
       console.log('allReset')
      this.stompClient.send('/app/allreset')
    },
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
         this.stompClient.subscribe('/user/allmotor/alone/getResponse', function (res) {
            console.log("----allmotor----");
            debugger
            /*  _this.allmotor=res.body;
                        console.log("----allmotor----"+_this.allmotor);
            console.log("res => "+res); */
            _this.allmotor = res.body;
            Bus.$emit('allmotor',_this.allmotor);
           //  vueEvents.$emit('allmotor',_this.allmotor);
            //  info.forEach(function (item, index) {
            //    _this.allmotor[index]=item[index];
            //   });
          //   for(var i = 0 ; i < info.l ; i++){
          //    this.allmotor.push(i)
          //  }
          
             console.log("----allmotor----"+_this.allmotor);

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