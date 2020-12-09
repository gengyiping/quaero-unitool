<template>
  <div class=""  style="background: rgb(238, 255, 247);"> 
      <div class="containerContent" title="进样测试" style="width:auto; height:auto; ">  
           <table style="margin-top: 10px;">
              <tr>
                  <td colspan=""></td>
                  <td><el-button type="primary" @click="start()" plain>开始</el-button></td>
                  <td></td>
                  <td><el-button type="primary" @click="suspend()" plain>{{susp}}</el-button></td>
                  <td colspan=""></td>
                  <td colspan=""><el-button type="primary" @click="end()" plain>结束</el-button></td>
                  <td colspan=""></td>
             <tr>
              <tr>
               <td colspan="7" rowspan="10">
                 <el-input type="textarea" v-model="desc" :rows="10" ></el-input>
               </td>
             </tr>
             
         </table>
      </div>
      
</div> 
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import VueWorker from 'vue-worker'
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      desc:'1111',
      worker: null,
      susp:'暂停',

    }
  },
  
  mounted() {
  //  this.testconnection()
  this.stompClient= this.$store.state.stompClient;
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
      start(){
          debugger
          var _this=this
          this.susp='暂停'
         var nowtime= new Date().getFullYear() +"-" +(new Date().getMonth() + 1) + "-" +new Date().getDate() 
          +" " + new Date().getHours() +":" +new Date().getMinutes() +":" + new Date().getSeconds();
          if(this.desc==''){
            this.desc=+ this.desc+'开始时间：'+nowtime+"\n";
          }else{
             this.desc=+ this.desc+"\n"+'开始时间：'+nowtime+"\n";
          }
          // _this.stompClient.send('/app/testMoveone')
          new Promise(function (resolve,reject) {
              _this.stompClient.send('/app/testMoveone')
               Bus.$on('testMoveone',function(val){//监听first组件的txt事件
                   _this.desc=_this.desc+"\n"+val
                    var nowtime= new Date().getFullYear() +"-" +(new Date().getMonth() + 1) + "-" +new Date().getDate() 
          +" " + new Date().getHours() +":" +new Date().getMinutes() +":" + new Date().getSeconds();
           this.desc=this.desc+"\n"+'结束时间：'+nowtime+"\n";

               })
              // resolve(resData);
              //  resolve();
          }).then(function () {
             this.desc=+ this.desc+"\n"+'结束时间：'+nowtime+"\n";
              console.log('结束!');
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
        this.susp='暂停'
         _this.stompClient.send('/app/testMoveStop/'+2)
          var nowtime= new Date().getFullYear() +"-" +(new Date().getMonth() + 1) + "-" +new Date().getDate() 
          +" " + new Date().getHours() +":" +new Date().getMinutes() +":" + new Date().getSeconds();
           this.desc=this.desc+"\n"+'结束时间：'+nowtime+"\n";
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
        margin-top: 20px;
       
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