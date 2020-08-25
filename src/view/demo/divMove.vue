<template>
<div class="">
  <div style="">
        <el-button @click="moveLeft" >单个向左</el-button>
        <el-button @click="leftStop" >向左立即停止</el-button>
        <div id="left" style="">
                <div class="mleft" id="mleft"></div><br>
         </div>
         <div style="float: left;">
           代码如下：
         </div>
         <el-input type="textarea" style="white-space: pre-wrap;" :rows="8" placeholder="请输入内容" v-model="singleLeftArea">
         </el-input>
  </div>
  <br>
  <div style="">
      <el-button @click="movemtleft" >多个向左</el-button>
      <el-button @click="mtleftStop" >多个向左立即停止</el-button>
       <div id="mtileft" style="display: flex">
                <div class="mtleft" id="mtleft1" ></div>
                <div class="mtleft" id="mtleft2"></div>
                <div class="mtleft" id="mtleft3" ></div>
        </div>
   <br>
  </div>
  <div style="float: left;">
       <el-button @click="moveTop" >div旋转</el-button>
       <el-button @click="movebottom" >向上</el-button>
      <div id = "box"></div>
   </div>
        
        
        
        <div>
           
        <div id="top">
          <div></div><br>
        </div>
        <div id="bottom">
          <div></div><br>
        </div>
       </div>
     
</div>
</template>
<script>
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'
import $ from 'jquery'

 var stopleft = true;
 var  mtistop = true;
 var rotateTimes = 1;
export default {
  data() {
    return {
      list1: [],
      stompClient: null,
      stopleft,
      mtistop,
      rotateTimes,
      singleLeftArea: ' <div style=""> '+
        '<el-button @click="moveLeft" >单个向左</el-button> '+
        '<el-button @click="leftStop" >向左立即停止</el-button> '+
        '<div id="left" style=""> '+
        '       <div class="mleft" id="mleft"></div><br> '+
        ' </div> '+
        ' <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="singleLeftArea"> '+
        ' </el-input>'+
        '</div>'+
         ' <script>'+
        'import $ from "'+'jquery'+'"'+
        ' var stopleft = true;'+
        ' export default {'+
        '   data() {'+
        '    return {'+
        '    list1: [],'+
        '    stompClient: null,'+
        '    stopleft,'+
        '    }'+
        '   },'+
        ' methods: {'+
        '   moveLeft(){'+
        '   var b=$("#mleft").offset().left;//移动div当前位置'+
        '  for(var i=0;i<30;i++){'+
        '    setTimeout(function(){'+
        ' if(stopleft==false)return;'+
        '      if(b<300-10){//防止移到方块外,背景div 300,移动div 10'+
    		'          b=b+10;//目标位置'+
        '         $("#mleft").animate({left:b+"'+'px'+'"});//y移动'+
        '      }'+
        '  }, 1000*i);'+
        '   }'+
        '},'+
        'leftStop(){'+
        ' stopleft=false;'+  
        ' },'+
        ' }'+
        ' }'+
        ' <'+"/"+'script>'+
        '<style rel="stylesheet/scss" lang="scss">'+
        '#left{'+  
        '       width:300px;'+
        '       height: 50px;'+
        '       background: red;'+
        '      }'+
        '   .mleft{'+
        '       position:relative;'+
        '        width:10px;'+
        '        height: 50px;'+
        '        background: rgb(0, 102, 255);'+ 
        '      }'+
        '        <'+"/"+'style>',

        
      
    }
  },
  mounted() {
  },
  methods: {
      moveLeft(){
          stopleft = true;
          var b=$("#mleft").offset().left;//移动div当前位置
          for(var i=0;i<30;i++){
            setTimeout(function() {
          console.log("stopleft="+stopleft);
         if(stopleft==false)return;
              if(b<300-10){//防止移到方块外,背景div 300,移动div 10
    		          b=b+10;//目标位置
                 $("#mleft").animate({left:b+'px'});//y移动
              }
          }, 1000*i);
           }
     },
     leftStop(){
         stopleft=false;  
     },
     movemtleft(){
       mtistop = true;
        //debugger
       // var a=$("#mtileft").offset().left;//背景div当前位置
         var b=0;  //$("#mtileft div").offset().left;//移动div当前位置
          for(var i=0;i<30;i++){
            setTimeout(function() {
           if(mtistop==false)return;
              if(b<300-10*3){//防止移到方块外,背景div 300,移动div 10
              for(var j=1;j<4;j++){
                b=b+10;//目标位置
                $("#mtileft div").animate({left:b+'px'});//y移动
              }
             }
             }, 1000*i);
           }
     },
     mtleftStop(){
       mtistop=false;  
     },

      sleep(numberMillis) {   //定义睡眠 
             var now = new Date();    
             var exitTime = now.getTime() + numberMillis;   
             while (true) { 
               now = new Date();       
             if (now.getTime() > exitTime) 
                     return;    
              } 
        },
moveRight(){
},
moveTop(){
  debugger;
  var box = document.getElementById("box");
            box.style.webkitTransform = 'rotate('+90*rotateTimes+'deg)';//webkit内核的浏览器
            box.style.mozTransform = 'rotate('+90*rotateTimes+'deg)';
            box.style.msTransform = 'rotate('+90*rotateTimes+'deg)';
            box.style.oTransform = 'rotate('+90*rotateTimes+'deg)';
            box.style.transform = 'rotate('+90*rotateTimes+'deg)';
			
			console.log("finished");
			rotateTimes++;
},
movebottom(){
}

  }
}
</script>
<style rel="stylesheet/scss" lang="scss">
.el-tab-pane{
    margin-top: 30px;
}
#left{
  
width:300px;
height: 50px;
background: red;
}
.mleft{
   position: relative;
   width:10px;
   height: 50px;
  background: rgb(0, 102, 255); 
}

#mtileft{
width:300px;
height: 50px;
background: red;
}
.mtleft{
   position:relative;
   width:10px;
   height: 48px;
  background: rgb(0, 102, 255); 
  border:1px solid #000
}
#box{
			width: 150px;
			height: 120px;
			background: red;
			margin: 100px;
			border-bottom: 2px solid black;
		}

</style>