<template>
  <div class="" > 
      <div style="width:100%;height:300px; background: red;">
          电机更多界面
       <div>
        <span>用户</span><input type="text" id="user" value="zhangsan"/>
        <button id="connect" @onclick="connect">Connect</button>
        <button id="disconnect" disabled="disabled" @onclick="disconnect">Disconnect</button>
    </div>
    <div id="conversationDiv">
        <span>名字</span><input type="text" id="name" value="zhangsan"/>
        <button id="sendName" v-on:onclick="sendName()">Send</button>
        <p id="response"></p>
    </div>
 </div>
      
</div> 
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
    }
  },
  
  mounted() {
//this.connect();
//this.sendName();
  },
  methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
          setConnected(connected){
            document.getElementById("connect").disabled = connected;
            document.getElementById("disconnect").disabled = !connected;
            $("#response").html();
        },
        connect() {
            var socket = new SockJS("http://127.0.0.1:8080/webSocket");
            this.stompClient = Stomp.over(socket);
             this.stompClient.connect({}, function(frame) {
               // this.setConnected(true);
                console.log('Connected: ' + frame);
                this.stompClient.subscribe('/topic/greetings', function(greeting){
                  console.log(JSON.parse(greeting.body).content)
                  //  showGreeting(JSON.parse(greeting.body).content);
                });
 
                this.stompClient.subscribe('/user/' + document.getElementById('user').value + '/message',function(greeting){
                  //  alert(JSON.parse(greeting.body).content);
                    console.log(JSON.parse(greeting.body).content)
                   // showGreeting(JSON.parse(greeting.body).content);
                });
            });

        },

        disconnect() {
            if (stompClient != null) {
                this.stompClient.disconnect();
            }
            this.setConnected(false);
            console.log("Disconnected");
        },
        
        sendName() {
          debugger
            var name = document.getElementById('name').value;
            console.info(1111111111);
            this.stompClient.send("/app/queue", {}, JSON.stringify({ 'name': name}));
        },
 
  
    
  }
}

</script>
<style rel="stylesheet/scss" lang="scss">

</style>