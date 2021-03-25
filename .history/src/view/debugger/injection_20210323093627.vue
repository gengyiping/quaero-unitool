<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
      <div style="width:1080px;height:1200px;background: rgb(238, 255, 247);">
         <div class="containerd" title="基本操作" style="width:300px; height:auto; float:left;">
           <table class="basetable" style="margin-top: 12px">
             <tr>
               <td><el-button type="primary"  @click="allRest(5)" style="width: 125px;" plain>整机复位</el-button></td>
               <td><el-button type="primary" style="width: 125px;" @click="basket()" plain>提篮在线确认</el-button></td>
             </tr>
              <tr>
               <td><el-button type="primary"  @click="allRest(3)" style="width: 125px;" plain>进样仓复位</el-button></td>
               <td><el-button type="primary"  @click="allRest(1)" style="width: 125px;" plain>转移仓复位</el-button></td>
             </tr>
             <tr>
               <td><el-button type="primary"  @click="allRest(2)" style="width: 125px;" plain>回收仓复位</el-button></td>
               <td><el-button type="primary"  @click="allRest(6)" style="width: 125px;" plain>递进电机复位</el-button></td>
             </tr>
             <tr>
               <td><el-button type="primary"  @click="allRest(0)" style="width: 125px;" plain>急诊电机复位</el-button></td>
               <td><el-button type="primary"  @click="allRest(7)" style="width: 125px;" plain>阻挡电机复位</el-button></td>
             </tr>
              <tr>
               <td><el-button type="primary"  @click="allRest(8)" style="width: 125px;" plain>换轨电机复位</el-button></td>
               <td><el-button type="primary" @click="getChip()" style="width: 125px;"    plain>获取芯片ID</el-button></td>
             </tr>
              <tr>
               <td colspan="2" ><el-input v-model="chid" placeholder="获取芯片ID" style="width: 92%;"></el-input></td>
             </tr>
              <tr>
               <td>
                 <el-select v-model="servalue" placeholder="请选择" style="width: 125px;" @change="serEdit"> 
                      <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 125px;"  @click="openServer()" plain>{{openval}}</el-button></td>
             </tr>
              <tr>
               <td colspan="2" ><el-input v-model="barcode" placeholder="设置条码仪参数" style="width: 92%;"></el-input></td>
             </tr>
              <tr>
               <td colspan="2" ><el-button type="primary" style="width: 92%;" @click="setBarCode()" plain>设置条码仪参数</el-button></td>
             </tr>
             <tr>
               <td>
                 <el-select v-model="selVal" placeholder="请选择" style="width: 125px;"  @change="fnEdit">
                      <el-option
                          v-for="item in moveoptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 125px;" @click="transferMove()" plain>区域转移</el-button></td>
             </tr>
              <tr>
               <td>
                 <el-select v-model="ledval" placeholder="请选择" style="width: 125px;"  @change="ledEdit">
                      <el-option
                          v-for="item in ledoptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 125px;" @click="ledcontrl()" plain>LED控制</el-button></td>
             </tr>
              <tr>
               <td><el-input v-model="heightlight" placeholder="亮灯时间" style="width: 85%;"></el-input></td>
               <td><el-input v-model="lowlight" placeholder="灭灯时间" style="width: 85%;"></el-input></td>

             </tr>
              <tr>
               <td><el-input v-model="bootIp" placeholder="Bootload Ip" style="width: 125px;"></el-input></td>
               <!-- <td><el-button type="primary" style="width: 125px;" @click="openfirupdate()" plain>开启固件更新</el-button></td> -->
              <td><el-button type="primary" style="width: 125px;" @click="firclicks" plain>开启固件更新</el-button></td>

             </tr>
            
             <tr>
               <td colspan="2">
                  <!-- <div> -->
                      <input type="file"  @change="firfileChange($event)"> 
                        <!-- <el-button type="primary"  @click="firclicks">固件更新</el-button> -->
                       <div id='firdd' hidden></div>
                   <!-- </div> -->
                </td>
             </tr>
             <tr>
               <td > </td>
               <td >
                 <el-button type="primary" style="width: 125px;" @click="initialUpdate()" plain>首次更新</el-button>
                </td>
             </tr>
            
           </table>
            <!-- <el-button type="primary" icon="el-icon-upload" class="mr10" @click="uploadFiles()">文件上传</el-button>
            <el-dialog v-dialogDrag title="文件上传" center :visible.sync="uploadVisible" width="60%" :close="handlerClose">
            <UploadBigFile class="uploadSlot"></UploadBigFile>
        </el-dialog> -->
         </div>
          <div class="containerd" title="传感器查询" style="width:655px; height:auto;margin-left: 5px; float:left;">
             <el-form :model="sensorsearchForm"  ref="sensorsearchForm" label-width="273px" class="demo-ruleForm" style="margin-top: 20px;">
              <table style="margin-top:10px;text-align:left;">
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
                  <td>回收仓电机序号<br/>[D5-S6]</td>
                  <td><el-input v-model="sensorsearchForm.sensor5" placeholder="请查询结果"></el-input></td>
                  <td>回收提篮检测(提手)<br/>[D6-R4]</td>
                  <td><el-input v-model="sensorsearchForm.sensor6" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>回收提篮检测(篮体)<br/>[D7-R5]</td>
                  <td><el-input v-model="sensorsearchForm.sensor7" placeholder="请查询结果"></el-input></td>
                  <td>样本架进篮检测<br/>[D8-R6]</td>
                  <td><el-input v-model="sensorsearchForm.sensor8" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>样本架满检测<br/>[D9-R7]</td>
                  <td><el-input v-model="sensorsearchForm.sensor9" placeholder="请查询结果"></el-input></td>
                  <td>进样样本架入口检测[D12-R8]</td>
                  <td><el-input v-model="sensorsearchForm.sensor12" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>进样样本架出口检测<br/>[D13-R9]</td>
                  <td><el-input v-model="sensorsearchForm.sensor13" placeholder="请查询结果"></el-input></td>
                  <td>回收样本架入口检测<br/>[D14-R10]</td>
                  <td><el-input v-model="sensorsearchForm.sensor14" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>回收样本架出口检测<br/>[D15-R11]</td>
                  <td><el-input v-model="sensorsearchForm.sensor15" placeholder="请查询结果"></el-input></td>
                  <td>递进电机序号<br/>[D16-S8]</td>
                  <td><el-input v-model="sensorsearchForm.sensor16" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>递进跟随到位检测[D17-R12]</td>
                  <td><el-input v-model="sensorsearchForm.sensor17" placeholder="请查询结果"></el-input></td>
                  <td>换轨阻挡电机序号<br/>[D18-S10]</td>
                  <td><el-input v-model="sensorsearchForm.sensor18" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>换轨车电机序号<br/>[D19-S12]</td>
                  <td><el-input v-model="sensorsearchForm.sensor19" placeholder="请查询结果"></el-input></td>
                  <td>急诊电机序号<br/>[D21-S13]</td>
                  <td><el-input v-model="sensorsearchForm.sensor21" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>急诊位检测<br/>[D22-R13]</td>
                  <td><el-input v-model="sensorsearchForm.sensor22" placeholder="请查询结果"></el-input></td>
                  <td>转移仓到位检测<br/>[D23-R14]</td>
                  <td><el-input v-model="sensorsearchForm.sensor23" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>磁铁检测<br/>[D24-R15]</td>
                  <td><el-input v-model="sensorsearchForm.sensor24" placeholder="请查询结果"></el-input></td>
                  <td></td>
                  <td></td>
                </tr>
                 <tr>
                  <td ></td>
                  <td >
                    <el-row style="margin-top: 10px;">
                      <el-button type="primary" @click="sensorsearch(sensorsearchForm)" plain>&nbsp;&nbsp;查询&nbsp;&nbsp;</el-button>
                    </el-row>
                  </td>
                  <td>
                     <el-row style="margin-top: 10px;">
                      <el-button  @click="sensorEmpty(sensorsearchForm)" plain>&nbsp;&nbsp;清空&nbsp;&nbsp;</el-button>
                    </el-row>
                  </td>
                  <!-- <td>
                     <el-row style="margin-top: 10px;">
                      <el-button type="primary" @click="readwrite()" plain>测试读写文件</el-button>
                    </el-row>
                    </td> -->
                </tr>
              </table>
              </el-form>
          </div>
          <div>
             <img :src="imgUrl" style="">
          </div>
          
      </div>
      
