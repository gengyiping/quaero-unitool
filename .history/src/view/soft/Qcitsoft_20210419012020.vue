<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="appk">
   </div>
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
     
    }
  },
   components:{
         DemoTest:demoTest
   },
  mounted() {
    	document.getElementById("appk").innerHTML="<div>111111111111111AAAAAAAAAAAAAAAAAAAAAAAA</div>";
  
  },
  methods: {

  },
   computed: {
   
  },
  watch:{
    
  },
}

</script>