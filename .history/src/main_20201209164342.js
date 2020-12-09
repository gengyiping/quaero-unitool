// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import routers  from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookies from 'vue-cookies'

Vue.use(ElementUI)
Vue.use(store)
Vue.use(VueCookies)
//import axios from 'axios'
//import VueAxios from 'vue-axios'
//Vue.use(axios)
//Vue.use(VueAxios)
Vue.config.productionTip = false

const router = new VueRouter({
  mode: 'history',
  routes: routers
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  // 就可以this.$store引用了
  store,
   components: { App },
  //template: '<App/>'
   render: h => h(App)
})