</div> 
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Bus from '../../components/bus' //跨页面自定义传值组件
//import UploadBigFile from './Upload';
import {binaryToStr} from '../../components/binaryToStr'
import iconv from 'iconv-lite'
//import axios from 'axios'
//import VueAxios from 'vue-axios'
export default {
  data() {
    return {
      barcode:'',
       uploadVisible: false,
       imgUrl:require("../../../static/images/QcitS.png"),
      list1: [],
      stompClient: null,
      options:[{
          value: 0,
          label: 'tftp服务'
        },{
          value: 1,
          label: '开启轨道远程'
        },{
          value: 2,
          label: '扫码灯'
        }
       ],
      ledoptions:[{
          value: 1,
          label: '1-灯'
        },{
          value: 2,
          label: '2-灯'
        },{
          value: 3,
          label: '3-灯'
        },{
          value: 4,
          label: '4-灯'
        },{
          value: 5,
          label: '5-灯'
        },{
          value: 6,
          label: '6-灯'
        },{
          value: 7,
          label: '7-灯'
        },{
          value: 15,
          label: '15-进样灯'
        },{
          value: 16,
          label: '16-回收灯'
        },{
          value: 17,
          label: '17-Stop灯'
        },{
          value: 19,
          label: '19-start灯'
        }
       ],
      moveoptions:[{
          value: 0,
          label: '进样仓-进样出口检测'
        },{
          value: 1,
          label: '进样出口检测-转移仓'
        },{
          value: 2,
          label: '急诊位-转移仓'
        },{
          value: 3,
          label: '转移仓-转移扫码'
        },{
          value: 4,
          label: '转移扫码-进样皮带入口'
        },{
          value: 5,
          label: '进样皮带入口-换轨车'
        },{
          value: 6,
          label: '进样皮带-返回皮带'
        },{
          value: 7,
          label: '返回皮带-递进初始位'
        },{
          value: 8,
          label: '递进-格'
        },{
          value: 9,
          label: '递进位-返回皮带入口'
        },{
          value: 10,
          label: '回收入口-回收提篮'
        }],
        sensorsearchForm:{
        sensor1:'',
        sensor2:'',
        sensor3:'',
        sensor4:'',
        sensor5:'',
        sensor6:'',
        sensor7:'',
        sensor8:'',
        sensor9:'',
        sensor12:'',
        sensor13:'',
         sensor14:'',
        sensor15:'',
         sensor16:'',
        sensor17:'',
        sensor18:'',
        sensor19:'',
        sensor21:'',
        sensor22:'',
         sensor23:'',
         sensor24:''
        },
        chid:'',
        selVal:'',
        servalue:'',
        ledval:'',
        openval:'开启',
        tftpval:'开启',//tftp服务
        closeval:'开启',//远程服务
        scanval:'开启',//条码仪状态
        bootIp:'192.168.3.220',
        firfiles:'',
        addArrs:[],
        heightlight:'1000',
        lowlight:'0'
    }
  },
  // components: {
  //    UploadBigFile
  //   },
 
  mounted() {
     this.stompClient= this.$store.state.stompClient;
      var _this = this;
      Bus.$on('opentftp',function(val){//监听fi
              if(val=='开启成功'){
                   _this.openval='关闭'
              }else{
                  _this.openval='开启'
              }
               _this.tftpval=_this.openval
         })
     Bus.$on('chipID',function(val){//监听fi
              _this.chid=val
         })
      Bus.$on('openscan',function(val){//监听fi
              if(val=='开启成功'){
                   _this.openval='关闭'
              }else{
                  _this.openval='开启'
              }
               _this.scanval=_this.openval
             })
      Bus.$on('firupload',function(val){//监听fi
                 
                   if(val.indexOf("上传进行中") != -1){
                     var sknum=val.split("_")[0]
                      console.log("次数+++:"+sknum)
                        _this.sendLoad(imgName,n,sknum,knum,str)
                   }
                 })   
                Bus.$on('allsensor',function(val){//监听first组件的txt事件
           let obj=JSON.parse(val)
            for(let key in obj) {
               
                if(key==1)_this.sensorsearchForm.sensor1=obj[key]
                if(key==2)_this.sensorsearchForm.sensor2=obj[key]
                if(key==3)_this.sensorsearchForm.sensor3=obj[key]
                 if(key==4)_this.sensorsearchForm.sensor4=obj[key]
                  if(key==5)_this.sensorsearchForm.sensor5=obj[key]
                 if(key==6)_this.sensorsearchForm.sensor6=obj[key]
                 if(key==7)_this.sensorsearchForm.sensor7=obj[key]
                 if(key==8)_this.sensorsearchForm.sensor8=obj[key]
                 if(key==9)_this.sensorsearchForm.sensor9=obj[key]
                 if(key==12)_this.sensorsearchForm.sensor12=obj[key]
                 if(key==13)_this.sensorsearchForm.sensor13=obj[key]
                 if(key==14)_this.sensorsearchForm.sensor14=obj[key]
                 if(key==15)_this.sensorsearchForm.sensor15=obj[key]
                if(key==16)_this.sensorsearchForm.sensor16=obj[key]
                 if(key==17)_this.sensorsearchForm.sensor17=obj[key]
                 if(key==18)_this.sensorsearchForm.sensor18=obj[key]
                if(key==19)_this.sensorsearchForm.sensor19=obj[key]
                if(key==21)_this.sensorsearchForm.sensor21=obj[key]
                if(key==22)_this.sensorsearchForm.sensor22=obj[key]
                 if(key==23)_this.sensorsearchForm.sensor23=obj[key]
                  if(key==24)_this.sensorsearchForm.sensor24=obj[key]
              console.log(key);
              console.log(obj[key]);
           }
        })                
  },
  methods: {
     uploadFiles(){
            this.uploadVisible = true;
        },
    allRest(motorId) {
       
      Bus.$emit('progres',true) 
       this.$store.state.resinfo="复位开始"
       this.stompClient.send('/app/reset/'+motorId)
    },
    basket(){
        Bus.$emit('progres',true) 
      this.$store.state.resinfo="查询提篮在线开始"
      this.stompClient.send('/app/basket')
    },
     getChip(){
         Bus.$emit('progres',true) 
        this.$store.state.resinfo="获取芯片开始"
        var _this = this;// `这一步很重要`
         this.stompClient.send('/app/chip')
        
      },
     openServer(){
         Bus.$emit('progres',true) 
        var _this = this;
        if(_this.servalue==0){
           this.$store.state.resinfo="开启tftp服务开始"
          var openvalue=''
          if(_this.openval=='开启')openvalue=1
          if(_this.openval=='关闭')openvalue=0   
          this.stompClient.send('/app/openTftp/'+openvalue)
         
        }else if(_this.servalue==1){
           this.$store.state.resinfo="开启轨道远程开始"
           this.stompClient.send('/app/openRemote')
        }else if(_this.servalue==2){
           this.$store.state.resinfo="开启条码灯开始"
           var scanvals=''
             if(_this.openval=='开启')scanvals=0
             if(_this.openval=='关闭')scanvals=1 
             this.stompClient.send('/app/openscan/'+scanvals)  
        }
      },
      transferMove(){//区域转移
        Bus.$emit('progres',true) 
        this.$store.state.resinfo="区域转移开始"
        var _this = this
        var transId=_this.selVal
        this.stompClient.send('/app/transferMove/'+transId)
      },
      ledcontrl(){
         var _this = this
         var ledId=_this.ledval
        this.stompClient.send('/app/ledcontrl/'+ledId+'/'+_this.heightlight+'/'+_this.lowlight)
      },
      openfirupdate(){
          Bus.$emit('progres',true) 
         this.$store.state.resinfo="开启固件更新开始"
         var _this = this
         this.stompClient.send('/app/openBoot')
      },
       serEdit(servalue){
        this.servalue = servalue;
        if(servalue==1){
             this.openval=this.closeval
        }else if(servalue==0){
             this.openval=this.tftpval
        }else if(servalue==2){
             this.openval=this.scanval
        } 
        console.log(servalue)
      },
       fnEdit(selVal){
       this.selVal = selVal;
       console.log(selVal)
      },
      ledEdit(ledval){
        this.ledval = ledval;
      },
      setBarCode(){//设置条码仪参数
       Bus.$emit('progres',true) 
         this.$store.state.resinfo="开启设置条码仪参数"
         this.stompClient.send('/app/setbarCode/'+this.barcode)
      },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      checkIp:function(ip){
         var val = /([0-9]{1,3}\.{1}){3}[0-9]{1,3}/;
            var vald = val.exec(ip);
            if (vald == null) {
                alert('注意IP有效性');
                return false;
            }
            if (vald != '') {
                if (vald[0] != ip) {
                    alert('注意IP有效性');
                    return false;
                }
            }
      },
       firfileChange(e) {
      // let files = document.getElementById('firdd').files[0].path
      //this.imgDataUrl =this.getObjectURL(files)
      this.firfiles = e.target.files[0]
      var filetype=e.target.files[0].type
      let url = null ;
      if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(this.firfiles) ;
      }else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(this.firfiles) ;
      }else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(this.firfiles) ;
      }
       var imgName = this.firfiles.name;
         var idx = imgName.lastIndexOf(".");  
          if (idx != -1){
                    var ext = imgName.substr(idx+1).toUpperCase();   
                    ext = ext.toLowerCase( ); 
                     if (ext!='bin'){
                       
                    }else{
                          this.addArrs.push(this.firfiles);
                    }   
                }else{

                }
      var dataURL = url;
      console.log('路径'+dataURL);
              var data = new FormData();
        data.append('file', this.firfiles);
      //  //this.stompClient.send('/app/fir',data)
      //  debugger
      this.$axios({
        method:'post',
        url: '/api/upload/fir',
        data: data,
      })
        .then(function (response) {
          console.log(response.data)
        })

    },
    Uint8ToString(u8a){
      var CHUNK_SZ = 0x8000;
      var c = [];
      for (var i=0; i < u8a.length; i+=CHUNK_SZ) {
        c.push(String.fromCharCode.apply(null, u8a.subarray(i, i+CHUNK_SZ)));
      }
      return c.join("");
     },
    //读文件
