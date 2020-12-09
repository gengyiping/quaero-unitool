<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
      <div class="containerContent" title="Ip设置" style="width:200px;height:300px;">
        111111111
      </div>
      <div class="containerContent" title="读写电机信息" style="width:auto; height:auto;">  
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
  }
}

</script>
<style rel="stylesheet/scss" lang="scss">
 .container{
        position:relative;
        border:1px solid #DCDFE6;
        margin-top: 20px;
       
    }
    .container::before{
        content:attr(title);
        position:absolute;
        left:20%;
        transform:translateX(-50%);
        -webkit-transform:translate(-50%,-50%);
        padding:0 10px;
        background-color:#fff;
    }
</style>