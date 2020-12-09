<template>
  <div class=""  style="background: rgb(238, 255, 247);"> 
            <div class="containerContent" title="连接信息" style="width:auto; height:auto; float:left;">  
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