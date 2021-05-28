<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="app">
  <ol>
     <template v-for="(site,key, index) in sites">
       <li v-if="sites[index-1]===site.name">
       
      </li>
       <li v-else>
       
         {{ site.name }}
      </li> 
      <!--  <li>
       {{site.name}}
      </li> -->
    </template>
  </ol>
</div>
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
      sites: [
      { name: 'Runoob' },
       { name: 'Runoob' },
      { name: 'Google' },
      { name: 'Taobao' }
    ]
    }
  },
   components:{
         DemoTest:demoTest
   },
  mounted() {
     Vue.component('ele', { render: function(createElement) { return createElement( 'script', { attrs: { type: 'text/javascript', src:'//rf.revolvermaps.com/0/0/6.js?i=5oyoyi5xdt1&m=7&c=e63100&cr1=ffffff&f=arial&l=0&bv=90&lx=-420&ly=420&hi=20&he=7&hc=a8ddff&rs=80', async:'async' } } ) } })
  },
  methods: {

  },
   computed: {
   
  },
  watch:{
    
  },
}

</script>