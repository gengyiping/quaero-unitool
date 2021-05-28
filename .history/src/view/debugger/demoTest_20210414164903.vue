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
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
export default {
    data() {
      return {
       
      };
    },
    mounted:function(){
    
    },
   
    methods: {
      
    }
};
</script>
<style rel="stylesheet/scss" lang="scss">

</style>