<template>
  <div class="" > 
      <div style="width:100%;height:600px;background: rgb(238, 255, 247);">
         <div class="container" title="基本操作" style="width:265px; height:auto; float:left;">
           <table class="basetable" style="margin-top:10px">
             <tr>
               <td><el-button type="primary"  @click="allRest(5)" style="width: 126px;" plain>整机复位</el-button></td>
               <td><el-button type="primary" style="width: 126px;" plain>提篮在线确认</el-button></td>
             </tr>
              <tr>
               <td><el-button type="primary"  @click="allRest(3)" style="width: 126px;" plain>进样仓复位</el-button></td>
               <td><el-button type="primary"  @click="allRest(1)" style="width: 126px;" plain>转移仓复位</el-button></td>
             </tr>
             <tr>
               <td><el-button type="primary"  @click="allRest(2)" style="width: 126px;" plain>回收仓复位</el-button></td>
               <td><el-button type="primary"  @click="allRest(6)" style="width: 126px;" plain>递进电机复位</el-button></td>
             </tr>
             <tr>
               <td><el-button type="primary"  @click="allRest(0)" style="width: 126px;" plain>急诊电机复位</el-button></td>
               <td><el-button type="primary"  @click="allRest(7)" style="width: 126px;" plain>阻挡电机复位</el-button></td>
             </tr>
              <tr>
               <td><el-button type="primary"  @click="allRest(8)" style="width: 126px;" plain>换轨电机复位</el-button></td>
               <td><el-button type="primary" style="width: 126px;"  @click="getChip()"  plain>获取芯片ID</el-button></td>
             </tr>
              <tr>
               <td colspan="2"><el-input v-model="chid" placeholder="获取芯片ID" ></el-input></td>
             </tr>
              <tr>
               <td>
                 <el-select v-model="servalue" placeholder="请选择" style="width: 126px;" @change="serEdit"> 
                      <el-option
                          v-for="item in options"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 126px;"  @click="openServer()" plain>{{openval}}</el-button></td>
             </tr>
             <tr>
               <td>
                 <el-select v-model="selVal" placeholder="请选择" style="width: 126px;"  @change="fnEdit">
                      <el-option
                          v-for="item in moveoptions"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                      </el-option>
                 </el-select>
               </td>
               <td><el-button type="primary" style="width: 126px;" @click="transferMove()" plain>区域转移</el-button></td>
             </tr>
              <tr>
               <td><el-input v-model="bootIp" placeholder="Bootload Ip" ></el-input></td>
               <td><el-button type="primary" style="width: 126px;" @click="openfirupdate()" plain>开启固件更新</el-button></td>
             </tr>
             <tr>
               <td colspan="2">
                  <div>
                      <input type="file" @change="firfileChange"> 
                        <el-button type="primary" size="mini" @click="firclicks">固件更新</el-button>
                       <div id='firdd' hidden></div>
                   </div>
                </td>
               <!-- <td><el-button type="primary" style="width: 126px;" @click="transferMove()" plain>选择固件</el-button></td> -->
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
                  <td>回收仓电机序号<br/>[D21-S13]</td>
                  <td><el-input v-model="sensorsearchForm.sensor21" placeholder="请查询结果"></el-input></td>
                </tr>
                 <tr>
                  <td>急诊位检测<br/>[D22-S13]</td>
                  <td><el-input v-model="sensorsearchForm.sensor22" placeholder="请查询结果"></el-input></td>
                  <td>转移仓到位检测<br/>[D23-R14]</td>
                  <td><el-input v-model="sensorsearchForm.sensor23" placeholder="请查询结果"></el-input></td>
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
import Bus from '../../components/bus' //跨页面自定义传值组件
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      options:[{
          value: 0,
          label: 'tftp服务'
        },{
          value: 1,
          label: ' 开启轨道远程'
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
         sensor23:''
        },
        chid:'',
        selVal:'',
        servalue:'',
        openval:'开启',
        closeval:'开启',
        bootIp:'192.168.3.221',
        firfiles:'',
        addArrs:[],
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
        debugger
         this.stompClient.send('/app/reset/'+motorId)
      },
      getChip(){
        debugger
        var _this = this;// `这一步很重要`
         this.stompClient.send('/app/chip')
         Bus.$on('chipID',function(val){//监听fi
              debugger
              _this.chid=val
         })
      },
      serEdit(servalue){
        debugger;
        this.servalue = servalue;
        if(servalue==1){
             this.openval='开启'
        }else if(servalue==0){
             this.openval=this.closeval
        }
        console.log(servalue)
      },
      openServer(){
        debugger
        var _this = this;
        if(_this.servalue==0){
          var openvalue=''
          if(_this.openval=='开启')openvalue=1
          if(_this.openval=='关闭')openvalue=0   
          this.stompClient.send('/app/openTftp/'+openvalue)
          Bus.$on('opentftp',function(val){//监听fi
              debugger
              if(val=='开启成功'){
                   _this.openval='关闭'
              }else{
                  _this.openval='开启'
              }
               _this.closeval=_this.openval
         })
        }else if(_this.servalue==1){
           this.stompClient.send('/app/openRemote')
        }
      },
      fnEdit(selVal){
        debugger
       this.selVal = selVal;
       console.log(selVal)
      },
      transferMove(){//区域转移
      debugger
        var _this = this
        var transId=_this.selVal
        this.stompClient.send('/app/transferMove/'+transId)
      },
      openfirupdate(){
         var _this = this
         this.stompClient.send('/app/openBoot')
      },
       firfileChange(e) {
      debugger
      this.firfiles = e.target.files[0]
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
      this.readfir(this.firfiles,imgName);
    },
    Uint8ToString(u8a){
      var CHUNK_SZ = 0x8000;
      var c = [];
      for (var i=0; i < u8a.length; i+=CHUNK_SZ) {
        c.push(String.fromCharCode.apply(null, u8a.subarray(i, i+CHUNK_SZ)));
      }
      return c.join("");
     },
    readfir(fil,imgName){
      var _this = this
    var reader = new FileReader()
    //reader.readAsText(fil)
  // reader.readAsBinaryString(fil)
   reader.readAsArrayBuffer(fil);

     var text=''
     var str=''
    reader.onload = function()
    {
        debugger
        var arrayBufferData=reader.result
        str=_this.Uint8ToString(new Uint8Array(arrayBufferData))
       var fs=this.FileSlicer(str,0)
        var sliceSize=1024*5
        var slices=Math.ceil(file.length/sliceSize)
     
       socket1.onopen=function(){
         for(var i=0;i<fs.slices;i++){
           socket1.send(fs.getNextSlice())
         }
       }
       text=reader.result
         var arrayBufferData=reader.result;
          console.log("arrayBufferData"+arrayBufferData.length)
           console.log("Uint8Array"+new Uint8Array(arrayBufferData).length)
          str=_this.Uint8ToString(new Uint8Array(arrayBufferData))
            var n = 1024*5
            var kstr=''
            var knum=Math.ceil(str.length/n)
            var sleeptime=-1
              var socket1=new WebSocket('http://127.0.0.1:8080/webSocket/app/firupload')
         for (var i = 0, l = str.length; i < l/n; i++) {
         //   for (var i = 0; i < knum; i++) {
               console.log("次数+++:"+i)
               var a = str.slice(n*i, n*(i+1))
               _this.stompClient.send('/app/firupload/'+imgName+'/'+knum+'/'+i,a,{})
                _this.waitsl(200)
              
            // this.stompClient.send('/app/firupload',messageJson,{})
            //  kstr=kstr+"<input class='upfirfile' value='"+a+"'>"
            // document.getElementById("firdd").innerHTML += "<input class='upfirfile' value='"+a+"'>"
            // document.getElementById("firdd").append("<input class='upfirfile' value='"+a+"'>");
           }
           //  document.getElementById("firdd").innerHTML=kstr
           console.log("str"+str.length);
          // document.getElementById("firdd").innerHTML += "<input id='upfirfile' value='"+str+"'>"
    } 
    },
    FileSlicer(file,curentSlice){
      var sliceSize=1024*5
      this.slices=Math.ceil(file.length/sliceSize)
      this.getNextSlice=function(){
        var start=curentSlice*sliceSize
        var end=Math.min((curentSlice+1)*sliceSize,file.length)
        ++curentSlice
        return file.slice(start,end)
      }
    },
     waitsl(delay) {
       var start = (new Date()).getTime();
        while((new Date()).getTime() - start < delay) {
          continue;
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
      if(this.firfiles){
      //  console.log('baseread='+document.getElementById("upfirfile").defaultValue)
         debugger
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
      sensorsearch(sensorsearchForm){
         this.stompClient.send('/app/sensorSearch')
           Bus.$on('allsensor',function(val){//监听first组件的txt事件
           debugger
           let obj=JSON.parse(val)
            for(let key in obj) {
               debugger
                if(key==1)sensorsearchForm.sensor1=obj[key]
                if(key==2)sensorsearchForm.sensor2=obj[key]
                if(key==3)sensorsearchForm.sensor3=obj[key]
                 if(key==4)sensorsearchForm.sensor4=obj[key]
                  if(key==5)sensorsearchForm.sensor5=obj[key]
                 if(key==6)sensorsearchForm.sensor6=obj[key]
                 if(key==7)sensorsearchForm.sensor7=obj[key]
                 if(key==8)sensorsearchForm.sensor8=obj[key]
                 if(key==9)sensorsearchForm.sensor9=obj[key]
                 if(key==12)sensorsearchForm.sensor12=obj[key]
                 if(key==13)sensorsearchForm.sensor13=obj[key]
                 if(key==14)sensorsearchForm.sensor14=obj[key]
                 if(key==15)sensorsearchForm.sensor15=obj[key]
                if(key==16)sensorsearchForm.sensor16=obj[key]
                 if(key==17)sensorsearchForm.sensor17=obj[key]
                 if(key==18)sensorsearchForm.sensor18=obj[key]
                if(key==19)sensorsearchForm.sensor19=obj[key]
                if(key==21)sensorsearchForm.sensor21=obj[key]
                if(key==22)sensorsearchForm.sensor22=obj[key]
                 if(key==23)sensorsearchForm.sensor23=obj[key]
              console.log(key);
              console.log(obj[key]);
           }
      
        })
      },
      sensorEmpty(sensorsearchForm){
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