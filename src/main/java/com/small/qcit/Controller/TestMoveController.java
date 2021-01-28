package com.small.qcit.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.module.ExecuteException;
import com.small.qcit.domain.dto.User;
@RestController
public class TestMoveController  extends optContorller{
	@Autowired
    private static SimpMessagingTemplate template;
	 @MessageMapping("/testMove/{transId}")
  public void testMove(@DestinationVariable("transId") String transId,User user) {
		     System.out.println("区域转移--------");
		     String chipId="";
				try {
					if("0".equals(transId)){
						api.iAddInject();	
					}else if("1".equals(transId)){
						api.iMoveToCache();
					}else if("2".equals(transId)){
						api.iemergInject();
					}else if("3".equals(transId)){
						List<String> strings=api.iScanBarcode();
					}else if("4".equals(transId)){
						api.injectMove();
					}else if("5".equals(transId)){
						api.imoveToTrack();
					}else if("6".equals(transId)){
						api.iTrackMove();
					}else if("7".equals(transId)){
						api.iProgInit();
					}else if("8".equals(transId)){
						api.iProOne();
					}else if("9".equals(transId)){
						api.iProToBack();
					}else if("10".equals(transId)){
						api.iBackTobasket();
					}
					 chipId="区域转移成功";
						successSend("/user/testMove/alone/getResponse", chipId, user);
				} catch (Exception e) {
					errorSend(e, user);
				}
  }
	 private static boolean suspend=true;
	 private static boolean stoped=false;
	 @MessageMapping("/testMoveStop/{suspstate}")
	// @SendTo("/user/testMoveStop/alone/getResponse")
  public void testMoveStop(@DestinationVariable("suspstate") String suspstate,User user) {
		     System.out.println("流程暂停或继续或停止--------");
		     //String chipId="";
				try {
					if("0".equals(suspstate)){
						suspend=true;
					}else if("1".equals(suspstate)){
						suspend=false;
					}else if("2".equals(suspstate)){
						stoped=true;
					}
					//successSend("/user/testMoveStop/alone/getResponse", chipId, user);
				} catch (Exception e) {
					errorSend(e, user);
				}
  }
	public static void movestate() throws InterruptedException{
		while(true){
			if(suspend){break;}
			if(suspend==false){
				Thread.sleep(1500);
			}
		}
		if(stoped){return;}
	}
	    private static Semaphore injectTrans = null;//进样至进样转移
	    private static Semaphore inBletTrans = null;//进样转移至进样皮带
	    private static Semaphore moveBletTrans = null;//进样转移至进样皮带
	    private static Semaphore outBletTrans = null;//返回入口至递进电机
	    public static ExecutorService executor;
	    public static int num=0;//进样值
	    public static int sgnum=0;//被赋的进样值，每减一次用来判断是否最后一个
	    
	 @MessageMapping("/testMoveone")
  public void testMoveone(User user) {
		     System.out.println("流程区域转移--------");
		     String chipId="";
				try {
					suspend=true;
					stoped=false;
					injectTrans = new Semaphore(1);//进样至进样转移
					   inBletTrans = new Semaphore(1);//进样转移至进样皮带
					   moveBletTrans = new Semaphore(1);//进样转移至进样皮带
					   outBletTrans = new Semaphore(1);//返回入口至递进电机
					complex(user);
					 chipId="流程转移结束";
					successSend("/user/testMoveone/alone/getResponse", chipId, user);
				} catch (Exception e) {
					errorSend(e, user);
				}
  }
	   
