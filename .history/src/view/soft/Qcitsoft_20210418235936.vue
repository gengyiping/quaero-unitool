<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
    <div id="app">
      <ul>
          <li v-for="(value,key,index) in content"> 角标：{{index}}------ 键名: {{key}} ------ 值：{{value}} </li>
      </ul>
  </div>
</div>
</template>
<script>
import demoTest from '@/view/debugger/demoTest'
export default {
  data() {
    return {
      user:{
                name: 'SkyManss',
                gender: '男',
                age: 26
            },
            {
                name: 'SkyManss',
                gender: '男',
                age: 25
            }
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