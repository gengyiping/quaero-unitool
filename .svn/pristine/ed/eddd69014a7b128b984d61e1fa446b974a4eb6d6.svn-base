package com.small.qcit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.small.qcit.config.ReceiveMessage;

@Controller
public class SubController {
    @Autowired
    public SimpMessagingTemplate template;  
      
    
    @MessageMapping("/subscribe")
    public void subscribe(ReceiveMessage rm) {
        for(int i =1;i<=20;i++) {
            //广播使用convertAndSend方法，第一个参数为目的地，和js中订阅的目的地要一致
            template.convertAndSend("/topic/getResponse", rm.getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    
    @MessageMapping("/queue")
    public void queuw(ReceiveMessage rm) {
        System.out.println("进入方法");
        for(int i =1;i<=20;i++) {
            /*广播使用convertAndSendToUser方法，第一个参数为用户id，此时js中的订阅地址为
            "/user/" + 用户Id + "/message",其中"/user"是固定的*/
            template.convertAndSendToUser("zhangsan","/message",rm.getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
  //单独聊天
//    @MessageMapping("/aloneRequest")	
//    public ReceiveMessage alone(ReceiveMessage chatRoomRequest){
//        //方法用于一对一测试
//    	ReceiveMessage response=new ReceiveMessage();
//    	response.setName("1111");
//        this.template.convertAndSendToUser(chatRoomRequest.getName()+"","/alone/getResponse",response);
//        return response;
//    }
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
  //单独聊天
    @MessageMapping("/aloneRequest")	
    public ChatRoomResponse alone(ChatRoomResponse chatRoomRequest){
        //方法用于一对一测试
	System.out.println("userId = " + chatRoomRequest.getUserId());
        System.out.println("name = " + chatRoomRequest.getName());
        System.out.println("chatValue = " + chatRoomRequest.getChatValue());	       
        ChatRoomResponse response=new ChatRoomResponse();
        response.setName(chatRoomRequest.getName());       
        response.setChatValue(chatRoomRequest.getChatValue());
        messagingTemplate.convertAndSendToUser(chatRoomRequest.getUserId()+"","/alone/getResponse",response);
        return response;
    }

}
