<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="appk">
   </div>
   <div class="" v-for="(item,index) in [1,1,1,1,1,1,1,1,1,1]">
	<img v-if="helpeds==0" class="tou1" :style="{'margin-right':(index==2||index==5?'1em':'0.2em')}" src="img/tou.png" />
	<img v-else class="tou1" :style="{'margin-right':(index==2||index==5?'1em':'0.2em')}" :src="'img/tou'+(index+1)+'.png'" />
</div>
————————————————
版权声明：本文为CSDN博主「a_靖」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_35713752/article/details/98855954
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