// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueCookies from 'vue-cookies'
import {setCookie, getCookie, delCookie} from './util/util.js'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

Vue.use(ElementUI)
Vue.use(store)
Vue.use(VueCookies)
//Vue.use(NProgress)
NProgress.inc(0.2)
NProgress.configure({easing:'ease',speed:500,showSpinner:false})
//import axios from 'axios'
//import VueAxios from 'vue-axios'
//Vue.use(axios)
//Vue.use(VueAxios)
Vue.config.productionTip = false

router.afterEach(() => {
  NProgress.done()
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  // 就可以this.$store引用了
  store,
   components: { App },
   beforeMount () {
    Vue.prototype.$cookieStore = {
      setCookie,
      getCookie,
      delCookie
    }
  },
  template: '<App/>'
  // render: h => h(App)
})
