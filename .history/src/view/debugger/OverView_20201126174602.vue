<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
      <div class="containerContent" title="Ip配置" style="width:auto;height:300px; float:left;">
        <table>
          <tr>
            <td>IP</td>
            <td colspan="3"><el-input
       placeholder="请输入内容"
             v-model="ip"
                  clearable>
          </el-input>
            </td>
          </tr>
          <tr>
            <td>
              端口
            </td>
            <td colspan="3">
              <el-input
             placeholder="请输入内容"
             v-model="port"
                  clearable>
          </el-input>
            </td>
          </tr>
          <tr>
            <td><el-button type="primary" plain>从本地读取</el-button></td>
            <td><el-button type="primary" plain>保存至本地</el-button></td>
            <td><el-button type="primary" plain>从设备读取</el-button></td>
            <td><el-button type="primary" plain>保存至设备</el-button></td>
          </tr>
        </table>
      </div>
      <div class="containerContent" title="连接信息" style="width:auto; height:auto; float:left;">  
         <table>
              <tr>
                  <td>Moudle</td>
                  <td> <el-input
             placeholder="请输入内容"
             v-model="moudle"
                  >
          </el-input></td>
             <tr>
             <tr>
               <td>MB</td>
               <td></td>
             </tr>
              <tr>
               <td>IpPort</td>
               <td></td>
             </tr>
              <tr>
               <td>FirVersion</td>
               <td></td>
             </tr>
              <tr>
               <td>DllVersion</td>
               <td></td>
             </tr>
              <tr>
               <td>ChipID</td>
               <td></td>
             </tr>
         </table>
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
      ip:''
    }
  },
  
  mounted() {
 this.stompClient= this.$store.state.stompClient;
  },
  methods: {
 handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
  }
}

</script>
<style rel="stylesheet/scss" lang="scss">
  .motorContent{
width:100%;
height: 740px;
margin-top: 10px;
background: rgb(238, 255, 247);
  }
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