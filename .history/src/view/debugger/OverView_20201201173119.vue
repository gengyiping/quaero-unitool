<template>
  <div class=""  style="background: rgb(238, 255, 247);"> 
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
   components: {
    MotorList
    },
  mounted() {
 this.stompClient= this.$store.state.stompClient;
   // this.loadselect();//加载电机
   //this.motorId=this.$store.state.motorId;
  },
  methods: {
    coordLimit(ruleForm,opt){
      debugger
       var messageJson =""
      if(opt=='read'){
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId
          });
      }else{
        messageJson = JSON.stringify({
            coord:ruleForm.coord,
            acce:ruleForm.physicsCoord,
            motorId:this.$store.state.motorId
          });
      }
       this.stompClient.send('/app/coordLimit/'+opt,messageJson)
        Bus.$on('phymax',function(val){//监听first组件的txt事件
        debugger
           var op= val.split(';')
           if(op[2]=='read'){
            ruleForm.coord=op[0]
            ruleForm.physicsCoord=op[1]
           }
        })
    },
    outMove(outStepForm,opt){
      debugger;
       var messageJson =""
       if(opt=='read'){
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId
          });
      }else{
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId,
           coord:outStepForm.reverse,
            acce:outStepForm.forward
          });
      }
    this.stompClient.send('/app/outStepForm/'+opt,messageJson,{})
        Bus.$on('outMove',function(val){//监听first组件的txt事件
           var op= val.split(';')
           if(op[2]=='read'){
            outStepForm.reverse=op[0]
            outStepForm.forward=op[1]
           }
        })
    },
    zeroOpt(zeroForm,opt){
       var messageJson =""
       if(opt=='read'){
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId
          });
      }else{
         messageJson = JSON.stringify({
            motorId: this.$store.state.motorId,
           coord:zeroForm.zeroCoord,
          });
      }
    this.stompClient.send('/app/zeroOpt/'+opt,messageJson,{})
        Bus.$on('zeroOpt',function(val){//监听first组件的txt事件
           var op= val.split(';')
           if(op[1]=='read'){
            zeroForm.zeroCoord=op[0]
           }
        })
    },
    createMotorFile(){
       var messageJson= JSON.stringify({
            motorId: this.$store.state.motorId
          });
    this.stompClient.send('/app/createMotorFile',messageJson,{})
    },
   speedAccForm(speedForm,opt){
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
  }
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