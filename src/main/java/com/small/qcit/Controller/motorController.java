package com.small.qcit.Controller;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quaero.base.MotorSeting;
import com.quaero.client.ExecuteException;
import com.quaero.method.QCITApi5;
import com.quest.common.util.CmdExecUtil;
import com.quest.common.util.GsonUtil;
import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.module.Acceleration;
import com.quest.software.bus4j.module.Speed;
import com.quest.software.bus4j.module.motor.Coordinate;
import com.quest.software.bus4j.module.motor.IMotorFlag;
import com.quest.software.bus4j.module.motor.MotorLostStep;
import com.quest.software.bus4j.module.motor.IStpMotorModuleMoreCh.IMotorCoordinateLimit;
import com.quest.software.bus4j.module.motor.MotorConfig.EmmrEna;
import com.quest.software.bus4j.module.motor.MotorConfig.EmmrTrigger;
import com.quest.software.bus4j.module.motor.MotorConfig.MicroStep;
import com.quest.software.bus4j.module.motor.MotorConfig.MotorClock;
import com.quest.software.bus4j.module.motor.MotorConfig.MotorCurrent;
import com.quest.software.bus4j.module.motor.MotorConfig.MotorEna;
import com.quest.software.bus4j.module.motor.MotorConfig.MotorForward;
import com.quest.software.bus4j.module.motor.MotorConfig.MotorRotate;
import com.quest.software.bus4j.module.motor.MotorConfig.ProtectByBus;
import com.quest.software.bus4j.module.motor.MotorConfig.StopEna;
import com.quest.software.bus4j.module.motor.MotorConfig.ZeroEna;
import com.quest.software.bus4j.module.motor.MotorConfig.ZeroSwitch;
import com.quest.software.bus4j.module.motor.MotorConfig.ZeroTrigger;
import com.quest.software.common.bus.NodeManager;
import com.quest.software.common.bus.NodeManager.Node;
import com.small.qcit.dto.Message;
import com.small.qcit.dto.MotorParameters;
import com.small.qcit.dto.MotorSetings;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;

import lombok.Data;
import lombok.val;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;


@RestController
public class motorController {
	public QCITApi5 api=new QCITApi5();
	@Autowired
    private SimpMessagingTemplate template;
	
