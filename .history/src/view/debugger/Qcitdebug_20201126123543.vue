<template>
<div class="width:100%;height:100%">
<el-menu
:default-active="activeIndex"
  class="el-menu-demo"
  mode="horizontal"
  @select="handleSelect"
  background-color="#545c64"
  text-color="#fff"
  active-text-color="#ffd04b"
  >
  <el-menu-item index="1">OverView</el-menu-item>
  <el-submenu index="2">
    <template slot="title">进样器+前置仓</template>
    <el-menu-item index="2-1">基本操作</el-menu-item>
    <el-menu-item index="2-2">定标</el-menu-item>
  </el-submenu>
   <el-submenu index="3" >
     <template slot="title">电机</template>
    <el-menu-item index="3-1">基本操作</el-menu-item>
    <el-menu-item index="3-2">更多操作</el-menu-item>
   </el-submenu>
   <el-menu-item index="4">测试</el-menu-item>
</el-menu>
  <div id="maincontain" style="width:100%;height:100%;background: #e5e9f2;">
<keep-alive>
    <OVERVIEW v-if="index===1"></OVERVIEW>
    <INJECTION v-if="index===2"></INJECTION>
     <INJECTIONCALIB v-if="index===3"></INJECTIONCALIB>
    <MOTOR v-else-if="index===4" ></MOTOR>
    <MOTORMORE v-else-if="index===5"></MOTORMORE>
    <TEST v-else-if="index===6"></TEST>
    <OVERVIEW v-else></OVERVIEW>  
  </keep-alive>

</div>
</div>
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import MOTORBASE from '@/view/debugger/motor'
import MOREMOTOR from '@/view/debugger/motormore'
import OverView from '@/view/debugger/OverView'
import InjectionCalib from '@/view/debugger/InjectionCalib'
import Injection from '@/view/debugger/injection'
import Test from '@/view/debugger/Test'

export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      activeIndex: '1',
       activeIndex2: '1',
      index:0,
    }
  },
   components:{
         OVERVIEW:OverView,
         INJECTION:Injection,
         INJECTIONCALIB:InjectionCalib,
         MOTOR:MOTORBASE,
         MOTORMORE:MOREMOTOR,
         TEST:Test
   },
  mounted() {
    debugger
   this.stompClient= this.$store.state.stompClient;
  },
  methods: {
 handleSelect(key, keyPath) {
   var _this=this
     debugger
       var indexkey=0;
        console.log(key, keyPath);
        if(key=="1")indexkey=1;
        if(key=="2-1")indexkey=2;
         if(key=="2-2")indexkey=3;
        if(key=="3-1")indexkey=4;
        if(key=="3-2")indexkey=5;
         if(key=="4")indexkey=6;
        this.index =indexkey;
        debugger
       var pages=this.$store.state.rootPage.split("_")
       if(this.index!=pages[2]){
        var page=pages[0]+'_'+pages[1]+'_'+this.index+'_#'
        var ip=localStorage.getItem('ip_addr')
         debugger
         while(true){
              debugger
              if(this.$store.state.stompClient!=null){
                 _this.stompClient.send('/app/page/'+ip+'/'+page,{})
                 break;
              }
               
         }
       }
      }
  },
   computed: {
    secondPage(){
         debugger
        return this.$store.state.secondPage
       },  
  },
  watch:{
    secondPage(newVal,oldVal){
      debugger
        console.log("newVal="+newVal)
         console.log("oldVal="+oldVal)
             if(newVal!==oldVal){
          var pagesindexs=newVal.split("_")
           this.index=pagesindexs[2]
          }
           this.$store.state.thirdPage=newVal
    },
  },
}

</script>
<style rel="stylesheet/scss" lang="scss">


</style>