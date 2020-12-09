import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
//import webTest from '@/components/webTest'
import Login from '@/components/Login'
//import MOTORBASE from '@/view/debugger/motor'
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'webTest',
     // component: webTest,
      component: Login,
    }
   
  ]
})
