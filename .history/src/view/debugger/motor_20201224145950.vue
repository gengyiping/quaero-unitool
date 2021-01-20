<template >
  <div class="" style="background: rgb(238, 255, 247);" > 
       <div class="motorhead" >
           <MotorList height="100%" width="100%"/>
        </div>
      <div class="motorContent" >    
          <div class="container" title="电机控制" style="width:475px; height:auto; float:left;">
                  <div class="container" title="单步控制" style="width:auto; height:auto;">
                 <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="margin-top: 20px;">
                   <table>
                      <tr  >
                        <td>
                                <el-form-item label="步数" prop="coord" :rules="[{ type: 'number', message: '步数必须为数字值'}]">
                                      <el-input v-model="ruleForm.coord" ></el-input>
                                 </el-form-item>
                        </td>
                        <td class="tdpad">
                                <el-row>
                                      <el-button type="primary"  @click="motorcontrol(ruleForm,'backZero')" plain>零位←</el-button>
                                   </el-row>
                        </td>
                         <td class="tdpad">
                                    <el-row>
                                       <el-button type="primary"  @click="motorcontrol(ruleForm,'farZero')" plain>零位→</el-button>
                                   </el-row>
                        </td>
                       </tr>
                       <tr>
                           <td> 
                              <el-form-item label="速度" prop="speed" :rules="[{ required: true, message: '速度不能为空'},{ type: 'number', message: '速度必须为数字值'}]">
                                       <el-input v-model="ruleForm.speed"></el-input>
                                 </el-form-item>
                            </td>
                            <td class="tdpad">
                               <el-row>
                                   <el-button type="primary" @click="motorcontrol(ruleForm,'moveGo')" plain>&nbsp;&nbsp;GO&nbsp;&nbsp;</el-button>
                              </el-row>
                            </td>
                             <td class="tdpad">
                               <el-row>
                              <el-button type="primary" @click="motorcontrol(ruleForm,'seekZero')" plain>Home</el-button>
                                  </el-row>
                            </td>
                       </tr>
                        <tr>
                           <td> 
                                 <el-form-item label="加速度" prop="acce" :rules="[{ required: true, message: '加速度不能为空'},{ type: 'number', message: '加速度必须为数字值'}]">
                                       <el-input v-model="ruleForm.acce"></el-input>
                                 </el-form-item>
                            </td>
                            <td colspan="2" class="tdpad">
                               <el-row>
                                  <el-button type="primary" @click="motorcontrol(ruleForm,'reset')" plain>&thinsp; &nbsp;&nbsp;&nbsp;&nbsp;Reset&thinsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
                              </el-row>
                            </td>
                       </tr>
                        <tr>
                           <td> 
                                 <el-form-item label="连续单步" >
                                         <el-switch v-model="ruleForm.series"></el-switch>
                                 </el-form-item>
                            </td>
                            <td colspan="2">
                               <el-row class="tdpad">
                                  <el-button type="primary" @click="motorcontrol(ruleForm,'quickReset')" plain>QuickReset</el-button>
                              </el-row>
                            </td>
                       </tr>
                   </table>
                 </el-form>
                 </div> 
                   <div class="container" title="传感器触发急停" style="width:auto; height:auto;">
                        <el-form :model="sensorForm" :rules="rules" ref="sensorForm" label-width="100px" class="demo-ruleForm" style="margin-top: 20px;">
                            <table>
                              <tr>
                                <td>
                                    <el-form-item label="传感Id" prop="sensorId" :rules="[{ required: true, message: '速度不能为空'},{ type: 'number', message: '速度必须为数字值'}]">
                                      <el-input-number v-model="sensorForm.sensorId"  @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
                                    </el-form-item>
                                </td>
                                <td class="tdpad">
                                    <el-row>
                                      <el-button type="primary" @click="sensorStop(sensorForm,'moveSensorStop')" plain>MoveSensorStop</el-button>
                                   </el-row>
                                </td>
                              </tr>
                               <tr>
                                <td>
                                  <el-form-item label="步数" prop="coord" :rules="[{ required: true, message: '速度不能为空'},{ type: 'number', message: '速度必须为数字值'}]">
                                      <el-input v-model="sensorForm.coord" style="width:180px"></el-input>
                                 </el-form-item>
                                </td>
                                <td class="tdpad">
                                    <el-row>
                                      <el-button type="primary" @click="sensorStop(sensorForm,'stepSensorStop')" plain>StepSensorStop</el-button>
                                   </el-row>
                                </td>
                              </tr>
                            </table>
                         </el-form>
                  </div> 
                   <div class="container" title="走计数凹槽口" style="width:auto; height:auto;">
                     <el-form :model="grooveForm" :rules="rules" ref="grooveForm" label-width="100px" class="demo-ruleForm" style="margin-top: 20px;">
                            <table>
                              <tr>
                                <td>
                                    <el-form-item label="凹槽Id" prop="sensorId" >
                                      <el-input-number v-model="grooveForm.sensorId"  @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
                                    </el-form-item>
                                </td>
                                <td class="tdpad">
                                    <el-row>
                                      <el-button type="primary" @click="grooveMove(grooveForm)" plain>Move</el-button>
                                   </el-row>
                                </td>
                              </tr>
                               <tr>
                                <td>
                                  <el-form-item label="步数" prop="coord" >
                                      <el-input v-model="grooveForm.coord" style="width:180px"></el-input>
                                 </el-form-item>
                                </td>
                                <td>
                                </td>
                              </tr>
                            </table>
                     </el-form>
                  </div> 
          </div>  
          <div class="container" title="电机信息" style="width:425px; height:auto;float:left;margin-left: 10px;">
                 <div  class="container"  title="读取电机信息" style="margin-top: 18px;height:60px;">
                     <div style="margin-top: 32px;">
                          <el-row style="float:left;margin-left: 10px;">
                      <el-button type="primary" @click="getCoord()"  plain>读取当前坐标</el-button>
                      </el-row>
                      <el-row  style="float:left;margin-left: 10px;">
                        <el-button type="primary" @click="steplost()" plain>读取失步数</el-button>
                      </el-row>
                      <el-row  style="float:left;margin-left: 10px;">
                      <el-button type="primary" @click="motorFlag()" plain>读取状态标志</el-button>
                     </el-row>
                   </div>
                 </div>
                 <div class="container"  title="电机配置" style="height:auto;"> 
                      <el-form :inline="true" :model="formInline" class="demo-form-inline" style="margin-top: 10px;text-align: right;">
                           <el-form-item label="细分数：">
                              <!--  <el-select v-model="formInline.cmshift" placeholder="NOCHANGE">
                                <el-option label="STEP_1" value="STEP_1"></el-option>
                                  <el-option label="STEP_2" value="STEP_2"></el-option>
                                  <el-option label="STEP_4" value="STEP_4"></el-option>
                                 <el-option label="STEP_8" value="STEP_8"></el-option>
                                  <el-option label="STEP_16" value="STEP_16"></el-option>
                                  <el-option label="NOCHANGE" value="NOCHANGE"></el-option> 
                              </el-select>-->
                              <el-select v-model="formInline.cmshift" placeholder="NOCHANGE">
                                  <el-option v-for="item in cmshifts" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                             <el-form-item label="是否启用紧停信号：">
                              <el-select v-model="formInline.cmstopEna" placeholder="NOCHANGE">
                                 <!-- <el-option label="DIS" value="DIS"></el-option>
                                  <el-option label="ENA" value="ENA"></el-option>
                                  <el-option label="NOCHANGE" value="NOCHANGE"></el-option> -->
                                  <el-option v-for="item in disEna" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                            <el-form-item label="启用防撞保护传感器：">
                              <el-select v-model="formInline.cmemmrEna" placeholder="NOCHANGE">
                                 <el-option v-for="item in disEna" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>

                            </el-form-item>
                             <el-form-item label="防撞传感器触发电平：">
                              <el-select v-model="formInline.cmemmrtrigger" placeholder="NOCHANGE">
                                 <el-option v-for="item in lowHeight" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                             <el-form-item label="电机是否是圆盘电机：">
                              <el-select v-model="formInline.cmRotate" placeholder="NOCHANGE">
                                <el-option v-for="item in noYes" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                              <el-form-item label="零位传感器是否具有开关：">
                              <el-select v-model="formInline.cmZeroSwitch" placeholder="NOCHANGE">
                                 <el-option v-for="item in disEna" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                            <el-form-item label="零位传感器使能电平：">
                              <el-select v-model="formInline.cmZeroEna" placeholder="NOCHANGE">
                                 <el-option v-for="item in lowHeight" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                             <el-form-item label="零位传感器触发电平：">
                              <el-select v-model="formInline.cmZeroTrigger" placeholder="NOCHANGE">
                                 <el-option v-for="item in lowHeight" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                             <el-form-item label="电机使能电平：">
                              <el-select v-model="formInline.cmMotorEna" placeholder="NOCHANGE">
                                 <el-option v-for="item in lowHeight" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                            <el-form-item label="电机全电流电平：">
                              <el-select v-model="formInline.cmMotorCurr" placeholder="NOCHANGE">
                                 <el-option v-for="item in lowHeight" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                             <el-form-item label="电机时钟触发逻辑：">
                              <el-select v-model="formInline.cmMotorClk" placeholder="NOCHANGE">
                                <el-option v-for="item in fallingRising" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                             <el-form-item label="电机正方向电平：">
                              <el-select v-model="formInline.cmMotorForward" placeholder="NOCHANGE">
                                 <el-option v-for="item in lowHeight" :key="item.value" :label="item.label" :value="item.value">
                                  </el-option>
                              </el-select>
                            </el-form-item>
                            
                             <el-form-item>
                                <el-button type="primary" @click="writeInlineopt(formInline)">写入</el-button>
                                 <el-button type="primary" @click="searchInlineopt(formInline)">查询</el-button>
                             </el-form-item>
                      </el-form>
                 </div>
          </div>   
      </div>
      
