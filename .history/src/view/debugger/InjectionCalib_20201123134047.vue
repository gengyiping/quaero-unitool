<template>
  <div class="" > 
      <div style="width:100%;height:560px;background: rgb(238, 255, 247);">
         <div class="container" title="定标点" style="width:700px; height:auto; float:left;margin-left:10px">
         <el-radio-group v-model="radio"  style="margin-top:10px">
           <table>
             <tr>
               <td>
                 <el-radio :label="1" class="inradio"   border>进样电机推试管到底&emsp;&emsp;&nbsp;</el-radio><!-- Qcit.MB.injection -->
               </td>
               <td>
                 <el-radio :label="2" class="inradio" border>进样电机推试管至进样样本架出口检测</el-radio><!-- Qcit.MB.emergency -->
               </td>
               <td>
                  <el-radio :label="3" class="inradio" border>急诊至进样入口</el-radio><!-- Qcit.MB.intTransport -->
               </td>
               <td>
                 <el-radio :label="4" class="inradio" border>转移仓至扫码等待区</el-radio><!-- Qcit.MB.intTrack -->
               </td>
             </tr>
              <tr>
               <td>
                 <el-radio :label="5" class="inradio" border>扫码转移电机移动距离</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="6" class="inradio" border>转移电机推试管至进样皮带</el-radio><!-- Qcit.MB.outTrack -->
               </td>
                <td>
                 <el-radio :label="7" class="inradio" border>进样皮带入口至入口检测</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="8" class="inradio" border>进样皮带入口检测至换轨车</el-radio><!-- Qcit.MB.outTrack -->
               </td>
             </tr>
             <tr>
               <td>
                 <el-radio :label="9" class="inradio" border>进样皮带至返回皮带</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="10" class="inradio" border>返回皮带入口至递进初始位</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="11" class="inradio" border>递进初始位</el-radio><!-- Qcit.MB.outTrack -->
               </td>
              <td>
                 <el-radio :label="12" class="inradio"  border>递进一格&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.progressive -->
               </td>
             </tr>
              <tr>
               <td>
                  <el-radio :label="13" class="inradio" border>递进收起&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.stepbyStep -->
               </td>
                <td>
                 <el-radio :label="14" class="inradio" border>递进-回收入口&emsp;&emsp;</el-radio><!-- Qcit.MB.inrecovery -->
               </td>
                <td>
                  <el-radio :label="15" class="inradio" border>回收入口-回收卸载</el-radio> <!-- Qcit.MB.recoveryUnload -->
               </td>
                 <td>
                   <el-radio :label="16" class="inradio" border>试管宽度</el-radio> <!-- Qcit.MB.recoveryUnload --> 
                   </td>
             </tr>
             <tr>
               <td>
                 <el-input v-model="coord" placeholder="请输入坐标"></el-input>
               </td>
               <td>
                  <el-button type="primary" plain>删除设备定标</el-button>
               </td>
                <td>
                  <el-button type="primary" plain>一键下载设备参数至本地</el-button>
               </td>
               <td>
                  <el-button type="primary" plain>一键上传设备参数至设备</el-button>
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
    @selection-change="handleSelectionChange"  >

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
                  <el-input type="text" v-model="scope.row.forward" class="cell-input"/>
                </template>
    </el-table-column>
    <el-table-column
      prop="reverse"
      label="Reverse"
      width="80">
       <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.reverse" class="cell-input"/>
                </template>
    </el-table-column>
     <el-table-column
      prop="zero"
      label="zero"
      width="50">
          <template slot-scope="scope">
             <el-checkbox v-model="scope.row.zero" v-bind="{'checked': scope.row.zero? true : false}" @change="v => {update(scope.row, v, 'zero')}"></el-checkbox>
         </template>
    </el-table-column>
    <el-table-column
      prop=""
      label="零位坐标"
      width="80">
             <template slot-scope="scope">
                  <el-input type="text" v-model="scope.row.zeroCoord" class="cell-input"/>
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
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId,0)">←零位</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="60" >
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId,1)">零位→</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="40" >
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId)">GO</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="75">
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId)">SeekZero</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="65" show-overflow-tooltip>
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId)">Reset</el-button>
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
    this.stompClient= this.$store.state.stompClient;
    this.loadselect(this.tableData2);//加载电机
  },
  methods: {
     handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
      readCoord(opt){//0 读取坐标参数 ;1读取设备坐标参数 2读取坐标 ;3读取设备坐标
       var _this = this
       _this.coord=''
         this.getCoordPoint()
          this.stompClient.send('/app/readCoord/'+opt+'/'+this.motorId+'/'+this.coodPoint)
           Bus.$on('readCoord',function(val){//监听fi
           debugger
              _this.coord=val
          })
      },
       writeCoord(opt){//0 保存本地定标参数 ;1保存设备定标参数; 2保存本地定标 3保存设备定标
       var _this = this;
         this.getCoordPoint()
          this.stompClient.send('/app/writeCoord/'+opt+'/'+this.motorId+'/'+this.coodPoint+'/'+coord)
      },
      getCoordPoint(){
        debugger
         var _this = this;// `这一步很重要`
         if(_this.radio==1){
           this.coodPoint='QCIT.Config.InjectionMax'
           this.motorId=3
         }else if(_this.radio==2){
           this.coodPoint='QCIT.Config.Injection'
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
         } else if(_this.radio==13){
           this.coodPoint='QCIT.Config.iProToBack'
           this.motorId=4
         }else if(_this.radio==15){
           this.coodPoint='QCIT.Config.iProStop'
           this.motorId=2
         }
      },
      loadselect(datatable){
       console.log('loadallmotor')
        this.stompClient.send('/app/loadMotor')
          var _this = this;// `这一步很重要`
          Bus.$on('allmotor',function(val){//监听first组件的txt事件
            debugger
            var motorlist=val.split(",")
            this.allmotor=motorlist
            // var  tr= '<tr>'+
            //         ' <td><input type="checkbox" >全选</input></td>'+
            //         ' <td>Forward</td>'+
            //         ' <td>Reverse</td>'+
            //         ' <td>Zero</td>'+
            //         ' <td><div  style="width:65px">零位坐标</div></td>'+
            //         ' <td>'+
            //         '     <input type="button" value="查询"></input>'+
            //         '  </td>'+
            //         '  <td></td>'+
            //         '  <td></td>'+
            //         '  <td></td>'+
            //         '  <td></td>'+
            //         '  <td></td>'+
            //         '</tr>'
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
             datatable.push(obj)
            /*  tr=tr+'<tr>'+
                   '<td><input type="checkbox"  style="width: 135px;">'+motorlist[i]+'</input></td>'+
                   '<td><input  :disabled="true" style="width: 60px;"> </input></td>'+
                   '<td><input  :disabled="true" style="width: 60px;"> </input></td>'+
                   '<td><input type="checkbox" ></input></td>'+
                   '<td><input  placeholder="" style="width: 68px;"></input></td>'+
                   '<td><input  placeholder="请输入内容" style="width: 68px;"></input></td>'+
                   '<td>'+
                   '     <input type="button"  value="←零位"></input>'+
                   '</td>'+
                   '<td>'+
                   '     <input type="button"  value="零位→"></input>'+
                    '</td>'+
                   '<td>'+
                   '     <input type="button"  value="GO"></input>'+
                   ' </td>'+
                   ' <td>'+
                   '     <input type="button"  value="SeekZero"></input>'+
                   ' </td>'+
                   ' <td>'+
                   '     <input type="button"  value="Reset"></input>'+
                   ' </td>'+
                   ' </tr>' */
           }
           /*  document.getElementById("tabledata").innerHTML=tr */
      });
     },
     renderHeader(h) {
      return(
         <el-button class="filter-item" type="default" icon="el-icon-search" onClick={()=>this.handleAddItem()}>查询</el-button>
      )
    },
    waitsleep(delay) {
    var start = (new Date()).getTime();
    while((new Date()).getTime() - start < delay) {
        continue;
    }
    },
    handleAddItem() { 
      var _this = this;// `这一步很重要`
     var kk= this.$refs.multipleTable.selection
       _this.motorinfo=kk
      var motorArray=new Array();
      //  for(var i=0;i<kk.length;i++){
      //   debugger
      //    this.stompClient.send('/app/searchMotor/'+kk[i].motorId)
      //    _this.idx=i
      //     Bus.$on('searchMotor',(response) => {
      //     debugger
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
        debugger
        motorArray[i]=kk[i].motorId
     }
      this.stompClient.send('/app/searchMotor/'+motorArray)
       Bus.$on('searchMotor',function(val){//监听first组件的txt事件
           debugger
           let obj=JSON.parse(val)
            for(let key in obj) {
              debugger
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
    }
  }
}

</script>
<style rel="stylesheet/scss" lang="scss">
.inradio{
  width: 275px !important;
  float:left;
  vertical-align:middle;
   margin-top:0;
}
</style>