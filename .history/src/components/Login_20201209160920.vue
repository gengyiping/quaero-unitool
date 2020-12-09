<template>
  <div class="" style="background:#f2f2f2;height:100%">
        <!-- <form > -->
              <el-form ref="loginform"  label-width="80px" style="">
            <div class="login_avatar dropdown" style="margin-top: 50px;">
                <img src="../../static/images/0.jpeg" id="avatarList" data-toggle="dropdown"/>
                <div class="dropdown-menu avatar_list_div" aria-labelledby="avatarList">
                </div>
            </div>
            <div style="width:350px;margin:0 auto;">
               <el-form-item label="账户">
                    <el-input  placeholder="请输入账户名" v-model="loginform.userName"></el-input>
                </el-form-item>
               <el-form-item label="密码">
                     <el-input placeholder="请输入密码" v-model="loginform.password" show-password></el-input>
               </el-form-item>
               <el-form-item label="项目">
                  <el-select v-model="loginform.project" placeholder="请选择项目">
                      <el-option label="SmallQCIT" value="1"></el-option>
                  </el-select>
              </el-form-item>
              <el-form-item>
                     <el-button type="primary" @click="loginSubmit">登录</el-button>
                     <!-- <el-button>取消</el-button> -->
              </el-form-item>
            </div>
             </el-form>
  </div>
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import Qcitdebug from '@/view/debugger/Qcitdebug'
import Qcitsoft from '@/view/soft/Qcitsoft'
import Demo from '@/view/demo/divMove'
import Bus from './bus'
import {getNetworkIp} from './ip'
export default {
  data() {
    return {
      loginform:{
        userName:'匿名',
        password:'',
        project:''
      },
      password:'',
      tabPosition: 'left',
      list1: [],
      stompClient: null,
      initshow:true,
      closeshow:false,
      allmotor:null,
       centerDialogVisible: false,
       coord:null,
       ip:'',
       activeName: '0',
       pagestate:'',
       socketUrl: 'http://127.0.0.1:8080/webSocket',
       pageSate:''

    }
  },
  components: {
    Qcitdebug,
    Qcitsoft,
    Demo,
     
    },

  mounted() {
    this.init()//初始化连接
  },
  methods: {
     init(){//
        const socket = new SockJS('http://127.0.0.1:8080/webSocket')
         this.stompClient = Stomp.over(socket)
         this.$store.state.stompClient=this.stompClient
         this.config()// 配置stomp
         this.sub()//订阅地址

     },
     loginSubmit(){
        this.$router.push({ path:'/webTest.vue'  })

     },
    config() {
        // 每隔30秒做一次心跳检测
         this.stompClient.heartbeat.outgoing = 30000;
        // 客户端不接收服务器的心跳检测
         this.stompClient.heartbeat.incoming = 0;
     },
     sub(){
       var _this=this
        var user = this.createUser();
        this.stompClient.connect({}, function (frame) {
            console.log('-------连接成功------------')
           _this.stompClient.subscribe('/topic/getResponse', (val) => {
             // this.list1 = JSON.parse(val.body)
          console.log('-------++++++++++++++++++++++++++++++------------')
                         //下面会报错，应该是json的问题，但是数据可以接收到
          console.log(val.body)
           });
           _this.setConnected(true);
        }, function (error) {
           console.log('-------请重新连接！------------')
           this.refresh()
        });
     },
     refresh(){
        this.init()
     },
     createUser(){//创建用户
      
     },
     getUser(){
       
     },
     setConnected(connected) {//设置连接状态
      }

    
  },
 computed: {
    stompinit(){
       return this.$store.state.stompClient
    }
  },
  watch:{
    stompinit(newVal,oldVal){
      this.stompClient=newVal
    }
  },
}
</script>
<style rel="stylesheet/scss" lang="scss">
.login_avatar {
    cursor: pointer;
}
.dropdown {
    position: relative;
}
#avatarList{
 border-radius: 50%;
    width: 80px;
    height: 80px;
    box-shadow: 0 0 5px #999;
}
.form-control {
    display: block;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.428571429;
    color: #555;
    vertical-align: middle;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
}
#username {
    width: 180px;
    margin: 10px auto;
}
.btn-primary {
    color: #fff;
    background-color: #428bca;
    border-color: #357ebd;
}
.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: normal;
    line-height: 1.428571429;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    cursor: pointer;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    user-select: none;
}
.glyphicon:empty {
    width: 1em;
}
.glyphicon {
    position: relative;
    top: 1px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    -webkit-font-smoothing: antialiased;
    font-style: normal;
    font-weight: normal;
    line-height: 1;
    -moz-osx-font-smoothing: grayscale;
}
.btn-primary {
    color: #fff;
    background-color: #428bca;
    border-color: #357ebd;
}
button, select {
    text-transform: none;
}
button, input, select, textarea {
    margin: 0;
    font-family: inherit;
    font-size: 100%;
}
button {
    text-rendering: auto;
    color: -internal-light-dark(black, white);
    letter-spacing: normal;
    word-spacing: normal;
    text-transform: none;
    text-indent: 0px;
    text-shadow: none;
    text-align: center;
    cursor: default;
    font: 400 13.3333px Arial;
}
.login_fm {
    width: 100%;
    height: 100%;
    text-align: center;
    margin-top: 50px;
}

.tabBox{
  margin-top: 20px;
  .el-tabs--left{
    height: auto !important;
    .el-tabs__item{
      width: 60px;
      line-height: 30px;
      height: auto;
      word-wrap: break-word;
      white-space: pre-line;
      margin-bottom: 25px;
    };
  
  };
};
.el-tab-pane{
margin-top:0px !important;
}
.el-row {
    margin-bottom: 20px;
    margin-top: -23px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }

</style>