</div> 
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Bus from '../../components/bus' //跨页面自定义传值组件
import MotorList from '@/view/debugger/motorList' 
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      kuList:null,
      motorId:0,
      ruleForm: {
          coord: 1000,
          speed: 3000,
          acce: 5000,
          series: false
        },
        sensorForm:{
          sensorId: '',
           coord: 1000,
        },
        grooveForm:{
          sensorId: '',
          coord: 1000,
        },
       formInline: {
          cmshift: '',
          cmstopEna: '',
          cmemmrEna: '',
          cmemmrtrigger: '',
          cmRotate: '',
          cmZeroSwitch: '',
          cmZeroEna: '',
          cmZeroTrigger: '',
          cmMotorEna: '',
          cmMotorCurr: '',
          cmMotorClk: '',
          cmMotorForward: ''
        },
        cmshifts:[{
          value: 0,
          label: 'STEP_1'
        }, {
          value: 1,
          label: 'STEP_2'
        }, {
          value: 2,
          label: 'STEP_4'
        }, {
          value: 3,
          label: 'STEP_8'
        }, {
          value: 4,
          label: 'STEP_16'
        }, {
          value: 5,
          label: 'NOCHANGE'
        }],
        disEna:[{
          value: 0,
          label: 'DIS'
        }, {
          value: 1,
          label: 'ENA'
        }, {
          value: 2,
          label: 'NOCHANGE'
        }],
        lowHeight:[{
          value: 0,
          label: 'LOW'
        }, {
          value: 1,
          label: 'HEIGHT'
        }, {
          value: 2,
          label: 'NOCHANGE'
        }],
        noYes:[{
          value: 0,
          label: 'NO'
        }, {
          value: 1,
          label: 'YES'
        }, {
          value: 2,
          label: 'NOCHANGE'
        }],
        fallingRising:[{
          value: 0,
          label: 'FALLING'
        }, {
          value: 1,
          label: 'RISING'
        }, {
          value: 2,
          label: 'NOCHANGE'
        }],
    }
  },
   components: {
    MotorList
    },
  mounted() {
    this.stompClient= this.$store.state.stompClient;
   // this.loadselect();//加载电机
   this.motorId=this.$store.state.motorId;
    
  },
  methods: {
    motorcontrol(ruleForm,direction){//direction 电机动作
      Bus.$emit('progres',true) 
     this.$store.state.resinfo="电机运行开始"
    debugger
       console.log(direction)
         var messageJson = JSON.stringify({
            coord:ruleForm.coord,speed:ruleForm.speed,acce:ruleForm.acce,motorId:this.$store.state.motorId
          });
         console.log("----allmotor----"+messageJson);
       if(ruleForm.series==true&direction!='quickReset'&direction!='reset'&direction!='seekZero'){
          window.setInterval(()=>{
              inm++;
               console.log(inm+"---连续-direction----"+direction);
               this.stompClient.send('/app/motorcontrol/'+direction,messageJson,{})
               if(this.ruleForm.series)return;
          }, 3000);
       }else{
          console.log("----direction----"+direction);
            this.stompClient.send('/app/motorcontrol/'+direction,messageJson,{})
       }
          var _this = this;// `这一步很重要`
    },
    getCoord(){//获取当前坐标
      Bus.$emit('progres',true) 
     this.$store.state.resinfo="获取当前坐标开始"
       this.stompClient.send('/app/getCoord',this.$store.state.motorId,{})
    },
    steplost(){//获取失步数
      this.$store.state.resinfo="获取失步数开始"
      this.stompClient.send('/app/steplost',this.$store.state.motorId,{})
    },
    motorFlag(){//获取状态标志
     this.$store.state.resinfo="获取状态标志开始"
      this.stompClient.send('/app/motorFlag',this.$store.state.motorId,{})
    },
    sensorStop(sensorForm,sensorStop){//运动根据传感器停
    this.$store.state.resinfo="运动根据传感器停开始"
     var messageJson = JSON.stringify({
            coord:sensorForm.coord,
            sensorId:sensorForm.sensorId,
            motorId:this.$store.state.motorId
          });
        this.stompClient.send('/app/sensorStop/'+sensorStop,messageJson,{})
    },
    grooveMove(grooveForm){//走计数凹槽
     this.$store.state.resinfo="走计数凹槽开始"
     var messageJson = JSON.stringify({
            coord:grooveForm.coord,
            sensorId:grooveForm.sensorId,
            motorId:this.$store.state.motorId
          });
        this.stompClient.send('/app/grooveMove',messageJson,{})
    },
   searchInlineopt(formInline){//查询配置
    this.$store.state.resinfo="查询电机配置开始"
     var messageJson = JSON.stringify({
           motorId:this.$store.state.motorId
          });
        this.stompClient.send('/app/searchInlineopt',messageJson,{})
        var _this = this;// `这一步很重要`
         Bus.$on('searchInlineopt',function(val){//监听first组件的txt事件
         debugger;
        var vk= JSON.parse(val)
        if(vk.cmshift>0&vk.cmshift<5){
          formInline.cmshift=vk.cmshift
        }else{
          formInline.cmshift=5
        }
        if(vk.cmstopEna<2){
          formInline.cmstopEna=vk.cmstopEna
        }else{
          formInline.cmstopEna=2
        }
         if(vk.cmemmrEna<2){
          formInline.cmemmrEna=vk.cmemmrEna
        }else{
          formInline.cmemmrEna=2
        }
         if(vk.cmemmrtrigger<2){
           formInline.cmemmrtrigger=vk.cmemmrtrigger
        }else{
          formInline.cmemmrtrigger=2
        }
         if(vk.cmRotate<2){
           formInline.cmRotate=vk.cmRotate
        }else{
          formInline.cmRotate=2
        }
        if(vk.cmZeroSwitch<2){
           formInline.cmZeroSwitch=vk.cmZeroSwitch
        }else{
          formInline.cmZeroSwitch=2
        }
        if(vk.cmZeroEna<2){
           formInline.cmZeroEna=vk.cmZeroEna
        }else{
          formInline.cmZeroEna=2
        }
         if(vk.cmZeroTrigger<2){
          formInline.cmZeroTrigger=vk.cmZeroTrigger
        }else{
          formInline.cmZeroTrigger=2
        }
        if(vk.cmMotorEna<2){
           formInline.cmMotorEna=vk.cmMotorEna
        }else{
          formInline.cmMotorEna=2
        }
        if(vk.cmMotorCurr<2){
          formInline.cmMotorCurr=vk.cmMotorCurr
        }else{
          formInline.cmMotorCurr=2
        }
        if(vk.cmMotorClk<2){
           formInline.cmMotorClk=vk.cmMotorClk
        }else{
          formInline.cmMotorClk=2
        }
        if(vk.cmMotorForward<2){
          formInline.cmMotorForward=vk.cmMotorForward
        }else{
          formInline.cmMotorForward=2
        }
          });
    },
    writeInlineopt(formInline){
    this.$store.state.resinfo="保存电机配置开始"
     var messageJson = JSON.stringify({
             cmshift: formInline.cmshift,
          cmstopEna: formInline.cmstopEna,
          cmemmrEna: formInline.cmemmrEna,
          cmemmrtrigger: formInline.cmemmrtrigger,
          cmRotate: formInline.cmRotate,
          cmZeroSwitch: formInline.cmZeroSwitch,
          cmZeroEna: formInline.cmZeroEna,
          cmZeroTrigger: formInline.cmZeroTrigger,
          cmMotorEna: formInline.cmMotorEna,
          cmMotorCurr: formInline.cmMotorCurr,
          cmMotorClk: formInline.cmMotorClk,
          cmMotorForward: formInline.cmMotorForward,
           motorId:this.$store.state.motorId
          });
        this.stompClient.send('/app/writeInlineopt',messageJson,{})
    },
    handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
    handleCommand(command) {
        this.$message('click on item ' + command);
      }
  },
  computed: {
    stompinit(){
       return this.$store.state.stompClient
    },
   
  },
  watch:{
    stompinit(newVal,oldVal){
      this.stompClient=newVal
    },
     'ruleForm.series':{
       handler(newVal, oldVal) {
            console.log(newVal+":"+oldVal);
        },
        deep: true
     }
  },
}
</script>
<style rel="stylesheet/scss" lang="scss">
.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
  .motorhead{
width:100%;
height:auto;
min-height: 12px;
margin-left: 30px;
margin-top: 10px;
  }
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
    .el-form-item {
     margin-bottom: 3px;
}
.tdpad{
padding-top: 20px;
}

// .el-form-item{

// .content:disabled{
// margin-left: 0px;
// }
// }
</style>