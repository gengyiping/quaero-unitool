package com.small.qcit.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.module.DataFormatException;
import com.quest.software.bus4j.module.ExecuteException;
import com.small.qcit.constant.RobotConstant;
import com.small.qcit.constant.StompConstant;
import com.small.qcit.domain.dto.LoginState;
import com.small.qcit.domain.dto.User;
import com.small.qcit.domain.vo.MessageRO;
import com.small.qcit.domain.vo.MessageVO;
import com.small.qcit.enums.CodeEnum;
import com.small.qcit.enums.MessageTypeEnum;
import com.small.qcit.enums.inter.Code;
import com.small.qcit.exception.ErrorCodeException;
import com.small.qcit.service.MessageService;
import com.small.qcit.utils.CheckUtils;



@RestController
public class WebsocketController  extends optContorller{
	public String initState=null;
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
	 @Resource
	    private MessageService messageService;
//	 @MessageMapping(StompConstant.PUB_USER)
//	    public void sendToUser(MessageRO messageRO, User user) throws Exception {
//	        if (!CheckUtils.checkMessageRo(messageRO) || !CheckUtils.checkUser(user)) {
//	            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
//	        }
//
//	        messageService.sendMessageToUser(messageRO.getReceiver(), new MessageVO(user, messageRO.getMessage(),
//	                messageRO.getImage(), MessageTypeEnum.USER, messageRO.getReceiver()));
//	    }
	 @MessageMapping("/init")
	    public void init(User user){
//	        if (!CheckUtils.checkMessageRo(messageRO) || !CheckUtils.checkUser(user)) {
//	            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
//	        }//参数验证
//	        if (CheckUtils.checkMessage(message)) {
//	        }
			 if(initState==null&&!"".equals(initState)){
			  try {
				  api.startClientLog();
				api.init();
				initState="初始化成功";
				//String message = messageRO.getMessage();
				successSend("/topic/getResponse", initState, user);
			} catch (Exception e) {
				errorSend(e, user);
			}
			 }
	 }
	 @MessageMapping("/initInfo")
	// @SendTo("/user/initInfo/alone/getResponse")
	 public void initInfo(User user) {
		 System.out.println("初始化信息--------");
		 String initInfo="";
		 try {
			String ipPort = api.getPropertys(0, "small_qcit_location", "QCIT.MB.ipPort");
			String fir=api.getFirVersion(0, 0);
			String dll=api.getDllVersion();
			String chip=api.getFirVersion(0, 1);
			initInfo=ipPort+"-"+fir+"-"+dll+"-"+chip;
			successSend("/user/initInfo/alone/getResponse", initInfo, user);
		} catch ( Exception e) {
			errorSend(e, user);
		}
	        //return initInfo;
	    }
	 
//	 @MessageMapping("/init")
//	 @SendTo("/topic/getResponse")
//	 public String init() {
//		 System.out.println("初始化--------");
//		 try {
//			 if(initState==null&&!"".equals(initState)){
//			  api.startClientLog();
//			  api.init();
//			  initState="成功";
//			 }
//
//		} catch (ErrCodeException | CmdCodeException | IOException | DocumentException | ExecuteException
//				| DataFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return  e.getMessage();
//		}
//	        return "初始化成功";
//	    }
//	 @MessageMapping("/initInfo")
//	 @SendTo("/user/initInfo/alone/getResponse")
//	 public String initInfo() {
//		 System.out.println("初始化信息--------");
//		 String initInfo="";
//		 try {
//			String ipPort = api.getPropertys(0, "small_qcit_location", "QCIT.MB.ipPort");
//			String fir=api.getFirVersion(0, 0);
//			String dll=api.getDllVersion();
//			String chip=api.getFirVersion(0, 1);
//			initInfo=ipPort+"-"+fir+"-"+dll+"-"+chip;
//		} catch (ErrCodeException | IOException | DocumentException | ExecuteException
//				| DataFormatException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return  e.getMessage();
//		}
//	        return initInfo;
//	    }
	
	 /**
	  * 获取上一次所在页面状态
	  */
	 private List<LoginState> loginmess=new ArrayList<LoginState>();
	 @MessageMapping("/initstate/{ip}")
	 @SendTo("/user/initstate/alone/getResponse")
	 public String initstate(@DestinationVariable("ip") String ip) {
		 System.out.println("状态--------");
		 String[] info=new String[4];
		 if(loginmess.size()==0){
			 LoginState logmes=new LoginState();
			 logmes.setIp(ip);
			
			 info[0]="0";info[1]="0";info[2]="#";info[3]="#";
			 logmes.setRootPage("0");
			 logmes.setFirstPage("0");
			 logmes.setSecondPage("#");
			 logmes.setThirdPage("#");
			 loginmess.add(logmes);
		 }else{
			 for(int i=0;i<loginmess.size();i++){
				 if(loginmess.get(i).getIp().equals(ip)){
					 LoginState infos=loginmess.get(i);
					 return infos.getRootPage()+"_"+infos.getFirstPage()+"_"+infos.getSecondPage()+"_"+infos.getThirdPage();
//					 if(!"#".equals(infos.getRootPage()))return info[0]+"_#_#_#";
//					 if(!"#".equals(infos.getFirstPage()))return info[0]+"_"+info[1]+"_#_#";
//					 if(!"#".equals(infos.getSecondPage()))return info[0]+"_"+info[1]+"_"+info[2]+"_#";
//					 if(!"#".equals(infos.getThirdPage()))return info[0]+"_"+info[1]+"_"+info[2]+"_"+info[3];
				 }
			 } 
		 }
		return "";
	 }
	 /**
	  * 保存所在页面
	  * @param ip
	  * @param page
	  * @return
	  */
	 @MessageMapping("/page/{ip}/{page}")
	 @SendTo("/user/page/alone/getResponse")
	 public String pageload(@DestinationVariable("ip") String ip,@DestinationVariable("page") String page) {
		 System.out.println("状态--------");
		    boolean tmstate=false;
		    String[] info=page.split("_");
			 for(int i=0;i<loginmess.size();i++){
				 if(loginmess.get(i).getIp().equals(ip)){
					 tmstate=true;
					 if(!loginmess.get(i).getRootPage().equals(info[0])){
						 loginmess.get(i).setRootPage(info[0]);
						 loginmess.get(i).setFirstPage("#");
						 loginmess.get(i).setSecondPage("#");
						 loginmess.get(i).setThirdPage("#");
					 }else{
						 if(!loginmess.get(i).getFirstPage().equals(info[1])){
							 loginmess.get(i).setFirstPage(info[1]);
							 loginmess.get(i).setSecondPage("#");
							 loginmess.get(i).setThirdPage("#");
						 }else{
							 if(!loginmess.get(i).getSecondPage().equals(info[2])){
								 loginmess.get(i).setSecondPage(info[2]);
								 loginmess.get(i).setThirdPage("#");
							 }else{
								 if(!loginmess.get(i).getThirdPage().equals(info[3])){
									 loginmess.get(i).setThirdPage(info[3]);
								 }
							 } 
						 }
					 }
				 }
			 } 
			 if(tmstate==false){
				 LoginState lstate=new LoginState();
				 lstate.setIp(ip);
				 lstate.setRootPage(info[0]);
				 lstate.setFirstPage(info[1]);
				 lstate.setSecondPage(info[2]);
				 lstate.setThirdPage(info[3]);
				 loginmess.add(lstate);
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
				api.allReset();
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
