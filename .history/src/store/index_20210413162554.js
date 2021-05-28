// 引入vue 和 Vuex
import Vue from 'vue';
import Vuex from 'vuex';
// 在vue中使用Vuex
Vue.use(Vuex);
// 将 store 分割成模块（module）
const store = new Vuex.Store({
  state: { counter:1,
          stompClient:null,//连接
          motorId:0,
          ip:null,
          initInfo:'',
          rootPage:0,
          firstPage:0,
          secondPage:0,
          thirdPage:0,
          connectstate:false,
          uid:'',
          resinfo:'',//信息栏内内容
          state:
          },
});
export default store