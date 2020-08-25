<template>
  <div class="" > 
      <div style="width:100%;height:300px;">
    <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link">
                    下拉菜单<i class="el-icon-arrow-down el-icon--right"> </i>
                </span>
                <el-dropdown-menu slot="dropdown" >
                    <el-dropdown-item v-for="(ce,index) in kuList" :key="index" :command="ce" icon="el-icon-plus">{{ce}}</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
      </div>
</div> 
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Bus from '../../components/bus'
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      kuList:null,
    }
  },
  mounted() {
    this.stompClient= this.$store.state.stompClient;
    this.loadselect();//加载电机
    
  },
  methods: {
    loadselect(){
       console.log('loadMotor')
        this.stompClient.send('/app/loadMotor')
          var _this = this;// `这一步很重要`
          Bus.$on('allmotor',function(val){//监听first组件的txt事件
          debugger
          _this.kuList=val.split(",");
    });
 
    },
 handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },
   handleCommand(command) {
        this.$message('click on item ' + command);
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
</style>