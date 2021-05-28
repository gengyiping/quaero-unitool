<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="appk">
   </div> 
  <!-- <div class="" v-for="(item,index) in items">
      <div v-if="(items[index-1])===item"></div>
      <div v-else>{{item}}</div>
   </div>  -->
  <!--   <div class="" v-for="(item,index) in itemk">
      <div v-if="getflag(index)"></div> 
      <div v-else>{{item.name}}</div>
   </div> -->
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
     items:[1,1,2,1,1,1,5,6,7,8],
      itemk:[{name:"jonron",age:"11",kl:[2,2,2,2]},
             {name:"jonron",age:"13",kl:[2,2,2,2]},
             {name:"lilei",age:"11",kl:[2,2,2,2]},
             {name:"haha",age:"12",kl:[2,2,2,2]}]
    }
  },
   components:{
         DemoTest:demoTest
   },
  mounted() {
   
    var str="<div>"
    var oldstr=""
    for(var i=0;i<this.itemk.length;i++){
      if(this.itemk[i].name===oldstr){
            
      }else{
            str+="1"
            
      }
      oldstr=this.itemk[i].name
      
    }
    str+="</div>"
   document.getElementById("appk").innerHTML=str;
  },
  methods: {

  },
   computed: {
     /*  getflag:function(index){
        debugger
          var tmk=this.itemk[index].name
          var tmk1=this.itemk[index-1].name
          if(tmk===tmk1){
            return false
           }else{
             return true 
           }
     } */
  },
  watch:{
    
  },
}

</script>