readFile(filename){
var fso = new ActiveXObject("Scripting.FileSystemObject");
var f = fso.OpenTextFile(filename,1);
var s = "";
while (!f.AtEndOfStream)
s += f.ReadLine()+"\n";
f.Close();
return s;
},
//写文件
writeFile(filename,filecontent){
var fso, f, s ;
fso = new ActiveXObject("Scripting.FileSystemObject");
f = fso.OpenTextFile(filename,8,true);
f.WriteLine(filecontent);
f.Close();
alert('ok');
},
    sendLoad(imgName,n,num,knum,str){//n:多少字节大小，num：第几次分割，knum：总分割数，str：分割内容
            var _this=this
                var sknum=num+1 
                var a = str.slice(n*num, n*(num+1))
               _this.stompClient.send('/app/firupload/'+imgName+'/'+knum+'/'+sknum,a,{})
               
            },
     waitsl(delay) {
       var start = (new Date()).getTime();
        while((new Date()).getTime() - start < delay) {
          continue;
       }
    },
      checkIp:function(ip){
         var val = /([0-9]{1,3}\.{1}){3}[0-9]{1,3}/;
            var vald = val.exec(ip);
            if (vald == null) {
                alert('注意BootLoadIP有效性');
                return false;
            }
            if (vald != '') {
                if (vald[0] != ip) {
                    alert('注意BootLoadIP有效性');
                    return false;
                }
            }
      },
     firclicks() {
        var _this = this
       if(0 == this.addArrs.length){
             this.$message({
               type: 'info',
               message: '请选择要上传的文件'
             });
             return;
           }
        if(this.checkIp(_this.bootIp)==false){
          return;
        }    
      if(this.firfiles){
         this.$store.state.resinfo="固件更新开始"
      //  console.log('baseread='+document.getElementById("upfirfile").defaultValue)
            var params={} //定义json对象
              var resAccount=new Array();//定义数组对象
             var $inputArr = $('.upfirfile');//获取class为resAccount的input对象
             $inputArr.each(function(){
	                 resAccount.push($(this).val());//遍历存入数组
             })
            params['upfirfile']=resAccount;
         var messageJson= JSON.stringify({
           // file:resAccount,
            fileName:this.firfiles.name,
            ipStr:this.bootIp
          });
          var fileName=this.firfiles.name
          var ip=this.bootIp
         _this.stompClient.send('/app/updateBoot/'+fileName+'/'+ip)
      }
    },
    initialUpdate(){//首次更新
       var _this = this
       if(0 == this.addArrs.length){
             this.$message({
               type: 'info',
               message: '请选择要上传的文件'
             });
             return;
           }
        if(this.checkIp(_this.bootIp)==false){
          return;
        }    
      if(this.firfiles){
         this.$store.state.resinfo="固件更新开始"
      //  console.log('baseread='+document.getElementById("upfirfile").defaultValue)
            var params={} //定义json对象
              var resAccount=new Array();//定义数组对象
             var $inputArr = $('.upfirfile');//获取class为resAccount的input对象
             $inputArr.each(function(){
	                 resAccount.push($(this).val());//遍历存入数组
             })
            params['upfirfile']=resAccount;
         var messageJson= JSON.stringify({
           // file:resAccount,
            fileName:this.firfiles.name,
            ipStr:this.bootIp
          });
          var fileName=this.firfiles.name
          var ip=this.bootIp
         _this.stompClient.send('/app/initialUpdate/'+fileName+'/'+ip)
      }
        
    },
      sensorsearch(sensorsearchForm){
          Bus.$emit('progres',true) 
         this.$store.state.resinfo="传感器查询开始"
         this.stompClient.send('/app/sensorSearch')
      },
      sensorEmpty(sensorsearchForm){
          Bus.$emit('progres',true) 
        this.$store.state.resinfo="清空界面传感器值开始"
        this.sensorsearchForm.sensor1=''
        this.sensorsearchForm.sensor2=''
        this.sensorsearchForm.sensor3=''
        this.sensorsearchForm.sensor4=''
        this.sensorsearchForm.sensor5=''
        this.sensorsearchForm.sensor6=''
        this.sensorsearchForm.sensor7=''
        this.sensorsearchForm.sensor8=''
        this.sensorsearchForm.sensor9=''
        this.sensorsearchForm.sensor12=''
        this.sensorsearchForm.sensor13=''
        this.sensorsearchForm.sensor14=''
        this.sensorsearchForm.sensor15=''
        this.sensorsearchForm.sensor16=''
        this.sensorsearchForm.sensor17=''
        this.sensorsearchForm.sensor18=''
        this.sensorsearchForm.sensor19=''
        this.sensorsearchForm.sensor21=''
        this.sensorsearchForm.sensor22=''
        this.sensorsearchForm.sensor23=''
         this.sensorsearchForm.sensor24=''
        this.$store.state.resinfo="清空界面传感器值结束"
          Bus.$emit('progres',false) 
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
 .containerd{
        position:relative;
        border:1px solid #DCDFE6;
        margin-top: 20px;
       
    }
    .containerd::before{
        content:attr(title);
        position:absolute;
        left:10%;
        transform:translateX(-50%);
        -webkit-transform:translate(-50%,-50%);
        padding:0 10px;
        background-color:#fff;
    }
    // .basetable .el-button{
    //    .el-button--primary{
    //       width: 85px;
    //    }
      
    // }
    .basetable td{
 width: 130px;
    }
   
    #nprogress .bar {
  background: red !important; //自定义颜色
  height: 10px;
}
</style>