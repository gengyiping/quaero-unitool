<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="appk">
   </div>
   <div class="" v-for="(item,index) in [1,1,1,1,1,1,1,1,1,1]">
      <div v-if="helpeds==0">{{item}}</div>
	<img v-if="helpeds==0" class="tou1" :style="{'margin-right':(index==2||index==5?'1em':'0.2em')}" src="img/tou.png" />
	<img v-else class="tou1" :style="{'margin-right':(index==2||index==5?'1em':'0.2em')}" :src="'img/tou'+(index+1)+'.png'" />
</div>
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
     item:[1,1,2,1,3,1,5,6,7,8]
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