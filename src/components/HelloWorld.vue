<template>
  <div class="hello">
    <button v-on:click="initload()">初始化</button>
    <button v-on:click="reset()">复位</button>
   
    <h1>{{ msg }}</h1>
    <h2>Essential Links</h2>
    <ul>
      <li>
        <a
          href="https://vuejs.org"
          target="_blank"
        >
          Core Docs
        </a>
      </li>
      <li>
        <a
          href="https://forum.vuejs.org"
          target="_blank"
        >
          Forum
        </a>
      </li>
      <li>
        <a
          href="https://chat.vuejs.org"
          target="_blank"
        >
          Community Chat
        </a>
      </li>
      <li>
        <a
          href="https://twitter.com/vuejs"
          target="_blank"
        >
          Twitter
        </a>
      </li>
      <br>
      <li>
        <a
          href="http://vuejs-templates.github.io/webpack/"
          target="_blank"
        >
          Docs for This Template
        </a>
      </li>
    </ul>
    <h2>Ecosystem</h2>
    <ul>
      <li>
        <a
          href="http://router.vuejs.org/"
          target="_blank"
        >
          vue-router
        </a>
      </li>
      <li>
        <a
          href="http://vuex.vuejs.org/"
          target="_blank"
        >
          vuex
        </a>
      </li>
      <li>
        <a
          href="http://vue-loader.vuejs.org/"
          target="_blank"
        >
          vue-loader
        </a>
      </li>
      <li>
        <a
          href="https://github.com/vuejs/awesome-vue"
          target="_blank"
        >
          awesome-vue
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
import VueAxios from 'vue-axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },

    methods:{
    initload: function () {
      // debugger
      // this.axios({
      //   method:'post',
      //   url: 'http://localhost:8888/init'
      // })
      //   .then(function (response) {
      //     console.log(response.data)
      //   })

      let ws = new WebSocket('ws://localhost:8080/websocket');
      debugger
        ws.onopen = () => {
          // Web Socket 已连接上，使用 send() 方法发送数据
          //console.log('数据发送中...')
          //ws.send('Holle')
          //console.log('数据发送完成')
        }
        ws.onmessage = evt => {
          console.log('数据已接收...')
          var received_msg = evt.data;
          console.log(received_msg);

          if("notice" == received_msg)
          {
            this.initData();
            this.play();
          }
          else{
            console.log("不刷新");
          }
        }
       /* ws.onclose = function () {
          // 关闭 websocket
          console.log('连接已关闭...')
        }
        // 路由跳转时结束websocket链接
        this.$router.afterEach(function () {
          ws.close()
        })*/
    }
  }

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
