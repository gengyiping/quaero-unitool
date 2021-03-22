<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
      <div style="width:1080px;height:550px;background: rgb(238, 255, 247);padding-top: 10px;">
      <div class="containerContent" title="进样测试" style="width:auto; height:auto;">  
           <table style="margin-top: 20px;">
              <tr>
                  <td colspan="">&emsp;</td>
                  <td><el-button type="primary" @click="start()" plain>开始</el-button></td>
                  <td>&emsp;</td>
                  <td><el-button type="primary" @click="suspend()" plain>{{susp}}</el-button></td>
                  <td colspan="">&emsp;</td>
                  <td colspan=""><el-button type="primary" @click="end()" plain>结束</el-button></td>
                  <td colspan="">&emsp;</td>
                  <td colspan=""><el-button type="primary" @click="logclear()" plain>清空</el-button></td>
                  <td colspan="">&emsp;<span>次数: {{ count }}</span></td>
             <tr>
              <tr>
               <td colspan="8" rowspan="20">
                 <el-input type="textarea" style="width:500px; height:450px;" id="desces" v-model="desc" :rows="20" ::disabled="removeAttr"></el-input>
               </td>
             </tr>
             
         </table>
      </div>
      </div> 
</div> 
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Bus from '../../components/bus' //跨页面自定义传值组件
import VueWorker from 'vue-worker'

export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      desc:'1111',
      worker: null,
      susp:'暂停',
      nowtime:'',
      count:0
    }
  },
  
  mounted() {
  //  this.testconnection()
  this.stompClient= this.$store.state.stompClient
   var _this=this
   
   Bus.$on('testMoveone',function(val){//监听first组件的txt事件
                   _this.desc=_this.desc+"\n"+val
                    _this.getNowTime()
                    _this.desc=_this.desc+"\n"+'结束时间：'+ _this.nowtime+"\n"
                    _this.count=_this.count+1
                    
               })
                Bus.$on('scanBarcode',function(val){//监听first组件的txt事件
                    _this.desc=_this.desc+"\n"+val
                })
    // $('#desces').removeAttr("disabled")
  },
  methods: {
    testconnection(){
//      var _this = this;// `这一步很重要`
//       const socket = new SockJS('http://127.0.0.1:8080/webSocket')
//       this.stompClient = Stomp.over(socket)
// //建立连接，订阅主题
//       this.stompClient.connect({}, (frame) => {

//       })
    },
    getNowTime(){
      this.nowtime= new Date().getFullYear() +"-" +(new Date().getMonth() + 1) + "-" +new Date().getDate() 
          +" " + new Date().getHours() +":" +new Date().getMinutes() +":" + new Date().getSeconds();
    },

      start(){
          
          var _this=this
           _this.fthis='',
          this.susp='暂停'
         this.getNowTime()
        // this.desc=''
          if(this.desc==''){
            _this.desc='开始时间：'+_this.nowtime+"\n";
          }else{
             _this.desc=this.desc+"\n"+'开始时间：'+_this.nowtime+"\n";
          }
          // _this.stompClient.send('/app/testMoveone')
          new Promise(function (resolve,reject) {
             // resolve()
              _this.stompClient.send('/app/testMoveone')
              // resolve(resData);
            //    resolve();
         }).then(function () {
            //  _this.desc=_this.desc+"\n"+'结束时间：'+_this.nowtime+"\n";
            //   console.log('结束!');
           // resolve();
         });
         
      },
      suspend(){
        var _this=this
        var suspstate=''
         new Promise(function (resolve,reject) {
            if(_this.susp=='继续'){
              _this.susp='暂停'
              suspstate=0
            }else{
             _this.susp='继续'
             suspstate=1
            }
              _this.stompClient.send('/app/testMoveStop/'+suspstate)
          })
      },
      end(){
         var _this=this
        this.susp='暂停'
        var suspstate=2
         new Promise(function (resolve,reject) {
              _this.stompClient.send('/app/testMoveStop/'+suspstate)
          })
          //  _this.getNowTime()
          //  _this.desc=_this.desc+"\n"+'结束时间：'+ _this.nowtime+"\n";
      },
      logclear(){
         this.desc=''
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
 .motorContent{
width:100%;
height:auto;
margin-top: 10px;

  }
    .container{
        position:relative;
        border:1px solid #DCDFE6;
        margin-top: 20px;
       
    }
    .container::before{
        content:attr(title);
        position:absolute;
        left:100px;
        transform:translateX(-50%);
        -webkit-transform:translate(-50%,-50%);
        padding:0 10px;
        background-color:#fff;
    }
        .containerContent{
        position:relative;
        border:1px solid #DCDFE6;
        margin-top: 10px;
       
    }
    .containerContent::before{
        content:attr(title);
        position:absolute;
        left:100px;
        transform:translateX(-50%);
        -webkit-transform:translate(-50%,-50%);
        padding:0 10px;
        background-color:#fff;
    }
</style>