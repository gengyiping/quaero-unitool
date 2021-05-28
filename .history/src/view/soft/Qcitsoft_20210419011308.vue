<template>
<div class="width:1080px;height:780px">
    应用主界面
    <div>
     <DemoTest></DemoTest>  
    </div>
   <div id="app">
  <ol>
     <template v-for="(site, index) in sites">
    <!--  {{ site.name}}--{{sites[index-1].name}} -->
    {{ site.name===sites[index-1].name}}
    <!--  <li v-if="{{sites[index-1].name===site.name}}">
       
      </li>
       <li v-else>
       
         {{ site.name }}
      </li>   -->
     
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
  /*  $(function(){
			$("#addBtn").click(function(){
				console.log("sadff");
				var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm'); //i g m是指分别用于指定区分大小写的匹配、全局匹配和多行匹配。
				var html = document.getElementById("temp").innerHTML;
				for(var i=0;i<4;i++){
					var source = html.replace(reg, function(node, key) {  
		                return {  
		                    'title' : '我是hbuilder啊，哈哈哈哈', 
		                    'dateA' : '2016-09-19'
		                }[key];  
		            });  
		            $(".list").append(source);
				}
			});
		}); */
  },
  methods: {

  },
   computed: {
   
  },
  watch:{
    
  },
}

</script>