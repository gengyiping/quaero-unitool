package com.small.qcit.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import com.quest.common.util.CmdExecUtil;
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
import com.small.qcit.interceptor.WebSocketInterceptor;
import com.small.qcit.service.MessageService;
import com.small.qcit.utils.CheckUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
public class WebsocketController  extends optContorller{
	public String initState=null;
	@Autowired
    private SimpMessagingTemplate messagingTemplate;
//	 @Resource
//	    private MessageService messageService;
	
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
		    log.info("开始连接设备信息"); 
			  try {
				  api.startClientLog();//开启客户端日志
				api.init();
				initState="初始化成功";
				 log.info(initState); 
				successSend("/topic/getResponse", initState, user);
			} catch (Exception e) {
				errorSend(e, user);
			}
	 }
	 @MessageMapping("/serverLog")
	    public void serverLog(User user){
		     log.info("开始开启服务端日志"); 
			  try {
				  api.startLog();//开启服务端日志
				String mess="开启服务端日志成功";
				 log.debug(mess); 
				successSend("/user/"+user.getIp()+"/serverLog/getResponse", mess, user);
			} catch (Exception e) {
				errorSend(e, user);
			}
	 }
	 @MessageMapping("/eventLog")
	    public void eventLog(User user){
		  log.info("开始开启启事件日志"); 
			  try {
				  api.starteventLog();//开启事件日志
				  String mess="开启启事件日志成功";
				  log.info(mess); 
				successSend("/user/"+user.getIp()+"/eventLog/getResponse", mess, user);
			} catch (Exception e) {
				errorSend(e, user);
			}
	 }
	 private static String  propath=System.getProperty("user.dir")+"\\qcit\\conf\\";
	 @MessageMapping("/oneToconf")
	    public void oneToconf(User user){
		 log.info("开始一键改配置"); 
			  try {
				//获取qcit/conf下所有文件
					List lsfire=getFiles(propath);
					String files=StringUtils.join(lsfire , ",");
	                   //下载Xml文件
						String ipPort = api.getPropertys(0, "Ip_qcit_location", "QCIT.MB.ipPort");
							String serid=api.getFirVersion(0, 1);//获取芯片ID
							String file="qcit_location";
							String mess=copyCoord(0,file,serid,ipPort);
							if(mess=="") {
								mess="一键改配置成功";
							}
							 log.info(mess); 
				successSend("/user/"+user.getIp()+"/oneToconf/getResponse", mess, user);
			} catch (Exception e) {
				errorSend(e, user);
			}
	 }
	  public static List<String> getFiles(String path) {
	        List<String> files = new ArrayList<String>();
	        File file = new File(path);
	        File[] tempList = file.listFiles();

	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()) {
	                files.add(tempList[i].toString());
	                //文件名，不包含路径
	                //String fileName = tempList[i].getName();
	            }
	            if (tempList[i].isDirectory()) {
	                //这里就不递归了，
	            }
	        }
	        return files;
	    }
		/**
		 * 
		 * @param boardIdx 
		 * @param file 
		 * @param serid 电路板ID
		 * @param ipPort
		 * @throws ErrCodeException
		 * @throws ExecuteException
		 */
		public String copyCoord(int boardIdx,String file,String serid,String ipPort) throws ErrCodeException, ExecuteException{
			String cms=""; 
			String ip=ipPort.split(":")[0];
			   String file1=file+"_"+serid;
			   File kFile=new File(propath+file1+".xml");
			   if(!kFile.exists()){//若不包含221_+serid.xml的文件,创建文件
				   api.setSwFunc(boardIdx, 0x5000, 1, 0, 0);//开启tftp服务
				   String command1=System.getProperty("user.dir")+"\\tftp.exe -i -v "+ip+" GET /config/"+file+".xml "+propath+file1+".xml";
					boolean cmdflag1=CmdExecUtil.processExec(command1);
					if(kFile.length()>0){
						
					}else{
						 //kFile.mkdir();
						   copy01(propath+file+".xml",propath+file1+".xml");//复制文件内容到创建的文件中	
//						   List<ElementNode> jddb=Dom4jUtils.readXml(kFile);//文件节点定标信息
//						   for (int j = 0; j < jddb.size(); j++) {
//							   ElementNode jd= jddb.get(i);
//							   String key=jd.getRootElement()+"."+jd.getAttrnode()+"."+jd.getNode();
//							   
//						   }
					 cms="您还没有上传定标文件到电路板，请尽快将定标文件上传到设备中:\\n 若模块定标中设备参数能读取到参数，请先一键下载设备参数到本地;\\n若模块定标中设备参数不能读取到参数，请先定标，再一键上传至设备电路板中;";

					}
					api.setSwFunc(boardIdx, 0x5000, 0, 0, 0);//关闭tftp服务
			   }else if(kFile.exists()&&kFile.length()==0){//若包含221_+serid.xml的文件,且大小为零，删除该文件
				   kFile.delete();
				   api.setSwFunc(boardIdx, 0x5000, 1, 0, 0);//开启tftp服务
				   String command1=System.getProperty("user.dir")+"\\tftp.exe -i -v "+ip+" GET /config/"+file+".xml "+propath+file1+".xml";
					boolean cmdflag1=CmdExecUtil.processExec(command1);
					if(kFile.length()>0){
						
					}else{
						   copy01(propath+file+".xml",propath+file1+".xml");//复制文件内容到创建的文件中	
					 cms="您还没有上传定标文件到电路板，请尽快将定标文件上传到设备中:\\n 若模块定标中设备参数能读取到参数，请先一键下载设备参数到本地;\\n若模块定标中设备参数不能读取到参数，请先定标，再一键上传至设备电路板中;";

					}
					api.setSwFunc(boardIdx, 0x5000, 0, 0, 0);//关闭tftp服务
			   }else if(kFile.exists()&&kFile.length()!=0){//若包含221_+serid.xml的文件,且大小为零，删除该文件
					 cms="您已使用过一键改配置";
			   }
			return cms;
		}
		public static void copy01(String sourceFile,String targetFile) 
		{

			BufferedReader br = null;
			FileWriter fw;
			BufferedWriter bw=null;
			 Writer writer=null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF-8"));
				 writer = new BufferedWriter(
						                         new OutputStreamWriter(
						                                 new FileOutputStream(targetFile), "UTF-8"));
						 
					String line = null;
					boolean flag=false;
					while( ( line = br.readLine() ) != null ){
						if(!flag)
						{
						flag=true;
						writer.write(line);
						}
						else
						{
						  writer.write("\r\n");
						  writer.write(line);
						}
					}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					br.close();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}
	 /**
	  * 查询是否连接
	  * @param user
	  */
	 @MessageMapping("/ifInit")
	    public void ifInit(User user){
		 log.info("开始查询是否初始化"); 
		 if("初始化成功".equals(initState)){
			 log.info("初始化状态："+initState); 
			 successSend("/user/"+user.getIp()+"/ifInit/alone/getResponse", "false", user);
		 }
	 }
	 
	 @MessageMapping("/initInfo")
	// @SendTo("/user/"+user.getIp()+"/initInfo/alone/getResponse")
	 public void initInfo(User user) {
		 log.info("开始获取连接信息"); 
		 String initInfo="";
		 try {
			String ipPort = api.getPropertys(0, "Ip_qcit_location", "QCIT.MB.ipPort");
			String fir=api.getFirVersion(0, 0);
			String dll=api.getDllVersion();
			String chip=api.getFirVersion(0, 1);
			initInfo=ipPort+"-"+fir+"-"+dll+"-"+chip;
			 log.info("取连接信息内容："+initInfo); 
			successSend("/user/"+user.getIp()+"/initInfo/alone/getResponse", initInfo, user);
		} catch ( Exception e) {
			errorSend(e, user);
		}
	        //return initInfo;
	    }
	 
	 /**
	  * 保存所在页面
	  * @param ip
	  * @param page
	  * @return
	  */
	 @MessageMapping("/page/{page}")
	// @SendTo("/user/"+user.getIp()+"/page/alone/getResponse")
	 public void pageload(@DestinationVariable("page") String page,User user) {
		 log.info("开始保存所在页面："+page); 
		 String[] info=page.split("-");
		 if(loginmess.size()==0){
			 User usernew=new User();
			 usernew.setUserId(user.getUserId());
			 usernew.setUsername(user.getUsername());
			 usernew.setProjectname(user.getProjectname());
			 usernew.setRootPage("0");
			 usernew.setFirstPage("#");
			 usernew.setSecondPage("#");
			 usernew.setThirdPage("#");
			 usernew.setIp(user.getIp());
			 loginmess.add(usernew);
		 }else {//存在用户名相同，ip不同的也添加
			 boolean ipflag=false;
			 for(int i=0;i<loginmess.size();i++){
				 if(user.getUsername().equals(loginmess.get(i).getUsername())&&user.getIp().equals(loginmess.get(i).getIp())){//用户名相同时保存界面
					 ipflag=true;
				 }
			 }
			 if(ipflag==false) {
				 User usernew=new User();
				 usernew.setUserId(user.getUserId());
				 usernew.setUsername(user.getUsername());
				 usernew.setProjectname(user.getProjectname());
				 usernew.setRootPage("0");
				 usernew.setFirstPage("#");
				 usernew.setSecondPage("#");
				 usernew.setThirdPage("#");
				 usernew.setIp(user.getIp());
				 loginmess.add(usernew);
			 }
		 }
		 for(int i=0;i<loginmess.size();i++){
			 if(user.getUsername().equals(loginmess.get(i).getUsername())&&user.getIp().equals(loginmess.get(i).getIp())){//用户名相同时保存界面
				 if(!loginmess.get(i).getRootPage().equals(info[0])&&!"#".equals(info[0])){
					 loginmess.get(i).setRootPage(info[0]);
					 loginmess.get(i).setFirstPage("#");
					 loginmess.get(i).setSecondPage("#");
					 loginmess.get(i).setThirdPage("#");
				 }else if(!loginmess.get(i).getFirstPage().equals(info[1])&&!"#".equals(info[1])){
						 loginmess.get(i).setFirstPage(info[1]);
						 loginmess.get(i).setSecondPage("#");
						 loginmess.get(i).setThirdPage("#");
					 }else if(!loginmess.get(i).getSecondPage().equals(info[2])&&!"#".equals(info[2])){
							 loginmess.get(i).setSecondPage(info[2]);
							 loginmess.get(i).setThirdPage("#");
						 }else if(!loginmess.get(i).getThirdPage().equals(info[3])&&!"#".equals(info[3])){
								 loginmess.get(i).setThirdPage(info[3]);
							 }
			 }
		 }
	 }
	 /**
	  * 获取上一次所在页面状态
	  */
	 @MessageMapping("/initstate")
	// @SendTo("/user/"+user.getIp()+"/initstate/alone/getResponse")
	 public void initstate(User user) {
		 System.out.println("状态--------");
		 String[] info=new String[4];
		 if(loginmess.size()==0){
			 User logmes=new User();
			 logmes.setUserId(user.getUserId());
			 logmes.setUsername(user.getUsername());
			 logmes.setProjectname(user.getProjectname());
			 logmes.setRootPage("0");
			 logmes.setFirstPage("#");
			 logmes.setSecondPage("#");
			 logmes.setThirdPage("#");
			 logmes.setIp(user.getIp());
			 loginmess.add(logmes);
		 }else{//存在用户名相同，ip不同的也添加
			 boolean ipflag=false;
			 for(int i=0;i<loginmess.size();i++){
				 if(user.getUsername().equals(loginmess.get(i).getUsername())&&user.getIp().equals(loginmess.get(i).getIp())){//用户名相同时保存界面
					 ipflag=true;
				 }
			 }
			 if(ipflag==false) {
				 User usernew=new User();
				 usernew.setUserId(user.getUserId());
				 usernew.setUsername(user.getUsername());
				 usernew.setProjectname(user.getProjectname());
				 usernew.setRootPage("0");
				 usernew.setFirstPage("#");
				 usernew.setSecondPage("#");
				 usernew.setThirdPage("#");
				 usernew.setIp(user.getIp());
				 loginmess.add(usernew);
			 }
			  
		 }
		 for(int i=0;i<loginmess.size();i++){
			 if(loginmess.get(i).getUsername().equals(user.getUsername())&&user.getIp().equals(loginmess.get(i).getIp())){
				 User infos=loginmess.get(i);
				 String initInfo= infos.getRootPage()+"-"+infos.getFirstPage()+"-"+infos.getSecondPage()+"-"+infos.getThirdPage();
					successSend("/user/"+user.getIp()+"/initstate/alone/getResponse", initInfo, user);
			 }
		 }
	 }

	 
	 @MessageMapping("/reset")
	 @SendTo("/topic/getResponse")
	 //@SendTo("/user/"+user.getIp()+"/1111/alone/getResponse")
	// @SendToUser("/user/"+user.getIp()+"/1111/alone/getResponse")
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
@Data
class MapPage{
	private String pageKey;
	private String pageValue;
}
