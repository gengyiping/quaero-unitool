// 引入vue 和 Vuex
import Vue from 'vue';
import Vuex from 'vuex';

// 在vue中使用Vuex
Vue.use(Vuex);

// 将 store 分割成模块（module）
const store = new Vuex.Store({
  state: { counter:1,
          stompClient:null,
          motorId:0,
          ip:null
          },
});

export default store