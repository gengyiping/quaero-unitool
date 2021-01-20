<template>
  <div class="" > 
     <div style="width: 100%;">

					<uploader :options="options" :autoStart="false" :fileStatusText="{
					               success: '上传成功',
					                error: '上传失败',
					                uploading: '正在上传',
					                paused: '暂停上传',
					                waiting: '等待上传'
					          }"
					 @file-success="onFileSuccess"  @file-added="fileAdded"  @file-error="onFileError"
					 class="uploader-example">
						<uploader-unsupport></uploader-unsupport>
						<uploader-drop>
							<uploader-btn :attrs="attrs" single>上传</uploader-btn>
						</uploader-drop>
						<uploader-list></uploader-list>
					</uploader>
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
        bootIp:'192.168.3.121',
        firfiles:'',
        addArrs:[],
    }
  },
  
  mounted() {
     this.stompClient= this.$store.state.stompClient;
  },
  methods: {
    allRest(motorId) {
      debugger 
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
         Bus.$on('chipID',function(val){//监听fi
              _this.chid=val
         })
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
          Bus.$on('opentftp',function(val){//监听fi
              if(val=='开启成功'){
                   _this.openval='关闭'
              }else{
                  _this.openval='开启'
              }
               _this.closeval=_this.openval
         })
        }else if(_this.servalue==1){
           this.$store.state.resinfo="开启轨道远程开始"
           this.stompClient.send('/app/openRemote')
        }
      },
      transferMove(){//区域转移
        Bus.$emit('progres',true) 
        this.$store.state.resinfo="区域转移开始"
        var _this = this
        var transId=_this.selVal
        this.stompClient.send('/app/transferMove/'+transId)
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
             this.openval='开启'
        }else if(servalue==0){
             this.openval=this.closeval
        }
        console.log(servalue)
      },
       fnEdit(selVal){
       this.selVal = selVal;
       console.log(selVal)
      },
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
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
       text=reader.result
         var arrayBufferData=reader.result;
          console.log("arrayBufferData"+arrayBufferData.length)
           console.log("Uint8Array"+new Uint8Array(arrayBufferData).length)
          str=_this.Uint8ToString(new Uint8Array(arrayBufferData))
            var n = 4068
            var kstr=''
            var knum=Math.ceil(str.length/n)
              console.log("总次数+++:"+knum)
            var sleeptime=-1
            for (var i = 0; i < knum; i++) {
              var sknum=i+1
              console.log("次数+++:"+i)
               var a = str.slice(n*i, n*(i+1))
                console.log("分割次数+++:"+i)
                _this.stompClient.send('/app/firupload/'+imgName+'/'+knum+'/'+sknum,a,{})
                console.log("发送次数+++:"+i)
                 _this.waitsl(200)
                console.log("等待次数+++:"+i)
           }
            //  var socket1=new WebSocket('ws://127.0.0.1:8080/webSocket/app/firupload')
        // for (var i = 0, l = str.length; i < l/n; i++) {
                        // socket1.onopen=function(){
                        //      socket1.send(a)
                        // }
            // this.stompClient.send('/app/firupload',messageJson,{})
            //  kstr=kstr+"<input class='upfirfile' value='"+a+"'>"
            // document.getElementById("firdd").innerHTML += "<input class='upfirfile' value='"+a+"'>"
            // document.getElementById("firdd").append("<input class='upfirfile' value='"+a+"'>");
           //  document.getElementById("firdd").innerHTML=kstr
           console.log("str"+str.length);
          // document.getElementById("firdd").innerHTML += "<input id='upfirfile' value='"+str+"'>"
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
      sensorsearch(sensorsearchForm){
          Bus.$emit('progres',true) 
         this.$store.state.resinfo="传感器查询开始"
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