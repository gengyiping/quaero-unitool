<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="app">
  <ol>
     <template v-for="(site, index) in sites">
      <li v-if="sites[index-1]===site.name ">
       
      </li>
       <li v-else>
       
         {{ site.name }}
      </li>
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
  
  },
  methods: {

  },
   computed: {
   
  },
  watch:{
    
  },
}

</script>