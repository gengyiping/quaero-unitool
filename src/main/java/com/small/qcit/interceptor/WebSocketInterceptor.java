package com.small.qcit.interceptor;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.small.qcit.Controller.optContorller;
import com.small.qcit.constant.UserStatusConstant;
import com.small.qcit.domain.UserCache;
import com.small.qcit.domain.dto.User;
import com.small.qcit.utils.CheckUtils;
import com.small.qcit.utils.SensitiveWordUtils;
import com.small.qcit.utils.UUIDUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * webcocket拦截器
 *
 */
@Component
@Slf4j
public class WebSocketInterceptor implements ChannelInterceptor {
    /**
     * 绑定用户信息
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
       // log.debug("进入拦截器 -> preSend");
        StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        optContorller opt=new optContorller();
        //1、判断是否首次连接
        if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())) {
        	String userName=stompHeaderAccessor.getFirstNativeHeader("userName");
        	 String projectName=stompHeaderAccessor.getFirstNativeHeader("projectName");
        	 String ip=stompHeaderAccessor.getFirstNativeHeader("ip");
        		User user = new User();
                user.setUserId(UUIDUtils.create());
                user.setUsername(SensitiveWordUtils.loveChina(userName));
                user.setProjectname(projectName);
                //String[] page=stompHeaderAccessor.getFirstNativeHeader("page").split("_");
                //user.setAddress(stompHeaderAccessor.getFirstNativeHeader("address"));
                user.setStatus(UserStatusConstant.ONLINE);
                user.setIp(ip);
                
               
                String initflag=stompHeaderAccessor.getFirstNativeHeader("initflag");
                List<User> userList=opt.getuserList();
              
                if ("admin".equals(userName) && "123456".equals(stompHeaderAccessor.getFirstNativeHeader("passWord"))){
                	
                	//if(userList.size()==0) {
//                		  user.setRootPage("0");
//                		  user.setFirstPage("#");
//                		  user.setSecondPage("#");
//                		  user.setThirdPage("#");
//                		  opt.setuserList(user);
                      	stompHeaderAccessor.setUser(user);
                    //  }else {
//                    	  boolean ipflag=false;
//                    	  for(int i=0;i<userList.size();i++){
//             				 if(userList.get(i).getUsername().equals(userName)&&ip.equals(userList.get(i).getIp())){//用户名相同时保存界面
//             					 ipflag=true;
//             					 stompHeaderAccessor.setUser(user);
//             				 }
//             			 }
//                    	  if(ipflag==false) {
                    	//	  stompHeaderAccessor.setUser(user);
                    	  //}
                    //  }
                	
//                	List dd=UserCache.listUser();
//                	for(int i=0;i<dd.size();i++){
//                		System.out.println("dd"+dd.get(i));
//                	}
                	
                     log.debug("绑定用户信息 -> {}", user);
                    return message;
                }else {
                    return null;
                }
        }
		return message;
    }
}
