<template>
  <div class="" > 
      <div style="width:100%;height:300px; background: red;">
          这是Test界面
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

  },
  methods: {
 handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
  },
  computed: {
    initinfo(){
         debugger
        return this.$store.state.initInfo
       },  
    stompinit(){
       return this.$store.state.stompClient
    }
  },
  watch:{
    initinfo(newVal,oldVal){
      debugger
      if(newVal!=''|newVal!=oldVal){
        var infoval=newVal.split('-')
        this.ipPort=infoval[0]
        this.firVersion=infoval[1]
        this.dllVersion=infoval[2]
        this.chipId=infoval[3]
        var ips=infoval[0].split(':')
        this.ip=ips[0]
        this.port=ips[1]
      }
    },
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