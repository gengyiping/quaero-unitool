<template>
  <div class=""  style="background: rgb(238, 255, 247);"> 
       <div class="motorhead" >
           <MotorList height="100%" width="100%"/>
        </div>
         <div class="motorContent" >
         <div class="containerContent" title="读写电机信息" style="width:auto; height:auto;">
             <table>
               <tr>
                 <td>
                     <div class="container" title="电机最大可运行坐标" style="width:390px; height:auto;">
                         <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="margin-top: 20px;">
                               <table>
                                 <tr>
                                   <td>
                                       <el-form-item label="运行坐标限制" prop="coord" >
                                          <el-input v-model="ruleForm.coord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="readCoordLimit(ruleForm)" plain>读取</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                                 <tr>
                                   <td>
                                       <el-form-item label="物理坐标限制" prop="physicsCoord" >
                                          <el-input v-model="ruleForm.physicsCoord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="writeCoordLimit(ruleForm)" plain>写入</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                               </table>
                         </el-form>
                    </div>
                 </td>
                  <td>
                     <div class="container" title="电机最大允许失步数" style="width:390px; height:auto;">
                        <el-form :model="outStepForm" :rules="rules" ref="outStepForm" label-width="100px" class="demo-ruleForm" style="margin-top: 20px;">
                             <table>
                                 <tr>
                                   <td>
                                       <el-form-item label="电机正向失步" prop="coord" >
                                          <el-input v-model="outStepForm.forward"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="readOutMove(outStepForm)" plain>读取</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                                 <tr>
                                   <td>
                                       <el-form-item label="电机反向失步" prop="coord" >
                                          <el-input v-model="outStepForm.reverse"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="writeOutMove(outStepForm)" plain>写入</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                               </table>
                        </el-form>
                    </div>
                  </td>
               </tr>
                <tr>
                 <td>
                      <div class="container" title="电机零位传感器坐标" style="width:390px; height:auto;">
                      <el-form :model="zeroForm" :rules="rules" ref="zeroForm" label-width="100px" class="demo-ruleForm" style="margin-top: 20px;">
                             <table>
                                 <tr>
                                   <td>
                                       <el-form-item label="电机当前坐标" prop="coord" >
                                          <el-input v-model="zeroForm.zeroCoord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="readZeroOpt(zeroForm)" plain>读取</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                                 <tr>
                                   <td>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="writeZeroOpt(zeroForm)" plain>写入</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                               </table>
                        </el-form>
                    </div>
                 </td>
                  <td>
                       <div class="container" title="电机设置文件创建与上传" style="width:390px; height:auto;">
                       <el-form :model="fileForm" :rules="rules" ref="zeroForm" label-width="0px" class="demo-ruleForm" style="margin-top: 20px;">
                             <table>
                                 <tr>
                                    <td class="tdpad">
                                     <el-row>
                                        <el-button type="primary"  @click="createMotorFile()" plain>创建电机默认文件</el-button>
                                      </el-row>
                                   </td>
                                   <td>

               <div>
    <input type="file" @change="inputFileChange"> 
    <el-button type="primary" size="mini" @click="clicks">上传</el-button>
    <div id='dd' hidden></div>
  </div>
                                   </td>
                                  
                                   <td class="tdpad">
                                   </td>
                                 </tr>
                               </table>
                        </el-form>
                    </div>
                  </td>
               </tr>
                <tr>
                 <td colspan="2">
                      <div class="container" title="电机运动速度参数" style="width:550px; height:auto;">
                        <el-form :model="speedForm" :rules="rules" ref="speedForm" label-width="273px" class="demo-ruleForm" style="margin-top: 20px;">
                             <table class="spacc">
                                 <tr>
                                   <td>
                                       <el-form-item label="电机运动默认速度(p/sec, 1圈=3200p)" prop="coord" >
                                          <el-input v-model="speedForm.speedCoord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="readSpeedAccForm(speedForm)" plain>读取</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                                    <tr>
                                   <td>
                                       <el-form-item label="电机运动默认加速度(p/sec^2, 1圈=3200p)" prop="coord" >
                                          <el-input v-model="speedForm.accCoord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td class="tdpad">
                                      <el-row>
                                        <el-button type="primary"  @click="writeSpeedAcc(speedForm)" plain>写入</el-button>
                                      </el-row>
                                   </td>
                                 </tr>
                                  <tr>
                                   <td>
                                       <el-form-item label="电机复位默认速度(p/sec, 1圈=3200p)" prop="coord" >
                                          <el-input v-model="speedForm.resetCoord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td>
                                   </td>
                                 </tr>
                                 <tr>
                                   <td>
                                       <el-form-item label="电机复位默认加速度(p/sec^2, 1圈=3200p)" prop="coord" >
                                          <el-input v-model="speedForm.resetAccCoord"></el-input>
                                       </el-form-item>
                                   </td>
                                   <td>
                                   </td>
                                 </tr>
                               </table>
                        </el-form>
                    </div>
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
import MotorList from '@/view/debugger/motorList' 
import Bus from '../../components/bus' //跨页面自定义传值组件
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
       motorId:0,
       fileList: [],
       localFile:{},
       attachmentUrl: '',
       files:'',
       ruleForm: {
          coord: '',
          physicsCoord: ''
        },
        outStepForm:{
          reverse: '',
          forward: ''
        },
        zeroForm:{
          zeroCoord: ''
        },
        speedForm:{
           speedCoord: '',
           accCoord: '',
           resetCoord: '',
           resetAccCoord: '',
        },
        fileForm:{
           zeroCoord: ''
        },
        addFileName:'',
        addType:'',
        addId:'',
        addArr:[],
        baseread:''
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
    readCoordLimit(ruleForm){
       var messageJson  = JSON.stringify({
            motorId: this.$store.state.motorId
          });
       this.stompClient.send('/app/coordLimit/read',messageJson)
        Bus.$on('phymax',function(val){//监听first组件的txt事件
        debugger
           var op= val.split(';')
            ruleForm.coord=op[0]
            ruleForm.physicsCoord=op[1]
        })
    },
    writeCoordLimit(ruleForm){
      debugger
       var messageJson  = JSON.stringify({
            coord:ruleForm.coord,
            acce:ruleForm.physicsCoord,
            motorId:this.$store.state.motorId
          });
       this.stompClient.send('/app/coordLimit/write',messageJson)
    },
    readOutMove(outStepForm){
      debugger;
       var messageJson = JSON.stringify({
            motorId: this.$store.state.motorId
          });
    this.stompClient.send('/app/outStepForm/read',messageJson,{})
        Bus.$on('outMove',function(val){//监听first组件的txt事件
           var op= val.split(';')
            outStepForm.reverse=op[0]
            outStepForm.forward=op[1]
        })
    },
     writeOutMove(outStepForm){
      debugger;
       var messageJson =""
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId,
           coord:outStepForm.reverse,
            acce:outStepForm.forward
          });
    this.stompClient.send('/app/outStepForm/write',messageJson,{})
    },
    readZeroOpt(zeroForm){
       var messageJson  = JSON.stringify({
            motorId: this.$store.state.motorId
          });
    this.stompClient.send('/app/zeroOpt/read',messageJson,{})
        Bus.$on('zeroOpt',function(val){//监听first组件的txt事件
            zeroForm.zeroCoord=op[0]
        })
    },
     writeZeroOpt(zeroForm){
       var messageJson = JSON.stringify({
            motorId: this.$store.state.motorId,
           coord:zeroForm.zeroCoord,
          });
      this.stompClient.send('/app/zeroOpt/write',messageJson,{})
    },
    createMotorFile(){
       var messageJson= JSON.stringify({
            motorId: this.$store.state.motorId
          });
    this.stompClient.send('/app/createMotorFile',messageJson,{})
    },
   readSpeedAcc(speedForm){
       var messageJson =""
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId,
            acce:speedForm.speedCoord,
            speed:speedForm.accCoord,
            resetSpeed:speedForm.resetCoord,
            resetAcc:speedForm.resetAccCoord
          });
    this.stompClient.send('/app/speedAcc/read',messageJson,{})
         Bus.$on('speedAccList',function(val){//监听first组件的txt事件
         debugger
           var op= val.split(';')
              speedForm.speedCoord=op[0]
              speedForm.accCoord=op[1]
              speedForm.resetCoord=op[2]
              speedForm.resetAccCoord=op[3]
        })
    },
     writeSpeedAcc(speedForm){
       var messageJson =""
       if(opt=='read'){
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId
          });
      }else{
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId,
            acce:speedForm.speedCoord,
            speed:speedForm.accCoord,
            resetSpeed:speedForm.resetCoord,
            resetAcc:speedForm.resetAccCoord
          });
      }
    this.stompClient.send('/app/speedAcc/'+opt,messageJson,{})
         Bus.$on('speedAccList',function(val){//监听first组件的txt事件
         debugger
           var op= val.split(';')
           if(op[4]=='read'){
              speedForm.speedCoord=op[0]
              speedForm.accCoord=op[1]
              speedForm.resetCoord=op[2]
              speedForm.resetAccCoord=op[3]
           }
        })
    },
    
    inputFileChange(e) {
      debugger
      this.files = e.target.files[0]
       var imgName = this.files.name;
         var idx = imgName.lastIndexOf(".");  
          if (idx != -1){
                    var ext = imgName.substr(idx+1).toUpperCase();   
                    ext = ext.toLowerCase( ); 
                     if (ext!='json'){
                       
                    }else{
                          this.addArr.push(this.files);
                    }   
                }else{

                }
      this.reads(this.files);
    },
 reads(fil){
    var reader = new FileReader()
     reader.readAsText(fil)
     var text=''
    reader.onload = function()
    {
        debugger
        text=reader.result.split('}')[0]+"}"
        document.getElementById("dd").innerHTML += "<input id='upfile' value='"+text+"'>"
    }
      // if (fil.length > 1) {
      //                this.form.fileList = [fileList[fileList.length - 1]]  // 这一步，是 展示最后一次选择的json文件
      //             }
    //this.baseread=text
  },
    clicks() {
      debugger
       if(0 == this.addArr.length){
             this.$message({
               type: 'info',
               message: '请选择要上传的文件'
             });
             return;
           }
      if(this.files){
        console.log('baseread='+document.getElementById("upfile").defaultValue.split('}')[0]+"}")
         var messageJson= JSON.stringify({
            file:document.getElementById("upfile").defaultValue,
            fileName:this.files.name
          });
        this.stompClient.send('/app/upload',messageJson,{})
      }
    },
    getFile(event){
      debugger
           var file = event.target.files;
           for(var i = 0;i<file.length;i++){
            //    上传类型判断
               var imgName = file[i].name;
                var idx = imgName.lastIndexOf(".");  
                if (idx != -1){
                    var ext = imgName.substr(idx+1).toUpperCase();   
                    ext = ext.toLowerCase( ); 
                     if (ext!='json'){
                       
                    }else{
                          this.addArr.push(file[i]);
                    }   
                }else{

                }
           }
       },
       submitAddFile(){
         debugger;
           if(0 == this.addArr.length){
             this.$message({
               type: 'info',
               message: '请选择要上传的文件'
             });
             return;
           }

            var formData = new FormData();
            formData.append('num', this.addType);
            formData.append('linkId',this.addId);
            formData.append('rfilename',this.addFileName);
            for(var i=0;i<this.addArr.length;i++){
                formData.append('fileUpload',this.addArr[i]);
            }
          let config = {
            headers: {
              'Content-Type': 'multipart/form-data',
              'Authorization': this.token
            }
          };
          this.axios.post(apidate.uploadEnclosure,formData,config)
            .then((response) => {
                if(response.data.info=="success"){this.$message({
                        type: 'success',
                        message: '附件上传成功!'
                    });
                }
            })
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
  },
}

</script>
<style rel="stylesheet/scss" lang="scss">
  .motorhead{
width:100%;
height:auto;
min-height: 12px;
margin-left: 30px;
margin-top: 10px;
  }
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
    .tdpad{
padding-top: 20px;
}
    // .spacc{
// .el-form-item{
//       .el-form-item__label{
//            width: 246px !important;//清楚内联的100宽度的样式
//       }
//     }
//     } 
    
</style>