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
                 <el-input type="textarea" style="width:500px; height:450px;" id="desces" v-model="desc" :rows="20"  readonly></el-input>
               </td>
             </tr>
             
         </table>
      </div>
      </div> 
</div> 
</template>
<script>
import SockJS from  'sockjs-client';  // 安装并引入相关模块
import  Stomp from 'stompjs';
export default {
    data() {
      return {
        dataList: []
      };
    },
    mounted:function(){
      this.initWebSocket();
    },
    beforeDestroy: function () { // 页面离开时断开连接,清除定时器    
      this.disconnect();
      clearInterval(this.timer);
    },
    methods: {
      initWebSocket() {
        this.connection();
        let self = this;      
        this.timer = setInterval(() => { // 断开重连机制,尝试发送消息,捕获异常发生时重连
          try {
            self.stompClient.send("test");
          } catch (err) {
            console.log("断线了: " + err);
            self.connection();
          }
        }, 5000);
      },
      connection() {// 建立连接对象
        this.socket = new SockJS('http://xxxxxx:8081/ws');//连接服务端提供的通信接口，连接以后才可以订阅广播消息和个人消息
        this.stompClient = Stomp.over(this.socket);// 获取STOMP子协议的客户端对象
        var headers = {// 定义客户端的认证信息,按需求配置
          login: 'mylogin',
          passcode: 'mypasscode',
          // additional header
          'client-id': 'my-client-id'
        };
        this.stompClient.connect(headers,(frame) => {// 向服务器发起websocket连接
          this.stompClient.subscribe('/topic/chat_msg', (msg) => { // 订阅服务端提供的某个topic
           consolel.log(msg.body);  // msg.body存放的是服务端发送给我们的信息
          });
        }, (err) => {
            console.log(err); // 连接发生错误时的处理函数
        });
      },
      disconnect() {// 断开连接
        if (this.stompClient != null) {
          this.stompClient.disconnect();
          console.log("Disconnected");
        }
      }
    }
};
</script>
<style rel="stylesheet/scss" lang="scss">

</style>