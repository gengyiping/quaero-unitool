<template>
  <div class="" > 
      <div style="width:100%;height:600px;background: rgb(238, 255, 247);">
         <div class="container" title="基本操作" style="width:265px; height:auto; float:left;">
           <table class="basetable" style="margin-top:10px">
             <tr>
               <td><el-button type="primary"  @click="allRest('')" style="width: 126px;" plain>整机复位</el-button></td>
               <td><el-button type="primary" style="width: 126px;" plain>提篮在线确认</el-button></td>
             </tr>
              <tr>
               <td><el-button type="primary"  @click="allRest(0)" style="width: 126px;" plain>进样仓复位</el-button></td>
               <td><el-button type="primary" style="width: 126px;" plain>转移仓复位</el-button></td>
             </tr>
             <tr>
               <td><el-button type="primary"  @click="allRest(1)" style="width: 126px;" plain>回收仓复位</el-button></td>
               <td><el-button type="primary" style="width: 126px;" plain>递进电机复位</el-button></td>
             </tr>
             <tr>
               <td><el-button type="primary"  @click="allRest(2)" style="width: 126px;" plain>进样传送复位</el-button></td>
               <td><el-button type="primary" style="width: 126px;" plain>回收传送复位</el-button></td>
             </tr>
              <tr>
               <td><el-button type="primary"  @click="allRest(3)" style="width: 126px;" plain>换轨电机复位</el-button></td>
               <td></td>
             </tr>
              <tr>
               <td><el-input v-model="input" placeholder="请输入内容" ></el-input></td>
               <td><el-button type="primary" style="width: 126px;" plain>获取芯片ID</el-button></td>
             </tr>
              <tr>
               <td>
                 <el-select v-model="value" placeholder="请选择" style="width: 126px;">
                      <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 126px;" plain>开启</el-button></td>
             </tr>
             <tr>
               <td>
                 <el-select v-model="value" placeholder="请选择" style="width: 126px;">
                      <el-option
                          v-for="item in moveoptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 126px;" plain>区域转移</el-button></td>
             </tr>
           </table>
         </div>
          <div class="container" title="传感器查询" style="width:655px; height:auto;margin-left: 5px; float:left;">
             <el-form :model="sensorsearchForm" :rules="rules" ref="sensorsearchForm" label-width="273px" class="demo-ruleForm" style="margin-top: 20px;">
              <table style="margin-top:10px">
                <tr>
                  <td>进样提篮检测(提手)<br/>[D1-R1]</td>
                  <td><el-input v-model="sensorsearchForm.sensor1" placeholder="请查询结果"></el-input></td>
                  <td>进样提篮检测(篮体)<br/>[D2-R2]</td>
                  <td><el-input v-model="sensorsearchForm.sensor2" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>样本架出篮口检测<br/>[D3-R3]</td>
                  <td><el-input v-model="sensorsearchForm.sensor3" placeholder="请查询结果"></el-input></td>
                  <td>转移电机序号<br/>[D4-S3]</td>
                  <td><el-input v-model="sensorsearchForm.sensor4" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>回收仓电机序号<br/>[D6-S6]</td>
                  <td><el-input v-model="sensorsearchForm.sensor6" placeholder="请查询结果"></el-input></td>
                  <td>回收提篮检测(提手)<br/>[D7-R4]</td>
                  <td><el-input v-model="sensorsearchForm.sensor7" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>回收提篮检测(篮体)<br/>[D8-R5]</td>
                  <td><el-input v-model="sensorsearchForm.sensor8" placeholder="请查询结果"></el-input></td>
                  <td>样本架进篮检测<br/>[D9-R6]</td>
                  <td><el-input v-model="sensorsearchForm.sensor9" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>样本架满检测<br/>[D10-R7]</td>
                  <td><el-input v-model="sensorsearchForm.sensor10" placeholder="请查询结果"></el-input></td>
                  <td>进样样本架入口检测[D12-R8]</td>
                  <td><el-input v-model="sensorsearchForm.sensor12" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>进样样本架出口检测[D13-R9]</td>
                  <td><el-input v-model="sensorsearchForm.sensor13" placeholder="请查询结果"></el-input></td>
                  <td>回收样本架入口检测[D14-R10]</td>
                  <td><el-input v-model="sensorsearchForm.sensor14" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>回收样本架出口检测[D15-R11]</td>
                  <td><el-input v-model="sensorsearchForm.sensor15" placeholder="请查询结果"></el-input></td>
                  <td>递进电机序号[D16-S8]</td>
                  <td><el-input v-model="sensorsearchForm.sensor16" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>递进跟随到位检测[D17-R12]</td>
                  <td><el-input v-model="sensorsearchForm.sensor17" placeholder="请查询结果"></el-input></td>
                  <td>换轨电机序号<br/>[D18-S10]</td>
                  <td><el-input v-model="sensorsearchForm.sensor18" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td ></td>
                  <td >
                    <el-row style="margin-top: 10px;">
                      <el-button type="primary" @click="sensorsearch()" plain>&nbsp;&nbsp;查询&nbsp;&nbsp;</el-button>
                    </el-row>
                  </td>
                  <td>
                     <el-row style="margin-top: 10px;">
                      <el-button  @click="sensorEmpty()" plain>&nbsp;&nbsp;清空&nbsp;&nbsp;</el-button>
                    </el-row>
                  </td>
                  <td></td>
                </tr>
              </table>
              </el-form>
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
      options:[{
          value: 0,
          label: 'tftp服务'
        }],
      moveoptions:[{
          value: 0,
          label: '进样仓-转移仓'
        },{
          value: 1,
          label: '转移仓-换轨车'
        },{
          value: 2,
          label: '换轨车-递进电机'
        },{
          value: 3,
          label: '递进电机-回收入口'
        },{
          value: 4,
          label: '回收入口-回收提篮'
        }],
        sensorsearchForm:{

        }
    }
  },
  
  mounted() {
 this.stompClient= this.$store.state.stompClient;
  },
  methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      allRest(motorId) {
        console.log(key, keyPath);
      },
      sensorsearch(){
         this.stompClient.send('/app/sensoerSearch')
      },
      sensorEmpty(){
sensor1:''
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
    .basetable .el-button{
       .el-button--primary{
          width: 85px;
       }
      
    }
</style>