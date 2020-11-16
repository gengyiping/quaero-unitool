<template>
  <div class="" > 
            <el-dropdown @command="handleCommand" style="float:left">
                <span class="el-dropdown-link">
                    {{motorName}}<i class="el-icon-arrow-down el-icon--right"> </i>
                </span>
                <el-dropdown-menu slot="dropdown" >
                    <el-dropdown-item v-for="(ce,index) in kuList" :key="index" :command="ce" icon="el-icon-plus">{{ce}}</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
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
      kuList:null,
      motorId:0,
      motorName:'电机选择'
    }
  },
  mounted() {
    this.stompClient= this.$store.state.stompClient;
    debugger
    this.loadselect();//加载电机
    this.motorId=this.$store.state.motorId;
  },
  methods: {
    loadselect(){
       console.log('loadMotor')
        this.stompClient.send('/app/loadMotor')
          var _this = this;// `这一步很重要`
          Bus.$on('allmotor',function(val){//监听first组件的txt事件
          _this.kuList=val.split(",");
    });
    },
    handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
   handleCommand(command) {
       debugger;
        var _this = this;// `这一步很重要`
       var motorId= command.split("_")[1];
       this.motorId=motorId;
        this.$message('click on item ' + command);
        this.$message('click on item ' + motorId);
         this.$forceUpdate()
        this.$store.state.motorId=this.motorId;
      }
  }
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
</style>