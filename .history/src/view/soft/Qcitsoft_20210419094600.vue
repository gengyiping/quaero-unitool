<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="appk">
   </div>
   <div class="" v-for="(item,index) in items">
      <div v-if="(items[index-1])===item"></div>
      <div v-else>{{item}}</div>
   </div>
    <div class="" v-for="(item,index) in itemk">
     <!-- <div v-if="itemk[(index-1)].name===item.name"></div> -->
      <div v-if="getflag(itemk,index)"></div> 
      <div v-else>{{item.name}}</div>
   </div>
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
     items:[1,1,2,1,3,1,5,6,7,8],
      itemk:{name:"jonron",name:"lilei",name:"haha"}
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
      getflag:function(){
  
     }
  },
  watch:{
    
  },
}

</script>