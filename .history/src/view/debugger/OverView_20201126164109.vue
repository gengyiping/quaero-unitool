<template>
  <div class="" > 
      <div style="width:100%;height:300px;background: rgb(238, 255, 247);">
          OverView界面
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

</style>