	 @MessageMapping("/loadMotor")
	 //@SendTo("/topic/getallMotor")
	 @SendTo("/user/allmotor/alone/getResponse")
	 public String loadMotor() {
		 System.out.println("加载所有电机--------");
		// Map<String, NodeId> nodeIdMap = new TreeMap<>();
		 List<String> nodeIdMap=new ArrayList<String>();
		 String strs="";
			int i = 0;
		 for (Node node : NodeManager.SMALLQCIT.MB_NODE_LIST) {
				//String prefix = (i < 10 ? ("0" + i) : (i + "")) + "_";
				String[] nodeValue = node.getNodeName().split("_");
				//nodeIdMap.put(nodeValue[0] + "_" + prefix + nodeValue[1], node.getNodeId());
				//nodeIdMap.add(nodeValue[0] + "_" + prefix + nodeValue[1]);
			//	strs=strs+nodeValue[0] + "_" + prefix + nodeValue[1]+",";
				strs=strs+nodeValue[1]+"_"+node.getAxis().getId()+",";
				i++;
			}
		 System.out.println("aa="+strs);
		//String strs= JSONArray.fromObject(nodeIdMap).toString();
		 //String[] strs = nodeIdMap.toArray(new String[nodeIdMap.size()]);
		 
	     return strs.substring(0, strs.length()-1);
	 }
	 // @RequestMapping(value = { "/api/v1/test" }, method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
	 // public String backMotor(@Header("coord")String coord,@Header("speed")long speed,@Header("acce")long acce,@Header("motorId")int ch) {
	 @MessageMapping("/motorcontrol/{motorcontrol}")
	 @SendTo("/user/motorcontrol/alone/getResponse")
	public String backMotor(@RequestBody Message message, @DestinationVariable("motorcontrol") String motorcontrol, Principal principal) {
		 System.out.println("回零位--------");
				try {
					if("backZero".equals(motorcontrol)){
						//api.move(0, message.getMotorId(), 0x00000011, -(int)message.getCoord(), message.getSpeed(), message.getAcce());
						api.motorStep(0, message.getMotorId(), new Coordinate(message.getCoord()).inverse(), new Speed(message.getSpeed()), new Acceleration(message.getAcce()));
					}else if("farZero".equals(motorcontrol)){
						api.motorStep(0, message.getMotorId(), new Coordinate(message.getCoord()), new Speed(message.getSpeed()), new Acceleration(message.getAcce()));
					}else if("moveGo".equals(motorcontrol)){
						api.motorMove(0, message.getMotorId(), new Coordinate(message.getCoord()), new Speed(message.getSpeed()), new Acceleration(message.getAcce()));
					}else if("seekZero".equals(motorcontrol)){
						api.motorSeekZero(0, message.getMotorId(), new Speed(message.getSpeed()), new Acceleration(message.getAcce()));
					}else if("reset".equals(motorcontrol)){
						api.motorReset(0, message.getMotorId(), new Speed(message.getSpeed()), new Acceleration(message.getAcce()));
					}else if("quickReset".equals(motorcontrol)){
						api.motorQuickReset(0, message.getMotorId(), new Speed(message.getSpeed()), new Acceleration(message.getAcce()));
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					String msg=sw.toString();
					return msg;
				}
	        return "移动成功！";
	    }
	    
	 @MessageMapping("/getCoord")
	 @SendTo("/user/getCoord/alone/getResponse")
	public String getCoord(@RequestBody String motorId, Principal principal) {
		 System.out.println("获取当前坐标--------");
		 long coord=0;
				try {
					coord=api.getCurrentCoordinate(0, Integer.valueOf(motorId)).getCoordinate();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					String msg=sw.toString();
					return msg;
				}
	        return coord+"";
	    }
	 @MessageMapping("/steplost")
	 @SendTo("/user/steplost/alone/getResponse")
	public String stepLost(@RequestBody String motorId, Principal principal) {
		 System.out.println("获取当前坐标--------");
		 String mess="";
		 MotorLostStep coord=null;
				try {
					coord=api.getMotorLostStep(0, Integer.valueOf(motorId));
					//coord=new MotorLostStep(new Coordinate(10),new Coordinate(20));
					mess="正向失步数："+coord.getForward()+";反向失步数："+coord.getReverse();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					String msg=sw.toString();
					return msg;
				}
	        return mess;
	    }
	 
	 
	 @MessageMapping("/motorFlag")
	 @SendTo("/user/motorFlag/alone/getResponse")
	public String getMotorFlag(@RequestBody String motorId, Principal principal) {
		 System.out.println("获取当前坐标--------");
		 String mess;
		   IMotorFlag coord=null;
				try {
					coord=api.getMotorFlag(0, Integer.valueOf(motorId));
					mess="Busy:"+coord.isBusy()+";EmmrSensor:"+coord.getEmmrSensor()+";ZeroSensor:"+coord.getZeroSensor();
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					String msg=sw.toString();
					return msg;
				}
	        return mess;
	    }
	 @MessageMapping("/sensorStop/{sensorStop}")
	 @SendTo("/user/sensorStop/alone/getResponse")
	public String sensorStop(@RequestBody Message message, @DestinationVariable("sensorStop") String sensorStop, Principal principal) {
		 System.out.println("获取传感器停--------");
		long coord=0;
				try {
					if("moveSensorStop".equals(sensorStop)){
						coord=api.moveSensorStop(0,Integer.valueOf(message.getMotorId()),new Coordinate(message.getCoord()),Integer.valueOf(message.getSensorId()), 1);
					}else if("stepSensorStop".equals(sensorStop)){
						coord=api.stepSensorStop(0,Integer.valueOf(message.getMotorId()),new Coordinate(message.getCoord()),Integer.valueOf(message.getSensorId()), 1);
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					String msg=sw.toString();
					return msg;
				}
	        return coord+"";
	    }
	 @MessageMapping("/grooveMove")
	 @SendTo("/user/grooveMove/alone/getResponse")
	public String grooveMove(@RequestBody Message message, Principal principal) {
		 System.out.println("走计数凹槽--------");
				try {
					api.motorPositMove(0, Integer.valueOf(message.getMotorId()),new Coordinate(message.getCoord()), Integer.valueOf(message.getSensorId()));
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					String msg=sw.toString();
					return msg;
				}
	        return "走计数凹槽成功";
	    }
	 
	 @MessageMapping("/searchInlineopt")
	 @SendTo("/user/searchInlineopt/alone/getResponse")
	public String searchInlineopt(@RequestBody MotorParameters message, Principal principal) {
		 System.out.println("读写电机参数--------");
		 String msg="";
				try {
						MotorParameters mororp=new MotorParameters();
						int cmshift=api.getCmshift(0, message.getMotorId());// 细分数
						mororp.setCmshift(cmshift);
							// cmpbb.setSelectedIndex(protectByBus);//数据总线的防撞信号
						int cmstopEna=api.getCmstopEna(0, message.getMotorId()); // 是否启用紧停信号
						mororp.setCmstopEna(cmstopEna);
						int cmemmrEna=api.getCmemmrEna(0, message.getMotorId()); // 启用防撞保护传感器
						mororp.setCmemmrEna(cmemmrEna);
						int cmemmrtrigger=api.getEmmrTrigger(0, message.getMotorId());// 防撞传感器触发电平
						mororp.setCmemmrtrigger(cmemmrtrigger);
						int cmRotate=api.getRotate(0, message.getMotorId());// 电机是否是圆盘电机
						mororp.setCmRotate(cmRotate);
						int cmZeroSwitch=api.getZeroSwitch(0, message.getMotorId());// 零位传感器是否具有开关
						mororp.setCmZeroSwitch(cmZeroSwitch);
						int cmZeroEna=api.getZeroEna(0, message.getMotorId());// 零位传感器使能电平
						mororp.setCmZeroEna(cmZeroEna);
						int cmZeroTrigger=api.getZeroTrigger(0, message.getMotorId()); // 零位传感器触发电平
						mororp.setCmZeroTrigger(cmZeroTrigger);
						int cmMotorEna=api.getMotorEna(0, message.getMotorId()); // 电机使能电平
						mororp.setCmMotorEna(cmMotorEna);
						int cmMotorCurr=api.getMotorCurrent(0, message.getMotorId()); // 电机全电流电平
						mororp.setCmMotorCurr(cmMotorCurr);
						int cmMotorClk=api.getMotorClk(0, message.getMotorId()); // 电机时钟触发逻辑
						mororp.setCmMotorClk(cmMotorClk);
						int cmMotorForward=api.getMotorForward(0, message.getMotorId());// 电机正方向电平
						mororp.setCmMotorForward(cmMotorForward);
						msg=GsonUtil.toJson(mororp);
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return msg;
	 }
	 @MessageMapping("/writeInlineopt")
	 @SendTo("/user/writeInlineopt/alone/getResponse")
	public String writeInlineopt(@RequestBody MotorParameters message, Principal principal) {
		 System.out.println("写电机参数--------");
		 String msg="";
				try {
					    com.quaero.base.MotorSeting config = new MotorSeting();
						config.setMsShift(message.getCmshift());// 细分数
							// cmpbb.setSelectedIndex(protectByBus);//数据总线的防撞信号
						config.setStopEna(message.getCmstopEna());// 是否启用紧停信号
						config.setEmmrEna(message.getCmemmrEna());// 启用防撞保护传感器
						config.setEmmrTrigger(message.getCmemmrtrigger());// 防撞传感器触发电平
						config.setRotate(message.getCmRotate());// 电机是否是圆盘电机
						config.setZeroSwitch(message.getCmZeroSwitch());// 零位传感器是否具有开关
						config.setZeroEna(message.getCmZeroEna());// 零位传感器使能电平
						config.setZeroTrigger(message.getCmZeroTrigger()); // 零位传感器触发电平
						config.setMotorEna(message.getCmMotorEna());// 电机使能电平
						config.setMotorCurrent(message.getCmMotorCurr());// 电机全电流电平
						config.setMotorClk(message.getCmMotorClk());// 电机时钟触发逻辑
						config.setMotorForward(message.getCmMotorForward());// 电机正方向电平
						api.setMotorConfig(0, message.getMotorId(), config);
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return "写电机参数成功";
	 }
	 @MessageMapping("/coordLimit/{opt}")
	 @SendTo("/user/coordLimit/alone/getResponse")
	public String coordLimit(@RequestBody Message message, @DestinationVariable("opt") String opt, Principal principal) {
		
		 String msg="";
				try {
					if("read".equals(opt)){
						 System.out.println("获取最大运行坐标--------");
						 IMotorCoordinateLimit step=api.getMaxMotorCoordinateLimit(0, message.getMotorId());
						 msg=step.getLimitPhy()+";"+step.getLimitMax()+";"+opt;
					}else if("write".equals(opt)){
						System.out.println("设置最大运行坐标--------");
						api.setMaxMotorCoordinateLimit(0, message.getMotorId(), getLimit(message.getCoord(),message.getAcce()));
						msg="完成最大运行坐标设置";
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return msg;
	    }
	 @MessageMapping("/outStepForm/{opt}")
	 @SendTo("/user/outStepForm/alone/getResponse")
	public String outStepForm(@RequestBody Message message, @DestinationVariable("opt") String opt, Principal principal) {
		 String msg="";
				try {
					if("read".equals(opt)){
						 System.out.println("获取最大允许失步数--------");
						 MotorLostStep step=api.getMotorLostStepLimit(0,message.getMotorId());
						 msg=step.getForward()+";"+step.getReverse()+";"+opt;
					}else if("write".equals(opt)){
						System.out.println("设置最大允许失步数--------");
						MotorLostStep step=new MotorLostStep(new Coordinate(Long.valueOf(message.getCoord())), new Coordinate(Long.valueOf(message.getAcce())));
						api.setMotorLostStepLimit(0, message.getMotorId(),step);
						msg="完成最大允许失步数设置";
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return msg;
	    }
	 
	 @MessageMapping("/zeroOpt/{opt}")
	 @SendTo("/user/zeroOpt/alone/getResponse")
	public String zeroOpt(@RequestBody Message message, @DestinationVariable("opt") String opt, Principal principal) {
		 String msg="";
				try {
					if("read".equals(opt)){
						 System.out.println("获取零位传感器值--------");
						 Coordinate coordinate= api.getMotorCoordinateZero(0,message.getMotorId());
						 msg=coordinate.getCoordinate()+";"+opt;
					}else if("write".equals(opt)){
						System.out.println("设置零位传感器值--------");
						api.setMotorCoordinateZero(0,message.getMotorId(),new Coordinate(message.getCoord()));
						msg="完成零位传感器值设置";
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return msg;
	    }
	 @MessageMapping("/createMotorFile")
	 @SendTo("/user/createMotorFile/alone/getResponse")
	public String  createMotorFile(@RequestBody Message message, Principal principal) {
		 String msg="";
				try {
						System.out.println("---------创建电机默认文件--------");
						api.createJsonFile(0,message.getMotorId());
						msg="完成"+message.getMotorId()+"号电机创建电机默认文件";
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return msg;
	    }
	 
	 @MessageMapping("/speedAcc/{opt}")
	 @SendTo("/user/speedAcc/alone/getResponse")
	public String speedAcc(@RequestBody Message message, @DestinationVariable("opt") String opt, Principal principal) {
		 String msg="";
				try {
					if("read".equals(opt)){
						 System.out.println("获取速度加速度值--------");
						 String speed= api.getProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".SpeedMax")+"";
						 String acceleration=api.getProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".Acceleration")+"";
						 String resetSpeed=api.getProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".ResetSpeed")+"";
						 String resetAcc=api.getProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".ResetAcceleration")+"";
						 msg=speed+";"+acceleration+";"+resetSpeed+";"+resetAcc+";"+opt;
					}else if("write".equals(opt)){
						System.out.println("设置速度加速度值--------");
						   api.setProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".SpeedMax", message.getSpeed()+"");
							api.setProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".Acceleration", message.getAcce()+"");
							api.setProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".ResetSpeed", message.getResetSpeed()+"");
							api.setProperty(0, "Motor_qcit_location", "QCIT.MB1_"+message.getMotorId()+".ResetAcceleration", message.getResetAcc()+"");
						api.setMotorCoordinateZero(0,message.getMotorId(),new Coordinate(message.getCoord()));
						msg="完成速度加速度值设置";
					}
				} catch (Exception e) {
					StringWriter sw = new StringWriter();    
					PrintWriter pw = new PrintWriter(sw);    
					e.printStackTrace(pw);    
					msg=sw.toString();
					return msg;
				}
	        return msg;
	    }
	 @MessageMapping("/upload")
	 @SendTo("/user/upload/alone/getResponse")
	public String  upload(@RequestBody Fileload  file, Principal principal) {
		 String msg="";
		 File files=null;
				
					String filestr=file.getFile();
					String fileName=file.getFileName();
					//String kk=getImageString(fileByte);
						System.out.println("---------创建电机默认文件--------");
						String saveFile=System.getProperty("user.dir")+"\\qcit\\"+fileName;//储存的地址
						files=new File(saveFile);
						BufferedWriter bw=null;
						FileWriter fw=null;
				        try {  
				        	if(!files.exists()){
				        		System.out.println("文件不存在!");
				        		files.createNewFile();
				        	}
				        	fw=new FileWriter(files,true);
				        	bw=new BufferedWriter(fw);
				        	bw.write(filestr);
				        	bw.flush();//注意必须刷新缓冲区 否则无法正确写入文件
				        	
				        	String ipPort = api.getPropertys(0, "Ip_qcit_location", "QCIT.MB1.ipPort").split(":")[0];
				        	if(!"".equals(ipPort)){
								String command=System.getProperty("user.dir")+"\\tftp.exe -i -v -t20 "+ipPort+" PUT "+saveFile+" /config/"+fileName;
								api.setSwFunc(0, 0x5000, 1, 0, 0);
								boolean cmdflag=CmdExecUtil.runExec(command);
								if(cmdflag == true){
									msg="上传电机文件成功";
							    }else{
							    	msg="上传电机文件失败,请重新上传";
							    }
							}
				        } catch (Exception e) {  
				        	StringWriter sw = new StringWriter();    
							PrintWriter pw = new PrintWriter(sw);    
							e.printStackTrace(pw);    
							msg=sw.toString();
				            e.printStackTrace();  
				        } finally {  
				            if (bw != null) {  
				                try {  
				                	bw.close(); 
				                	fw.close();
				                } catch (IOException e1) {  
				                	StringWriter sw = new StringWriter();    
									PrintWriter pw = new PrintWriter(sw);    
									e1.printStackTrace(pw);    
									msg=sw.toString();
				                    e1.printStackTrace();  
				                }  
				            }  
				        }  
				
	        return msg;
	 }
	 public static String getImageString(byte[] data) throws IOException {
	        BASE64Encoder encoder = new BASE64Encoder();
	        return data != null ? encoder.encode(data):"";
	    }
	 private IMotorCoordinateLimit getLimit(long phy,long max) throws Exception {
			return new IMotorCoordinateLimit() {
				public Coordinate getLimitPhy() {
					return new Coordinate(phy);
				}
				public Coordinate getLimitMax() {
					return new Coordinate(max);
				}
			};
		}

}
@Data
class Fileload{
	private String  file;
	private String fileName;
}
