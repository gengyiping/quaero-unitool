package com.small.qcit.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.small.qcit.constant.UserStatusConstant;
import com.small.qcit.domain.dto.User;
import com.small.qcit.utils.CheckUtils;
import com.small.qcit.utils.SensitiveWordUtils;
import com.small.qcit.utils.UUIDUtils;

import java.security.Principal;
import java.util.UUID;

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
        //1、判断是否首次连接
        if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())) {
        	String word=new CheckUtils().getPassword();
        		User user = new User();
                user.setUserId(UUIDUtils.create());
                user.setUsername(SensitiveWordUtils.loveChina(stompHeaderAccessor.getFirstNativeHeader("userName")));
                user.setProjectname(stompHeaderAccessor.getFirstNativeHeader("projectName"));
                String[] page=stompHeaderAccessor.getFirstNativeHeader("page").split("_");
                user.setRootPage(page[0]);
                user.setFirstPage(page[1]);
                user.setSecondPage(page[2]);
                user.setThirdPage(page[3]);
                //user.setAddress(stompHeaderAccessor.getFirstNativeHeader("address"));
                user.setStatus(UserStatusConstant.ONLINE);
                if ("admin".equals(stompHeaderAccessor.getFirstNativeHeader("userName")) && "123456".equals(stompHeaderAccessor.getFirstNativeHeader("passWord"))){
                	 stompHeaderAccessor.setUser(user);	
                     log.debug("绑定用户信息 -> {}", user);
                    return message;
                }else {
                    return null;
                }
        }
		return message;
    }
}
