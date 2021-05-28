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
    debugger
   this.stompClient= this.$store.state.stompClient
   this.activated()
  },
  methods: {
    activated() {
    this.activeIndex = '1' // 路由跳转设置初始值
    },
 handleSelect(key, keyPath) {
   var _this=this
       var indexkey=0;
        console.log(key, keyPath);
        if(key=="1")indexkey=1;
        if(key=="2-1")indexkey=2;
         if(key=="2-2")indexkey=3;
        if(key=="3-1")indexkey=4;
        if(key=="3-2")indexkey=5;
         if(key=="4")indexkey=6;
        this.index =indexkey;
        if(keyPath!='#'){
      // var pageL= localStorage.getItem('page')
      // var pages=pageL.split("_")
        var page='#-#-'+this.index+'-#'
            this.$store.state.stompClient.send('/app/page/'+page,{})
       }
      }
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