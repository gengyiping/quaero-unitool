import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
//import webTest from '@/components/webTest'
import Login from '@/components/Login'
//import MOTORBASE from '@/view/debugger/motor'
Vue.use(Router)

export default new Router({
  mode: 'history',//去掉路径中的#
  routes: [
    {
      path: '/',
      name: 'Login',
     // component: webTest,
      component: Login,
    }
   
  ]
})
