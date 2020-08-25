<template>
  <div id="app">
    <!-- <img src="./assets/logo.png"> -->
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
 data(){
 return{

  }
  },
  methods: { 
getData () {
 var that = this.$http.get('/testSpringBoot')
            .then(function (response) {
              console.log(response.data)
              console.log(this)
              that.serverData = response.data
            })
            .catch(function (error) {
              console.log(error)
            })
},
  },
  // fetch实现跨域
//   created() {
// // fetch方式实现跨域
// fetch("/testSpringBoot",{
//   method:"POST",
// }).then(res => {
//   console.log(res)
// })
//   }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
 margin-top: 0px;
}
</style>