	     /**
	      * 复杂流转
	     * @throws Exception 
	      */
	     public  void complex(User user) throws Exception{
	    	 executor=Executors.newFixedThreadPool(25);
	     	int djnum=0;//用来判断是不是第一个进样的
	     	 api.init();
	     	movestate();
	     	 api.allReset();
	     	movestate();
	     	num= api.iAddInject();
	     	movestate();
	     	sgnum=num;
	     	System.out.println("试管数量="+num);
	     	while(true){
	     		if(sgnum>0){
	     			if(api.getSensorStatus(0, 22)==1){
	     				api.iemergInject();//急诊进样 
	     				num++;
	     				sgnum++;
	     				movestate();
	     			}else{
	     				if(djnum!=0){
	     	     			api.iMoveToCache();
	     	     			movestate();
	     	     		 }
	     			}
	     			if(djnum==(num-1)){
	              		 new Thread() {
	               			public void run() {
	               				try {
	          						api.motorReset(0, 3);
	          						movestate();
	          					} catch (CmdCodeException | ErrCodeException | ExecuteException | IOException
	          							| DocumentException | InterruptedException e) {
	          						errorSend(e, user);
	          					}
	               			}
	               		}.start();
	              	}
	     			roam(num,InjectToBack.MoveToInBlet,user);
	     			movestate();
	              	 executor.execute(() -> {
	                     try {
	                    	 roam(sgnum,InjectToBack.InBletToBackBlet,user);
	                    	 movestate();
	                     } catch (Exception e) {
	                    	 errorSend(e, user);
	                     }
	                 });
	             	djnum++;
	             	sgnum--;
	     		}else{
	     			break;
	     		}
	        		
	     	}
	     	
	     }
	     /**
	      * 流转
	     * @throws Exception 
	      * 
	      */
	     public  void  roam(int num,InjectToBack injectToBack,User user) throws Exception{
	    	 switch(injectToBack) {
	    	     case MoveToInBlet:
	    	    	 List<String> board=  api.iScanBarcode();
	    			 String texts = "";
	    			 for (int i = 0; i < board.size(); i++) {
	    					texts = texts + board.get(i) + "\r\n";
	    			 }
					successSend("/user/scanBarcode/alone/getResponse", texts, user);
	    	    	 movestate();
	               	 moveBletTrans.acquire();
	               	 api.injectMove();
	               	movestate();
	               	injectTrans.release();
	    		 break;
	             case InBletToBackBlet://进样皮带入口至返回皮带入口
	            	  inBletTrans.acquire();
	            	   api.imoveToTrack();
	            	   movestate();
	            		api.iTrackMove();
	            		movestate();
	            		moveBletTrans.release();
	            		roam(num,InjectToBack.BackBletToIPro,user);
	            		movestate();
	            	 break;
	             case BackBletToIPro://返回皮带入口至初始吸样位
	            	 outBletTrans.acquire();
	               	api.iProgInit();
	               	movestate();
	               	inBletTrans.release();
	               	roam(num,InjectToBack.IProToOne,user);
	               	movestate();
	            	 break;
	             case IProToOne://递进一格
	            	 for(int i=0;i<4;i++){
	               		api.iProOne();	
	               		movestate();
	               	}
	            	 roam(num,InjectToBack.IProToInBack,user);
	            	 movestate();
	            	 break;
	             case IProToInBack://递进至返回入口
	            	 api.iProToBack();
	            	 movestate();
	            	 outBletTrans.release();
	            	 roam(num,InjectToBack.InBackToBackOver,user);
	            	 movestate();
	            	 break;
	             case InBackToBackOver://返回卸载
	                 api.iBackTobasket();
	                 movestate();
	                 if(num==0){
	                	 
	                 }
	            	 break;
	            default:
	                break;
	    	 } 
	     }
	     enum InjectToBack {
	    	 InjectToMove,//进样值至转移
	    	 MoveToInBlet,//转移至进样皮带入口
	    	 InBletToBackBlet,//进样皮带入口至返回皮带入口
	    	 BackBletToIPro,//返回皮带入口至初始吸样位
	    	 IProToOne,//递进一格
	    	 IProToInBack,//递进至返回入口
	    	 InBackToBackOver,//返回卸载
	     }

}
