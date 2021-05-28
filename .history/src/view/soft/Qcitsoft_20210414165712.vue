<template>
<div class="width:1080px;height:780px">
    应用主界面
    
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
  
  },
  methods: {

  },
   computed: {
    secondPage(){
         debugger
        return this.$store.state.secondPage
       }, 
       stompinit(){
       return this.$store.state.stompClient
    } 
  },
  watch:{
     stompinit(newVal,oldVal){
      this.stompClient=newVal
    },
    secondPage(newVal,oldVal){
      debugger
        console.log("newVal="+newVal)
         console.log("oldVal="+oldVal)
             if(newVal!==oldVal){
          var pagesindexs=newVal.split("-")
           if(pagesindexs[2]==1)this.activeIndex='1'
           if(pagesindexs[2]==2)this.activeIndex='2-1'
           if(pagesindexs[2]==3)this.activeIndex='2-2'
           if(pagesindexs[2]==4)this.activeIndex='3-1'
           if(pagesindexs[2]==5)this.activeIndex='3-2'
           if(pagesindexs[2]==6)this.activeIndex='4'
            this.handleSelect(this.activeIndex,'#')
           this.$store.state.thirdPage=newVal
          }
    },
  },
}

</script>