<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
     <el-button type="primary"  @click="testdemo()" style="width: 125px;" plain>测试demo按钮</el-button>
 </div> 
</template>


<script>
import SockJS from 'sockjs-client' 
import Stomp from 'webstomp-client'
export default {
    data() {
      return {
       stompClient:'',
      };
    },
    mounted(){
       this.stompClient= this.$store.state.stompClient //获取状态管理中的连接
    },
   
    methods: {
      testdemo(){
          
      }
    }
};
</script>
<style rel="stylesheet/scss" lang="scss">

</style>