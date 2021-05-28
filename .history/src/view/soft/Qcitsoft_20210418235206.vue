<template>
<div class="width:1080px;height:780px">
    应用主界面
     <DemoTest></DemoTest>  
   <div v-for="(item,i) in obj">{{i}}--{{item}}</div>
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
      content:[1,1,3,4,5,6,7]
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