<template>
  <div class="" style="background: rgb(238, 255, 247);"> 
      <div style="width:1080px;height:1030px;background: rgb(238, 255, 247);">
         <div class="container" title="定标点" style="width:950px; height:auto; float:left;margin-left:10px">
         <el-radio-group v-model="radio"  style="margin-top:10px">
           <table>
             <tr>
               <td>
                 <el-radio :label="1" class="inradio" title="QCIT.Config.InjectionMax"  style="width: 220px !important;"  border>进样电机推试管到底&emsp;&emsp;&nbsp;&emsp;</el-radio><!-- Qcit.MB.injection -->
               </td>
               <td>
                 <el-radio :label="2" class="inradio" title="QCIT.Config.Injection" style="width: 275px !important;" border>进样电机推试管至进样样本架出口检测</el-radio><!-- Qcit.MB.emergency -->
               </td>
               <td>
                  <el-radio :label="3" class="inradio" title="QCIT.Config.emergency" style="width: 210px !important;" border>急诊至进样入口&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.intTransport -->
               </td>
               <td>
                 <el-radio :label="4" class="inradio" title="QCIT.Config.InScan" style="width: 224px !important;" border>转移仓至扫码等待区&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.intTrack -->
               </td>
             </tr>
              <tr>
               <td>
                 <el-radio :label="5" class="inradio" title="QCIT.Config.ScanCode"  style="width: 220px !important;"  border>扫码转移电机移动距离&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="6" class="inradio" title="QCIT.Config.Transfer" style="width: 275px !important;" border>转移电机推试管至进样皮带&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.outTrack -->
               </td>
                <td>
                 <el-radio :label="7" class="inradio" title="QCIT.Config.InBelt" style="width: 210px !important;" border>进样皮带入口至入口检测</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="8" class="inradio" title="QCIT.Config.BeltToTrack"  style="width: 224px !important;" border>进样皮带入口检测至换轨车</el-radio><!-- Qcit.MB.outTrack -->
               </td>
             </tr>
             <tr>
               <td>
                 <el-radio :label="9" class="inradio" title="QCIT.Config.TrackToBackBelt"  style="width: 220px !important;" border>进样皮带出口至返回皮带入口</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="10" class="inradio" title="QCIT.Config.TrackToProg" style="width: 275px !important;" border>返回皮带入口至递进初始位&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="11" class="inradio" title="QCIT.Config.iProgInit" style="width: 210px !important;" border>递进初始位&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.outTrack -->
               </td>
              <td>
                 <el-radio :label="12" class="inradio" title="QCIT.Config.iProToOne" style="width: 224px !important;" border>递进一格&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.progressive -->
               </td>
             </tr>
              <tr>
               <td>
                  <el-radio :label="13" class="inradio" title="QCIT.Config.iProStop"  style="width: 220px !important;" border>递进收起&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.stepbyStep -->
               </td>
                <td>
                 <el-radio :label="14" class="inradio" title="QCIT.Config.iProToBack" style="width: 275px !important;" border>递进-回收入口&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.inrecovery -->
               </td>
                <td>
                  <el-radio :label="15" class="inradio" title="QCIT.Config.backOver" style="width: 210px !important;" border>回收入口-回收卸载&emsp;&emsp;&emsp;</el-radio> <!-- Qcit.MB.recoveryUnload -->
               </td>
                 <td>
                   <el-radio :label="16" class="inradio" title="QCIT.Config.Tubewidth" style="width: 224px !important;" border>试管宽度&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-radio> <!-- Qcit.MB.recoveryUnload --> 
                   </td>
             </tr>
             <!-- <tr>
               <td>
                  <el-radio :label="17" class="inradio" title="QCIT.Config.initPosition"  style="width: 220px !important;" border>换轨初始位&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</el-radio>
               </td>
             </tr> -->
             <tr>
               <td>
                 <el-input v-model="coord" placeholder="请输入坐标"></el-input>
               </td>
               <td>
                  <el-button type="primary"  @click="deleteCoord()" plain>删除设备定标</el-button>
               </td>
                <td>
                  <el-button type="primary" @click="loadCoord(0)" plain>一键下载设备参数至本地</el-button>
               </td>
               <td>
                  <el-button type="primary" @click="loadCoord(1)" plain>一键上传本地参数至设备</el-button>
               </td>
             </tr>
              <tr>
                 <td>
                 <el-button type="primary" @click="readCoord(2)"  plain>读取本地定标</el-button>
               </td>
                <td>
                  <el-button type="primary" @click="readCoord(0)" plain>读取本地定标参数</el-button>
               </td>
               <td>
                 <el-button type="primary"  @click="writeCoord(2)" plain>保存本地定标</el-button>
               </td>
               <td>
                  <el-button type="primary" @click="writeCoord(0)" plain>保存本地定标参数</el-button>
               </td>
             </tr>
              <tr>
                 <td>
                 <el-button type="primary" @click="readCoord(3)" plain>读取设备定标</el-button>
               </td>
               <td>
                  <el-button type="primary" @click="readCoord(1)" plain>读取设备定标参数</el-button>
               </td>
               <td>
                 <el-button type="primary" @click="writeCoord(3)" plain>保存设备定标</el-button>
               </td>
               <td>
                  <el-button type="primary" @click="writeCoord(1)" plain>保存设备定标参数</el-button>
               </td>
             </tr>
           </table>
         </el-radio-group>
         <div></div>
         <div></div>
         <div></div>
        <div></div>
         </div>
         <div class="container" title="电机控制" style="width:900px; height:auto; float:left;margin-left:10px">
           <div  style="margin-top:10px">
            <el-table
    ref="multipleTable"
    :data="tableData2"
    tooltip-effect="dark"
    style="width: 100%"
     >
 <!-- @selection-change="handleSelectionChange" -->
    <el-table-column
      type="selection"
      width="50" >
    </el-table-column>
    <el-table-column
      label="电机名称"
      width="130">
      <template slot-scope="scope">{{ scope.row.motorName }}</template>
    </el-table-column>
    <el-table-column
      prop="forward"
      label="Forward"
      width="80">
             <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.forward" class="cell-input" :disabled="true"/>
                </template>
    </el-table-column>
     <el-table-column
      prop=""
      label="motorId"
      width="80" v-if="show">
       <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.motorId" class="cell-input"  />
        </template>
    </el-table-column>
    <el-table-column
      prop="reverse"
      label="Reverse"
      width="80">
       <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.reverse" class="cell-input"  :disabled="true"/>
                </template>
    </el-table-column>
     <el-table-column
      prop="zero"
      label="zero"
      width="50">
          <template slot-scope="scope">
             <el-checkbox v-model="scope.row.zero" v-bind="{'checked': scope.row.zero? true : false}" @change="v => {update(scope.row, v, 'zero')}"  :disabled="true"></el-checkbox>
         </template>
    </el-table-column>
    <el-table-column
      prop=""
      label="零位坐标"
      width="80">
             <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.zeroCoord" class="cell-input"  :disabled="true"/>
                </template>
    </el-table-column>
    <el-table-column
      prop=""
      label=""
      :render-header="renderHeader"
      width="110">
       <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.coord" class="cell-input"/>
                </template>
    </el-table-column>
    <el-table-column
      prop=""
      label=""
      width="60">
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row,0)">←零位</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="60" >
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row,1)">零位→</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="40" >
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row,2)">GO</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="75">
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row,3)">SeekZero</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="65" show-overflow-tooltip>
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row,4)">Reset</el-button>
　　　　</template>
    </el-table-column>
  </el-table>
   </div>
              <!-- <table style="margin-top:10px"  id="tabledata">
                   <tr>
                     <td><el-checkbox v-model="checked" class="parent_check">全选</el-checkbox></td>
                     <td>Forward</td>
                     <td>Reverse</td>
                     <td>Zero</td>
                     <td><div  style="width:65px">零位坐标</div></td>
                     <td>
                         <el-row>
                            <el-button type="primary" @click="motorcontrol()" plain>查询</el-button>
                          </el-row>
                      </td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                   </tr>
              </table> -->
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
       el: '#example-1',
      list1: [],
      show:false,
      stompClient: null,
      allmotor:[],
      tableData2:[],
      radio:0,
      coord:'',
      coodPoint:'',
      motorId:'',
      motorinfo:[],
      idx:''
    }
  },
  
  mounted() {
     var _this = this
    this.stompClient= this.$store.state.stompClient;
     Bus.$on('allmotor',function(val){//监听first组件的txt事件
     debugger
            var motorlist=val.split(",")
            _this.allmotor=motorlist
           for(var i=0;i<motorlist.length;i++){
             var obj = {}
             var motor=motorlist[i].split('_')
             obj.motorName=motor[0]
             obj.forward=0
             obj.reverse=0
             obj.zero=false
             obj.zeroCoord=0
             obj.coord=0
             obj.motorId=motor[1]
              console.log('motorId='+obj.motorId)
              if(_this.tableData2.length<9){//防止电机界面加载电机时此处重复加载
                _this.tableData2.push(obj)
              }
           }
      });
    this.loadselect(this.tableData2);//加载电机
    
      Bus.$on('readCoord',function(val){//监听fi
              _this.coord=val
          })
      
      Bus.$on('searchMotor',function(val){//监听first组件的txt事件
           let obj=JSON.parse(val)
            for(let key in obj) {
               let obj1=JSON.parse(obj[key])
                console.log('obj1='+obj1)
               for(var i=0;i<kk.length;i++){
                   if(kk[i].motorId==key){
                         for(let key1 in obj1) {
                              if(key1=='forward')kk[i].forward =obj1[key1]
                              if(key1=='reverse')kk[i].reverse=obj1[key1]
                              if(key1=='zeroFlag')kk[i].zero=obj1[key1]
                              if(key1=='zero')kk[i].zeroCoord=obj1[key1]
                              if(key1=='coord')kk[i].coord=obj1[key1]
                         }
                   }
               }
           }
        })
  },
  methods: {
     handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      readCoord(opt){//0 读取坐标参数 ;1读取设备坐标参数 2读取坐标 ;3读取设备坐标
        Bus.$emit('progres',true) 
        this.$store.state.resinfo="读取坐标开始"
       var _this = this
       _this.coord=''
         this.getCoordPoint()
          this.stompClient.send('/app/readCoord/'+opt+'/'+this.motorId+'/'+this.coodPoint)
      },
       writeCoord(opt){//0 保存本地定标参数 ;1保存设备定标参数; 2保存本地定标 3保存设备定标
       if(this.checkNum(this.coord)==false){
                return;
       }
         Bus.$emit('progres',true) 
        this.$store.state.resinfo="写入坐标开始"
       var _this = this;
         this.getCoordPoint()
          this.stompClient.send('/app/writeCoord/'+opt+'/'+this.motorId+'/'+this.coodPoint+'/'+this.coord)
      },
      checkNum:function(val) {
             var reg = /^[+-]?\d*\.?\d{1,3}$/;
              if (!reg.test(val)) {
              alert("请输入正确的数值")
                return false;
               }
            },
      deleteCoord(){
          Bus.$emit('progres',true) 
         this.$store.state.resinfo="删除坐标开始"
         this.getCoordPoint()
         this.stompClient.send('/app/deleteCoord/'+this.coodPoint)
      },
      loadCoord(opt){
          Bus.$emit('progres',true) 
        if(opt==0){
           this.$store.state.resinfo="一键下载设备参数至本地开始"
        }else{
          this.$store.state.resinfo="一键上传本地参数至设备开始"
        }
         this.stompClient.send('/app/loadCoord/'+opt)
      },
      getCoordPoint(){
         var _this = this;// `这一步很重要`
         if(_this.radio==1){
           this.coodPoint='QCIT.Config.InjectionMax'
           this.motorId=3
         }else if(_this.radio==2){
           this.coodPoint='QCIT.Config.Injection'
           this.motorId=3
         }else  if(_this.radio==16){
           this.coodPoint='QCIT.Config.Tubewidth'
           this.motorId=3
         }else  if(_this.radio==3){
           this.coodPoint='QCIT.Config.emergency'
           this.motorId=0
         }else  if(_this.radio==4){
           this.coodPoint='QCIT.Config.InScan'
           this.motorId=1
         }else if(_this.radio==5){
           this.coodPoint='QCIT.Config.ScanCode'
           this.motorId=1
         }else if(_this.radio==6){
           this.coodPoint='QCIT.Config.Transfer'
           this.motorId=1
         }else if(_this.radio==7){
           this.coodPoint='QCIT.Config.InBelt'
           this.motorId=9
         }else if(_this.radio==8){
           this.coodPoint='QCIT.Config.BeltToTrack'
           this.motorId=9
         }else if(_this.radio==9){
           this.coodPoint='QCIT.Config.TrackToBackBelt'
           this.motorId=8
         }else if(_this.radio==10){
           this.coodPoint='QCIT.Config.TrackToProg'
           this.motorId=4
         }else if(_this.radio==11){
           this.coodPoint='QCIT.Config.iProgInit'
           this.motorId=6
         }else if(_this.radio==12){
           this.coodPoint='QCIT.Config.iProToOne'
           this.motorId=6
         }else if(_this.radio==13){
           this.coodPoint='QCIT.Config.iProStop'
           this.motorId=6
         } else if(_this.radio==14){
           this.coodPoint='QCIT.Config.iProToBack'
           this.motorId=4
         }else if(_this.radio==15){
           this.coodPoint='QCIT.Config.backOver'
           this.motorId=2
         }
        //  else if(_this.radio==17){
        //    this.coodPoint='QCIT.Config.initPosition'
        //    this.motorId=8
        //  }
      },
      loadselect(datatable){
          Bus.$emit('progres',true) 
         this.$store.state.resinfo="定标界面加载电机开始"
       console.log('loadallmotor')
        this.stompClient.send('/app/loadMotor')
         
         
     },
     renderHeader(h) {
      return(
         <el-button class="filter-item" type="default" icon="el-icon-search" onClick={()=>this.handleAddItem()}>查询</el-button>
      )
    },
    checkDetail(scopes,opt){
      debugger
      if(opt<3){
         if(this.checkNum(scopes.coord)==false){
                return;
       }
      }
     
        Bus.$emit('progres',true) 
       this.$store.state.resinfo="定标界面电机运行开始"
       var motor=scopes.motorId
       var coor=scopes.coord
      this.stompClient.send('/app/motorOpt/'+motor+'/'+coor+'/'+opt)

    },
    waitsleep(delay) {
       var start = (new Date()).getTime();
        while((new Date()).getTime() - start < delay) {
          continue;
       }
    },
    handleAddItem() { 
        Bus.$emit('progres',true) 
      this.$store.state.resinfo="定标界面电机查询开始"
      var _this = this;// `这一步很重要`
     var kk= this.$refs.multipleTable.selection
       _this.motorinfo=kk
      var motorArray=new Array();
      //  for(var i=0;i<kk.length;i++){
      //   
      //    this.stompClient.send('/app/searchMotor/'+kk[i].motorId)
      //    _this.idx=i
      //     Bus.$on('searchMotor',(response) => {
      //     
      //       let obj=JSON.parse(response)
      //       for(let key in obj) {
      //          console.log('_this.idx'+_this.idx)
      //                         if(key=='forward')_this.motorinfo[_this.idx].forward =obj[key]
      //                         if(key=='reverse')_this.motorinfo[_this.idx].reverse=obj[key]
      //                         if(key=='zeroFlag')_this.motorinfo[_this.idx].zero=obj[key]
      //                         if(key=="zero")_this.motorinfo[_this.idx].zeroCoord=obj[key]
      //                         if(key=='coord')_this.motorinfo[_this.idx].coord=obj[key]
      //                         kk[_this.idx]=_this.motorinfo[_this.idx]
      //      }
      //       })
      //       waitsleep(1000*1)
      
      
      //  (function(i) {
      //   setTimeout(function() {
      //       console.log(i);
      //   }, (i + 1) * 1000);
      //   })(i)
       
       //   }
       
     for(var i=0;i<kk.length;i++){
        
        motorArray[i]=kk[i].motorId
     }
      this.stompClient.send('/app/searchMotor/'+motorArray)
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
.inradio{
  float:left;
  vertical-align:middle;
   margin-top:0;
}
</style>