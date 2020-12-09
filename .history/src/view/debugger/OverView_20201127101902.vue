<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
      <div class="containerContent" title="连接信息" style="width:auto; height:auto; float:left;">  
         <table>
              <tr>
                  <td>Moudle</td>
                  <td> <el-input v-model="moudle" :disabled="true"></el-input></td>
             <tr>
              <tr>
               <td>IpPort</td>
               <td><el-input v-model="ipPort" :disabled="true"></el-input></td>
             </tr>
              <tr>
               <td>FirVersion</td>
               <td><el-input v-model="firVersion" :disabled="true"></el-input></td>
             </tr>
              <tr>
               <td>DllVersion</td>
               <td><el-input v-model="dllVersion" :disabled="true"></el-input></td>
             </tr>
              <tr>
               <td>ChipID</td>
               <td><el-input v-model="chipId" :disabled="true"></el-input></td>
             </tr>
         </table>
      </div>
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
            <td><el-button type="primary" @click="readloacl()" plain>从本地读取</el-button></td>
            <td><el-button type="primary" @click="writeloacl()" plain>保存至本地</el-button></td>
            <td><el-button type="primary"  @click="readEquipment()" plain>从设备读取</el-button></td>
            <td><el-button type="primary" @click="writeEquipment()" plain>保存至设备</el-button></td>
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
      ip:'',
      port:'',
      moudle:0,
      ipPort:'未连接',
      firVersion:'未连接',
      dllVersion:'未连接',
      chipId:'未连接',
    }
  },
  
  mounted() {
 this.stompClient= this.$store.state.stompClient;
  },
  methods: {
     handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      readloacl(){
        this.stompClient.send('/app/readloacl')
      },
      writeloacl(){
         this.stompClient.send('/app/writeloacl')
      },
      readEquipment(){
        this.stompClient.send('/app/readEquipment')
      }
  },
   computed: {
    initinfo(){
         debugger
        return this.$store.state.initInfo
       },  
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
  },
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