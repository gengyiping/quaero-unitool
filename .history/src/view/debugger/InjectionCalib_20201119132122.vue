<template>
  <div class="" > 
      <div style="width:100%;height:560px;background: rgb(238, 255, 247);">
         <div class="container" title="定标点" style="width:360px; height:auto; float:left;margin-left:10px">
         <el-radio-group v-model="radio"  style="margin-top:10px">
           <table>
             <tr>
               <td>
                 <el-radio :label="1" class="inradio"   border>进样电机推试管到底&emsp;&emsp;&nbsp;</el-radio><!-- Qcit.MB.injection -->
               </td>
               <td>
                 <el-radio :label="2" class="inradio" border>急诊-进样入口&emsp;&emsp;&nbsp;</el-radio><!-- Qcit.MB.emergency -->
               </td>
             </tr>
              <tr>
               <td>
                  <el-radio :label="3" class="inradio" border>进样入口-传送入口</el-radio><!-- Qcit.MB.intTransport -->
               </td>
               <td>
                 <el-radio :label="4" class="inradio" border>传送出口-换轨入口</el-radio><!-- Qcit.MB.intTrack -->
               </td>
             </tr>
              <tr>
               <td>
                 <el-radio :label="5" class="inradio" border>换轨入口-换轨出口</el-radio><!-- Qcit.MB.outTrack -->
               </td>
               <td>
                 <el-radio :label="6" class="inradio" border>换轨出口-递进位&emsp;</el-radio><!-- Qcit.MB.outTrack -->
               </td>
             </tr>
              <tr>
               <td>
                 <el-radio :label="7" class="inradio"  border>递进一格&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.progressive -->
               </td>
               <td>
                  <el-radio :label="8" class="inradio" border>递进收起&emsp;&emsp;&emsp;&emsp;</el-radio><!-- Qcit.MB.stepbyStep -->
               </td>
             </tr>
             <tr>
               <td>
                 <el-radio :label="9" class="inradio" border>递进-回收入口&emsp;&emsp;</el-radio><!-- Qcit.MB.inrecovery -->
               </td>
               <td>
                  <el-radio :label="10" class="inradio" border>回收入口-回收卸载</el-radio> <!-- Qcit.MB.recoveryUnload -->
               </td>
             </tr>
             <tr>
               <td>
                 <el-input v-model="coord" placeholder="请输入坐标"></el-input>
               </td>
               <td>
                  <el-button type="primary" plain>删除设备定标</el-button>
               </td>
             </tr>
             <tr>
               <td>
                 <el-button type="primary" @click="readCoord(0)" plain>读取本地定标</el-button>
               </td>
               <td>
                  <el-button type="primary" @click="readCoord(1)" plain>读取本地定标参数</el-button>
               </td>
             </tr>
              <tr>
               <td>
                 <el-button type="primary"  @click="writeCoord(0)" plain>保存本地定标</el-button>
               </td>
               <td>
                  <el-button type="primary" @click="writeCoord(1)" plain>保存本地定标参数</el-button>
               </td>
             </tr>
              <tr>
               <td>
                 <el-button type="primary" plain>读取设备定标</el-button>
               </td>
               <td>
                  <el-button type="primary" plain>读取设备定标参数</el-button>
               </td>
             </tr>
              <tr>
               <td>
                 <el-button type="primary" plain>保存设备定标</el-button>
               </td>
               <td>
                  <el-button type="primary" plain>保存设备定标参数</el-button>
               </td>
             </tr>
             <tr>
               <td colspan="2">
                 <el-button type="primary" plain>一键下载设备参数至本地</el-button>
               </td>
             </tr>
              <tr>
               <td colspan="2">
                  <el-button type="primary" plain>一键上传设备参数至设备</el-button>
               </td>
             </tr>
           </table>
         </el-radio-group>
         <div></div>
         <div></div>
         <div></div>
        <div></div>
         </div>
         <div class="container" title="电机控制" style="width:730px; height:auto; float:left;margin-left:10px">
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
      prop="zeroCoord"
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
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId)">←零位</el-button>
　　　　</template>
    </el-table-column>
     <el-table-column
      prop=""
      label=""
      width="60" >
            <template slot-scope="scope">
　　　　　　<el-button type="text" @click="checkDetail(scope.row.motorId)">零位→</el-button>
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
      motorId:''
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
      readCoord(opt){//0 读取参数 ;1读取设备参数
         var _this = this;// `这一步很重要`
        this.getCoordPoint()
          this.stompClient.send('/app/readcoord/'+opt+'/'+this.motorId+'/'+this.coodPoint)
      },
      getCoordPoint(){
        debugger
         var _this = this;// `这一步很重要`
         if(_this.radio==1){
           this.coodPoint='QCIT.Config.InjectionMax'
           this.motorId=3
            console.log(this.coodPoint);
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
    handleAddItem() { 
                   
    }
  }
}

</script>
<style rel="stylesheet/scss" lang="scss">
.inradio{
  width: 170px !important;
  float:left;
  vertical-align:middle;
   margin-top:0;
}
</style>