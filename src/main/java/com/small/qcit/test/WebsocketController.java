package com.small.qcit.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.method.QCITApi5;
import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.module.DataFormatException;
import com.quest.software.bus4j.module.ExecuteException;
import com.small.qcit.dto.LoginState;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class WebsocketController {
	public QCITApi5 api=new QCITApi5();
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	 @MessageMapping("/init")
	 @SendTo("/topic/getResponse")
	 public String init() {
		 System.out.println("初始化--------");
		 try {
			api.init(1, false);
		} catch (ErrCodeException | CmdCodeException | IOException | DocumentException | ExecuteException
				| DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  e.getMessage();
		}
	        return "测试初始化复位成功";
	    }
	 private List<LoginState> loginmess=new ArrayList<LoginState>();
	 @MessageMapping("/initstate/{ip}")
	 @SendTo("/user/initstate/alone/getResponse")
	 public String initstate(@DestinationVariable("ip") String ip) {
		 System.out.println("状态--------");
		 if(loginmess.size()==0){
			 LoginState logmes=new LoginState();
			 logmes.setIp(ip);
			 String[] info=new String[3];
			 info[0]="0";info[0]="#";info[1]="#";
			 logmes.setLoginInterface(info);
			 loginmess.add(logmes);
		 }else{
			 for(int i=0;i<loginmess.size();i++){
				 if(loginmess.get(i).getIp().equals(ip)){
					 String[] info=loginmess.get(i).getLoginInterface();
					 if(!"#".equals(info[0]))return info[0]+"_#_#";
					 if(!"#".equals(info[1]))return info[0]+"_"+info[1]+"_#";
					 if(!"#".equals(info[2]))return info[0]+"_"+info[1]+"_"+info[2];
				 }
			 } 
		 }
		return "";
	 }
	 @MessageMapping("/page/{ip}/{page}")
	 @SendTo("/user/page/alone/getResponse")
	 public String pageload(@DestinationVariable("ip") String ip,@DestinationVariable("page") String page) {
		 System.out.println("状态--------");
			 for(int i=0;i<loginmess.size();i++){
				 if(loginmess.get(i).getIp().equals(ip)){
					 String[] pages=page.split("_");
					 if(!"#".equals(pages[0])){
				      String[] info= loginmess.get(i).getLoginInterface();
				      info[0]=pages[0];
				      info[1]="";
				      info[2]="";
				      loginmess.get(i).setLoginInterface(info);
					 }
				 }
			 } 
	        return "";
	    }
	 
	 @MessageMapping("/reset")
	 @SendTo("/topic/getResponse")
	 //@SendTo("/user/1111/alone/getResponse")
	// @SendToUser("/user/1111/alone/getResponse")
	 public String reset() {
		 System.out.println("复位--------");
		 try {
			api.motorReset(0, 0);
		} catch (ErrCodeException | CmdCodeException | IOException | DocumentException | ExecuteException
				 e) {
			StringWriter sw = new StringWriter();    
			PrintWriter pw = new PrintWriter(sw);    
			e.printStackTrace(pw);    
			String msg=sw.toString();  
			return   msg;
		}
			//template.convertAndSendToUser("1111","/alone/getResponse","测试进样复位成功");
	        return "测试进样复位成功";
	    }
	 
	 @MessageMapping("/allreset")
	 @SendTo("/topic/getResponse")
	 public String allreset() {
		 System.out.println("ALL复位--------");
			try {
				api.allReset(0, false);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				StringWriter sw = new StringWriter();    
				PrintWriter pw = new PrintWriter(sw);    
				e.printStackTrace(pw);    
				String msg=sw.toString();  
				return   msg;
			}
	        return "测试ALL复位成功";
	    }
	 @MessageMapping("/test")
    public String test(String data) {
        return "测试成功";
    }

    /**
     * @param null:
     * @return
     * @description 方法描述 根据设置此处应该是 app/hello
     * /topic/getResponse 为订阅
     * @author zhangqiang
     * @date 2019年06月06日 17:03
     */
    @MessageMapping("/hello")
    @SendTo("/topic/getResponse")
    public String sub() throws InterruptedException {
        Thread.sleep(1000);
        return "感谢您订阅了";
    }

    @MessageMapping("welcome")
    public String say() throws Exception {
        Thread.sleep(1000);
        return "成功";
    }

    @MessageMapping("/message")
    @SendToUser("/message")
    public String userGetMessage() {
        return "message";
    }
    /**
     * 接收消息
     * @param name 姓名
     * @return welcome, [姓名] !
     *
     * @MessageMapping 类似于 @RequestMapping, 只不过映射的是 webSocket 的请求地址
     * @SendTo("/topic/getBro") 指定该方法响应给哪个 topic, 客户端订阅了 /topic/getBro 的都能收到方法响应
     */
//    @MessageMapping("/welcome")
//    @SendTo("/topic/getBro")
//    public String say(String name) {
//        log.info("name: " + name);
//        return "welcome, " + name + " !";
//    }

    /**
     * 广播式发送消息给订阅了「/topic/getBro」的客户端
     */
//    @RequestMapping("sendMsgBro")
//    @ResponseBody
//    public void sendMsg() {
//        messagingTemplate.convertAndSend("/topic/getBro", "服务器主动推送的广播消息");
//    }

    /**
     * 发送消息给指定 sessionId 的客户端, 且该客户端订阅了「/topic/getBro」
     *
     * @param sessionId 客户端的 sessionId
     */
//    @RequestMapping("sendMsgPoint")
//    @ResponseBody
//    public void sendMsgPoint(String sessionId) {
//        messagingTemplate.convertAndSendToUser(sessionId, "/user/getPoint", "服务器主动推送的点对点消息", createHeaders(sessionId));
//    }
//
//    private MessageHeaders createHeaders(String sessionId) {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        headerAccessor.setSessionId(sessionId);
//        headerAccessor.setLeaveMutable(true);
//        return headerAccessor.getMessageHeaders();
//    }
}
