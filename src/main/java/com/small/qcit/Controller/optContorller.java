package com.small.qcit.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.quaero.method.QCITApi;
import com.small.qcit.domain.dto.User;
import com.small.qcit.domain.vo.MessageRO;
import com.small.qcit.domain.vo.MessageVO;
import com.small.qcit.enums.CodeEnum;
import com.small.qcit.enums.MessageTypeEnum;
import com.small.qcit.interceptor.WebSocketInterceptor;
import com.small.qcit.service.MessageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class optContorller {
	@Resource
    private MessageService messageService;
	public List<User> loginmess=new ArrayList<User>();//所有用户
	public static QCITApi api=null;
	static{
		try{
			api=new QCITApi();
        }catch(Exception e){
              e.printStackTrace();
        }
	}
	
    public  void successSend(String url,String message,User user){
    	try {
    	 MessageRO messageRO=new MessageRO();
    	 messageRO.setMessage(message);
			messageService.sendMessage(url, new MessageVO(user, messageRO.getMessage(),MessageTypeEnum.USER));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public  void errorSend(Exception e,User user){
    	    StringWriter sw = new StringWriter();    
			PrintWriter pw = new PrintWriter(sw);    
			e.printStackTrace(pw);
			 log.error("fount error 。。。",e.toString());
			CodeEnum.ABNORMAL.setDesc(e.toString());
		    messageService.sendErrorMessage(CodeEnum.ABNORMAL, user);
	}
    public List getuserList() {
		return this.loginmess;
    }
    public void setuserList(User user) {
    	loginmess.add(user);
    }

}
