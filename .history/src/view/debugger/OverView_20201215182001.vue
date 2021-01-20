<template>
  <div class=""  style="background: rgb(238, 255, 247);"> 
            <div class="containerContent" title="连接信息" style="width:auto; height:auto; float:left;">  
         <table style="margin-top: 10px;">
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
              <tr>
               <td colspan="2"><el-button type="primary" @click="flushinfo()" plain>刷新</el-button></td>
             </tr>
         </table>
      </div>
      <div class="containerContent" title="Ip配置" style="width:auto;height:144px; float:left;">
        <table style="margin-top: 10px;">
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
                  :disabled="true" >
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
import MotorList from '@/view/debugger/motorList' 
import Bus from '../../components/bus' //跨页面自定义传值组件
export default {
  data() {
    return {
        list1: [],
      stompClient: null,
      ip:'',
      port:'11223',
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
    flushinfo(){
       this.$store.state.resinfo="获取连接信息开始"
      this.stompClient.send('/app/initInfo')//获取连接信息
    },
     handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      readloacl(){
         this.$store.state.resinfo="从本地读取IP开始"
        var _this=this
        this.stompClient.send('/app/readloacl')
         Bus.$on('readloacl',function(val){//监听fi
         debugger
           var ips=val.split(':')
            _this.ip=ips[0]
            _this.port=ips[1]
         })
      },
      writeloacl(){
        this.$store.state.resinfo="保存Ip至设备开始"
         var _this=this
         var iport=_this.ip+':'+_this.port
         this.stompClient.send('/app/writeloacl/'+ iport)
      },
      readEquipment(){
         var _this=this
        this.stompClient.send('/app/readEquipment')
        Bus.$on('readEquipment',function(val){//监听fi
        debugger
            _this.ip=val
            _this.port=11223
         })
      },
      writeEquipment(){
         var _this=this
           var iport=_this.ip+':'+_this.port
        this.stompClient.send('/app/writeEquipment/'+ iport)
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