package com.small.qcit.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.base.ElementNode;
import com.quest.common.util.CmdExecUtil;
import com.quest.software.bus4j.module.Axis;
import com.quest.software.bus4j.module.motor.Coordinate;
import com.quest.software.bus4j.module.motor.MotorLostStep;
import com.small.qcit.domain.dto.User;
import com.small.qcit.domain.vo.MessageRO;
import com.small.qcit.domain.vo.MessageVO;
import com.small.qcit.enums.CodeEnum;
import com.small.qcit.enums.MessageTypeEnum;
import com.small.qcit.service.MessageService;

import lombok.Data;
import net.sf.json.JSONObject;

@RestController
public class baseController extends optContorller {
	@Resource
	private MessageService messageService;

	public void successSend(String url, String message) {

	}

	@MessageMapping("/readloacl")
	// @SendTo("/user/readloacl/alone/getResponse")
	public void readloacl(User user) {
		System.out.println("读取本地ip--------");
		String chipId = "";
		try {
			chipId = api.getPropertys(0, "small_qcit_location", "QCIT.MB.ipPort");
			successSend("/user/readloacl/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/writeloacl/{ipPort}")
	public void writeloacl(@DestinationVariable("ipPort") String ipPort, User user) {
		System.out.println("写入本地ip--------");
		String chipId = "";
		try {
			api.setProperty(0, "small_qcit_location", "QCIT.MB.ipPort", ipPort);
			chipId = "Ip写入本地成功！";
			successSend("/user/writeloacl/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/writeEquipment/{ipPort}")
	public void writeEquipment(@DestinationVariable("ipPort") String ipPort, User user) {
		System.out.println("写入设备ip--------");
		String chipId = "";
		try {
			String[] ips = ipPort.split(":");
			api.createIp(0, ips[0]);
			api.setProperty(0, "small_qcit_location", "QCIT.MB.ipPort", ipPort);
			chipId = "Ip写入设备成功！";
			successSend("/user/writeEquipment/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/readEquipment")
	public String readEquipment(User user) {
		System.out.println("读取设备ip--------");
		String chipId = "";
		try {
			chipId = api.getIp(0);
			successSend("/user/readEquipment/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
		return chipId;
	}

	@MessageMapping("/sensorSearch")
	public void sensorSearch(User user) {
		System.out.println("查找所有传感器--------");
		String sensorList = "";
		try {
			Map<String, String> map = new HashMap<String, String>();
			long sensor1 = api.getSensorStatus(0, 1);
			map.put("1", getSensor(sensor1));
			long sensor2 = api.getSensorStatus(0, 2);
			map.put("2", getSensor(sensor2));
			long sensor3 = api.getSensorStatus(0, 3);
			map.put("3", getSensor(sensor3));
			long sensor4 = api.getSensorStatus(0, 4);
			map.put("4", getSensor(sensor4));
			long sensor5 = api.getSensorStatus(0, 5);
			map.put("5", getSensor(sensor5));
			long sensor6 = api.getSensorStatus(0, 6);
			map.put("6", getSensor(sensor6));
			long sensor7 = api.getSensorStatus(0, 7);
			map.put("7", getSensor(sensor7));
			long sensor8 = api.getSensorStatus(0, 8);
			map.put("8", getSensor(sensor8));
			long sensor9 = api.getSensorStatus(0, 9);
			map.put("9", getSensor(sensor9));
			long sensor12 = api.getSensorStatus(0, 12);
			map.put("12", getSensor(sensor12));
			long sensor13 = api.getSensorStatus(0, 13);
			map.put("13", getSensor(sensor13));
			long sensor14 = api.getSensorStatus(0, 14);
			map.put("14", getSensor(sensor14));
			long sensor15 = api.getSensorStatus(0, 15);
			map.put("15", getSensor(sensor15));
			long sensor16 = api.getSensorStatus(0, 16);
			map.put("16", getSensor(sensor16));
			long sensor17 = api.getSensorStatus(0, 17);
			map.put("17", getSensor(sensor17));
			long sensor18 = api.getSensorStatus(0, 18);
			map.put("18", getSensor(sensor18));
			long sensor19 = api.getSensorStatus(0, 19);
			map.put("19", getSensor(sensor19));
			long sensor21 = api.getSensorStatus(0, 21);
			map.put("21", getSensor(sensor21));
			long sensor22 = api.getSensorStatus(0, 22);
			map.put("22", getSensor(sensor22));
			long sensor23 = api.getSensorStatus(0, 23);
			map.put("23", getSensor(sensor23));
			sensorList = JSONObject.fromObject(map).toString();
			successSend("/user/sensorSearch/alone/getResponse", sensorList, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/reset/{motorId}")
	public void reset(@DestinationVariable("motorId") String motorId, User user) {
		System.out.println("打开复位--------");
		String chipId = "";
		try {
			int motor = Integer.valueOf(motorId);
			if ("5".equals(motorId)) {
				api.allReset();
				chipId = "整机复位成功";
			} else {
				api.motorReset(0, motor);
				if ("0".equals(motorId)) {
					chipId = "急诊电机";
				} else if ("1".equals(motorId)) {
					chipId = "转移电机";
				} else if ("2".equals(motorId)) {
					chipId = "回收电机";
				} else if ("3".equals(motorId)) {
					chipId = "进样电机";
				} else if ("6".equals(motorId)) {
					chipId = "递进电机";
				} else if ("7".equals(motorId)) {
					chipId = "阻挡电机";
				} else if ("8".equals(motorId)) {
					chipId = "换轨电机";
				}
				chipId += "复位成功";
			}
			successSend("/user/reset/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/basket")
	public void basket(User user) {
		System.out.println("提篮是否在线--------");
		String chipId = "";
		try {
			if (api.getSensorStatus(0, 1) != 1) {
				chipId += "进样提篮提手不在线;";
			} else if (api.getSensorStatus(0, 2) != 1) {
				chipId += "进样提篮篮体不在线;";
			} else if (api.getSensorStatus(0, 6) != 1) {
				chipId += "回收提篮篮体不在线;";
			} else if (api.getSensorStatus(0, 7) != 1) {
				chipId += "回收提篮篮体不在线;";
			} else {
				chipId = "进样、回收提篮在线";
			}
			successSend("/user/basket/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/chip")
	public void getChip(User user) {
		System.out.println("查询芯片ID--------");
		String chipId = "";
		try {
			chipId = api.getFirVersion(0, 1);
			successSend("/user/chip/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/openTftp/{openvalue}")
	public void openTftp(@DestinationVariable("openvalue") String openvalue, User user) {
		System.out.println("打开Tftp服务--------");
		String chipId = "";
		try {
			if ("1".equals(openvalue)) {
				api.setSwFunc(0, 0x5000, 1, 0, 0);
				chipId = "开启成功";
			} else {
				api.setSwFunc(0, 0x5000, 0, 0, 0);
				chipId = "关闭成功";
			}
			successSend("/user/openTftp/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/openRemote")
	public void openRemote(User user) {
		System.out.println("开启远程服务--------");
		String chipId = "";
		try {
			api.setSwFunc(0, 0x5003, 1, 0, 0);
			chipId = "开启远程服务成功";
			successSend("/user/openRemote/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/transferMove/{transId}")
	public void transferMove(@DestinationVariable("transId") String transId, User user) {
		System.out.println("区域转移--------");
		String chipId = "";
		try {
			if ("0".equals(transId)) {
				api.iAddInject();
			} else if ("1".equals(transId)) {
				api.iMoveToCache();
			} else if ("2".equals(transId)) {
				api.iemergInject();
			} else if ("3".equals(transId)) {
				List<String> strings = api.iScanBarcode();
			} else if ("4".equals(transId)) {
				api.injectMove();
			} else if ("5".equals(transId)) {
				api.imoveToTrack();
			} else if ("6".equals(transId)) {
				api.iTrackMove();
			} else if ("7".equals(transId)) {
				api.iProgInit();
			} else if ("8".equals(transId)) {
				api.iProOne();
			} else if ("9".equals(transId)) {
				api.iProToBack();
			} else if ("10".equals(transId)) {
				api.iBackTobasket();
			}
			chipId = "区域转移成功";
			successSend("/user/transferMove/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/openBoot")
	public void openBoot(User user) {
		System.out.println("开启固件更新--------");
		String chipId = "";
		try {
			api.openBootLoader(0);
			chipId = "开启固件更新成功";
			successSend("/user/openBoot/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/updateBoot/{fileName}/{ip}")
	public void updateBoot(@DestinationVariable("fileName") String fileName, @DestinationVariable("ip") String ip,
			User user) {
		System.out.println("固件更新啦--------");
		String chipId = "";
		try {
			String command = System.getProperty("user.dir") + "\\tftp.exe -i -v " + ip + " PUT "
					+ System.getProperty("user.dir") + "\\qcit\\" + fileName;
			System.out.println("command=" + command);
			boolean cmdflag = CmdExecUtil.runExec(command);
			if (cmdflag == true) {
				chipId = "固件更新成功";
			} else {
				chipId = "固件更新失败";
			}
			successSend("/user/updateBoot/alone/getResponse", chipId, user);

		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/firupload/{imgName}/{allnum}/{loadnum}")
	@SendTo("/user/firupload/alone/getResponse")
	public void firupload(@DestinationVariable("imgName") String imgName, @DestinationVariable("allnum") String allnum,
			@DestinationVariable("loadnum") String loadnum, @RequestBody String filestr, User user) {
		System.out.println(sendnum + ":固件上传--------");
		sendnum++;
		String chipId = "";
		File files = null;
		String saveFile = System.getProperty("user.dir") + "\\qcit\\" + imgName;// 储存的地址
		files = new File(saveFile);
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			if (loadnum.equals("1")) {
				if (!files.exists()) {
					System.out.println("文件不存在!");
					files.createNewFile();
				} else {
					files.delete();
					files.createNewFile();
				}
			} else if (allnum.equals(loadnum)) {
				System.out.println("写文件结束");
				sendnum = 0;
				chipId = loadnum + "_上传结束";
				successSend("/user/firupload/alone/getResponse", chipId, user);
			}
			fw = new FileWriter(files, true);
			bw = new BufferedWriter(fw);
			bw.write(filestr);
			bw.flush();// 注意必须刷新缓冲区 否则无法正确写入文件
			// chipId=loadnum+"_上传结束";
		} catch (IOException e) {
			errorSend(e, user);
		} finally {
			if (bw != null) {
				try {
					bw.close();
					fw.close();
				} catch (IOException e1) {
					errorSend(e1, user);
				}
			}
		}
	}
     
	@MessageMapping("/deleteCoord/{coodPoint}")
	public void deleteCoord(@DestinationVariable("coodPoint") String coodPoint, User user) {
		System.out.println("删除设备定标--------");
		String chipId = "";
		try {
			api.deleteParameter(0, coodPoint);
			chipId = "删除设备定标成功";
			successSend("/user/deleteCoord/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	
	@MessageMapping("/loadCoord/{opt}")
	//@SendTo("/user/loadCoord/alone/getResponse")
	public void loadCoord(@DestinationVariable("opt") String opt, User user) {
		System.out.println("设备定标--------");
		String chipId = "";
		try {
		    String firvserion=api.getFirVersion(0, 1);
		    String firname="qcit_location_"+firvserion;
			Map<Integer, List<ElementNode>> calibList = api.getMotorSpeed(firname);
			long plength = 0;
			Set<Entry<Integer, List<ElementNode>>> entryset = calibList.entrySet();
			for (Entry<Integer, List<ElementNode>> entry : entryset) {
				if (entry.getKey() == 0) {
					List<ElementNode> elementNode = entry.getValue();
					for (ElementNode elem : entry.getValue()) {
						String elemvalue = elem.getRootElement() + "." + elem.getNode() + "." + elem.getAttrnode();
						if ("0".equals(opt)) {// 下载到本地
							long ss = api.getParameter(0, elemvalue);
							api.setProperty(0, firname, elemvalue, ss + "");
							chipId = "下载本地参数到设备成功";
						} else if ("1".equals(opt)) {// 上传到设备
							api.setParameter(0, elemvalue, Long.valueOf(elem.getValue()));
							chipId = "上传本地参数到设备成功";
						}
					}
				}
			}
			if ("0".equals(opt)) {// 下载到本地
				api.getMotorSpeed(firname);
				chipId = "下载设备参数到本地成功";
			} else if ("1".equals(opt)) {// 上传到设备
				chipId = "上传本地参数到设备成功";
			}
			successSend("/user/loadCoord/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	@MessageMapping("/readCoord/{opt}/{motorId}/{coodPoint}")
	public void readCoord(@DestinationVariable("opt") String opt, @DestinationVariable("motorId") String motorId,
			@DestinationVariable("coodPoint") String coodPoint, User user) {
		System.out.println("读取定标--------");
		String chipId = "";
		try {
			long cood = 0;
			int motor = Integer.valueOf(motorId);
			if ("0".equals(opt)) {// 读取参数
				cood = api.getProperty(0, "qcit_location", coodPoint);
				if (cood < -2000000000)
					cood = 0;
			} else if ("1".equals(opt)) {// 读取设备参数
				cood = api.getParameter(0, coodPoint);
				if (cood < -2000000000)
					cood = 0;
			} else if ("2".equals(opt)) {// 读取坐标
				cood = api.getProperty(0, "qcit_location", coodPoint);
				if (cood < -2000000000)
					cood = 0;
				api.motorMove(0, motor, new Coordinate(cood));
			} else if ("3".equals(opt)) {// 读取设备参数坐标
				cood = api.getParameter(0, coodPoint);
				if (cood < -2000000000)
					cood = 0;
				api.motorMove(0, motor, new Coordinate(cood));
			}
			chipId = cood + "";
			successSend("/user/readCoord/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	@MessageMapping("/writeCoord/{opt}/{motorId}/{coodPoint}/{coord}")
	public void writeCoord(@DestinationVariable("opt") String opt, @DestinationVariable("motorId") String motorId,
			@DestinationVariable("coodPoint") String coodPoint, @DestinationVariable("coord") String coord,
			User user) {
		System.out.println("保存定标--------");
		String chipId = "";
		try {
			int motor = Integer.valueOf(motorId);
			if ("0".equals(opt)) {// 保存坐标参数到本地
				api.setProperty(0, "qcit_location", coodPoint, coord);
			} else if ("1".equals(opt)) {// 保存设备参数
				api.setParameter(0, coodPoint, Long.valueOf(coord));
				api.setProperty(0, "qcit_location", coodPoint, coord);
			} else if ("2".equals(opt)) {// 保存坐标到本地
				api.setProperty(0, "qcit_location", coodPoint, Axis.fromId(motor));
			} else if ("3".equals(opt)) {// 保存坐标到设备
				long len = api.getCurrentCoordinate(0, motor).getCoordinate();
				api.setParameter(0, coodPoint, len);
				api.setProperty(0, "qcit_location", coodPoint, Axis.fromId(motor));
			}
			chipId = "保存成功";
			successSend("/user/writeCoord/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	
	@MessageMapping("/motorOpt/{motorId}/{coord}/{opt}")
	public void motorOpt(@DestinationVariable("motorId") String motorId, @DestinationVariable("coord") String coord,
			@DestinationVariable("opt") String opt, User user) {
		System.out.println("电机控制--------");
		String chipId = "";
		try {
			int motor = Integer.valueOf(motorId);
			if ("0".equals(opt)) {
				api.motorStep(0, motor, new Coordinate(Long.valueOf(coord)).inverse());
				chipId = "回零位成功";
			} else if ("1".equals(opt)) {
				api.motorStep(0, motor, new Coordinate(Long.valueOf(coord)));
				chipId = "出零位出成功";
			} else if ("2".equals(opt)) {
				api.motorMove(0, motor, new Coordinate(Long.valueOf(coord)));
				chipId = "GO成功";
			} else if ("3".equals(opt)) {
				api.motorSeekZero(0, motor);
				chipId = "SeekZero成功";
			} else if ("4".equals(opt)) {
				api.motorReset(0, motor);
				chipId = "Reset成功";
			}
			successSend("/user/motorOpt/alone/getResponse", chipId, user);

		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/searchMotor/{motorId}")
	public void writeCoord(@DestinationVariable("motorId") List motorId, User user) {
		System.out.println("获取电机信息--------");
		String chipId = "";
		try {
			Map<String, String> map1 = new HashMap<String, String>();
			String kpString = "";
			for (int i = 0; i < motorId.size(); i++) {
				int ch = Integer.valueOf(motorId.get(i) + "");
				Map<String, String> map = new HashMap<String, String>();
				MotorLostStep lostStep = api.getMotorLostStep(0, ch);
				// 查询零位传感器
				long zeroSenor = api.getSensorStatus(0, ch + 49);
				// 查询防撞传感器
				// long falgEmmr=api.getEmmrSensor(0, ch);
				// 查询零位坐标
				long zenorLen = api.getMotorCoordinateZero(0, ch).getCoordinate();
				// 查询当前坐标
				long coordinate = api.getCurrentCoordinate(0, ch).getCoordinate();
				map.put("forward", lostStep.getForward().getCoordinate() + "");
				map.put("reverse", lostStep.getReverse().getCoordinate() + "");
				map.put("zeroFlag", getserFlag(zeroSenor) + "");
				map.put("zero", zenorLen + "");
				map.put("coord", coordinate + "");
				kpString = JSONObject.fromObject(map).toString();
				map1.put(ch + "", kpString);
			}
			chipId = JSONObject.fromObject(map1).toString();
			successSend("/user/searchMotor/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	// @MessageMapping("/sensorSearch")
	// @SendTo("/user/sensorSearch/alone/getResponse")
	// public String sensorSearch(Principal principal) {
	// System.out.println("查找所有传感器--------");
	// String sensorList="";
	// try {
	// Map<String,String> map=new HashMap<String,String>();
	// long sensor1=api.getSensorStatus(0, 1);
	// map.put("1", getSensor(sensor1));
	// long sensor2=api.getSensorStatus(0, 2);
	// map.put("2", getSensor(sensor2));
	// long sensor3=api.getSensorStatus(0, 3);
	// map.put("3", getSensor(sensor3));
	// long sensor4=api.getSensorStatus(0, 4);
	// map.put("4", getSensor(sensor4));
	// long sensor5=api.getSensorStatus(0, 5);
	// map.put("5", getSensor(sensor5));
	// long sensor6=api.getSensorStatus(0, 6);
	// map.put("6", getSensor(sensor6));
	// long sensor7=api.getSensorStatus(0, 7);
	// map.put("7", getSensor(sensor7));
	// long sensor8=api.getSensorStatus(0, 8);
	// map.put("8", getSensor(sensor8));
	// long sensor9=api.getSensorStatus(0, 9);
	// map.put("9", getSensor(sensor9));
	// long sensor12=api.getSensorStatus(0, 12);
	// map.put("12", getSensor(sensor12));
	// long sensor13=api.getSensorStatus(0, 13);
	// map.put("13", getSensor(sensor13));
	// long sensor14=api.getSensorStatus(0, 14);
	// map.put("14", getSensor(sensor14));
	// long sensor15=api.getSensorStatus(0, 15);
	// map.put("15", getSensor(sensor15));
	// long sensor16=api.getSensorStatus(0, 16);
	// map.put("16", getSensor(sensor16));
	// long sensor17=api.getSensorStatus(0, 17);
	// map.put("17", getSensor(sensor17));
	// long sensor18=api.getSensorStatus(0, 18);
	// map.put("18", getSensor(sensor18));
	// long sensor19=api.getSensorStatus(0, 19);
	// map.put("19", getSensor(sensor19));
	// long sensor21=api.getSensorStatus(0, 21);
	// map.put("21", getSensor(sensor21));
	// long sensor22=api.getSensorStatus(0, 22);
	// map.put("22", getSensor(sensor22));
	// long sensor23=api.getSensorStatus(0, 23);
	// map.put("23", getSensor(sensor23));
	//// long sensor49=api.getSensorStatus(0, 49+0);
	//// long sensor50=api.getSensorStatus(0, 49+1);
	//// long sensor51=api.getSensorStatus(0, 49+2);
	//// long sensor52=api.getSensorStatus(0, 49+3);
	//// long sensor53=api.getSensorStatus(0, 49+4);
	//// long sensor55=api.getSensorStatus(0, 49+6);
	//// long sensor56=api.getSensorStatus(0, 49+7);
	//// long sensor57=api.getSensorStatus(0, 49+8);
	//// long sensor58=api.getSensorStatus(0, 49+9);
	// sensorList=JSONObject.fromObject(map).toString();
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return sensorList;
	// }
	public String getSensor(long sensor) {
		String sen = "";
		if (sensor == 1) {
			sen = "触发";
		} else if (sensor == 0) {
			sen = "未触发";
		}
		return sen;
	}

	// @MessageMapping("/chip")
	// @SendTo("/user/chip/alone/getResponse")
	// public String getChip(Principal principal) {
	// System.out.println("查询芯片ID--------");
	// String chipId="";
	// try {
	// chipId=api.getFirVersion(0, 1);
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	// @MessageMapping("/readloacl")
	// @SendTo("/user/readloacl/alone/getResponse")
	// public String readloacl(Principal principal) {
	// System.out.println("读取本地ip--------");
	// String chipId="";
	// try {
	// chipId = api.getPropertys(0, "small_qcit_location", "QCIT.MB.ipPort");
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	// @MessageMapping("/writeloacl/{ipPort}")
	// @SendTo("/user/writeloacl/alone/getResponse")
	// public String writeloacl(@DestinationVariable("ipPort") String
	// ipPort,Principal principal) {
	// System.out.println("写入本地ip--------");
	// String chipId="";
	// try {
	// api.setProperty(0, "small_qcit_location", "QCIT.MB.ipPort",ipPort);
	// chipId="Ip写入本地成功！";
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }

	// @MessageMapping("/openTftp/{openvalue}")
	// @SendTo("/user/openTftp/alone/getResponse")
	// public String openTftp(@DestinationVariable("openvalue") String
	// openvalue,Principal principal) {
	// System.out.println("打开Tftp服务--------");
	// String chipId="";
	// try {
	// if("1".equals(openvalue)){
	// api.setSwFunc(0, 0x5000, 1, 0, 0);
	// chipId="开启成功";
	// }else{
	// api.setSwFunc(0, 0x5000, 0, 0, 0);
	// chipId="关闭成功";
	// }
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	// @MessageMapping("/reset/{motorId}")
	// @SendTo("/user/reset/alone/getResponse")
	// public String reset(@DestinationVariable("motorId") String
	// motorId,Principal principal) {
	// System.out.println("打开复位--------");
	// String chipId="";
	// try {
	// int motor=Integer.valueOf(motorId);
	// if("5".equals(motorId)){
	// api.allReset();
	// chipId="整机";
	// }else{
	// api.motorReset(0, motor);
	// if("0".equals(motorId)){
	// chipId="急诊电机";
	// }else if("1".equals(motorId)){
	// chipId="转移电机";
	// }else if("2".equals(motorId)){
	// chipId="回收电机";
	// }else if("3".equals(motorId)){
	// chipId="进样电机";
	// }else if("6".equals(motorId)){
	// chipId="递进电机";
	// }else if("7".equals(motorId)){
	// chipId="阻挡电机";
	// }else if("8".equals(motorId)){
	// chipId="换轨电机";
	// }
	// chipId+="复位成功";
	// }
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }

//	@MessageMapping("/readCoord/{opt}/{motorId}/{coodPoint}")
//	@SendTo("/user/readCoord/alone/getResponse")
//	public String readCoord(@DestinationVariable("opt") String opt, @DestinationVariable("motorId") String motorId,
//			@DestinationVariable("coodPoint") String coodPoint, Principal principal) {
//		System.out.println("读取定标--------");
//		String chipId = "";
//		try {
//			long cood = 0;
//			int motor = Integer.valueOf(motorId);
//			if ("0".equals(opt)) {// 读取参数
//				cood = api.getProperty(0, "qcit_location", coodPoint);
//				if (cood < -2000000000)
//					cood = 0;
//			} else if ("1".equals(opt)) {// 读取设备参数
//				cood = api.getParameter(0, coodPoint);
//				if (cood < -2000000000)
//					cood = 0;
//			} else if ("2".equals(opt)) {// 读取坐标
//				cood = api.getProperty(0, "qcit_location", coodPoint);
//				if (cood < -2000000000)
//					cood = 0;
//				api.motorMove(0, motor, new Coordinate(cood));
//			} else if ("3".equals(opt)) {// 读取设备参数坐标
//				cood = api.getParameter(0, coodPoint);
//				if (cood < -2000000000)
//					cood = 0;
//				api.motorMove(0, motor, new Coordinate(cood));
//			}
//			chipId = cood + "";
//		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String msg = sw.toString();
//			return msg;
//		}
//		return chipId;
//	}

//	@MessageMapping("/writeCoord/{opt}/{motorId}/{coodPoint}/{coord}")
//	@SendTo("/user/writeCoord/alone/getResponse")
//	public String writeCoord(@DestinationVariable("opt") String opt, @DestinationVariable("motorId") String motorId,
//			@DestinationVariable("coodPoint") String coodPoint, @DestinationVariable("coord") String coord,
//			Principal principal) {
//		System.out.println("保存定标--------");
//		String chipId = "";
//		try {
//			int motor = Integer.valueOf(motorId);
//			if ("0".equals(opt)) {// 保存坐标参数到本地
//				api.setProperty(0, "qcit_location", coodPoint, coord);
//			} else if ("1".equals(opt)) {// 保存设备参数
//				api.setParameter(0, coodPoint, Long.valueOf(coord));
//				api.setProperty(0, "qcit_location", coodPoint, coord);
//			} else if ("2".equals(opt)) {// 保存坐标到本地
//				long len = api.getCurrentCoordinate(0, motor).getCoordinate();
//				api.setProperty(0, "qcit_location", coodPoint, Axis.fromId(motor));
//			} else if ("3".equals(opt)) {// 保存坐标到设备
//				long len = api.getCurrentCoordinate(0, motor).getCoordinate();
//				api.setParameter(0, coodPoint, len);
//				api.setProperty(0, "qcit_location", coodPoint, Axis.fromId(motor));
//			}
//			chipId = "保存成功";
//		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String msg = sw.toString();
//			return msg;
//		}
//		return chipId;
//	}
    
//	@MessageMapping("/deleteCoord/{coodPoint}")
//	@SendTo("/user/deleteCoord/alone/getResponse")
//	public String deleteCoord(@DestinationVariable("coodPoint") String coodPoint, Principal principal) {
//		System.out.println("删除设备定标--------");
//		String chipId = "";
//		try {
//			api.deleteParameter(0, coodPoint);
//			chipId = "删除设备定标成功";
//		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String msg = sw.toString();
//			return msg;
//		}
//		return chipId;
//	}

	// @MessageMapping("/openRemote")
	// @SendTo("/user/openRemote/alone/getResponse")
	// public String openRemote(Principal principal) {
	// System.out.println("开启远程服务--------");
	// String chipId="";
	// try {
	// api.setSwFunc(0, 0x5003, 1, 0, 0);
	// chipId="开启远程服务成功";
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	// @MessageMapping("/transferMove/{transId}")
	// @SendTo("/user/transferMove/alone/getResponse")
	// public String transferMove(@DestinationVariable("transId") String
	// transId,Principal principal) {
	// System.out.println("区域转移--------");
	// String chipId="";
	// try {
	// if("0".equals(transId)){
	// api.iAddInject();
	// }else if("1".equals(transId)){
	// api.iMoveToCache();
	// }else if("2".equals(transId)){
	// api.iemergInject();
	// }else if("3".equals(transId)){
	// List<String> strings=api.iScanBarcode();
	// }else if("4".equals(transId)){
	// api.injectMove();
	// }else if("5".equals(transId)){
	// api.imoveToTrack();
	// }else if("6".equals(transId)){
	// api.iTrackMove();
	// }else if("7".equals(transId)){
	// api.iProgInit();
	// }else if("8".equals(transId)){
	// api.iProOne();
	// }else if("9".equals(transId)){
	// api.iProToBack();
	// }else if("10".equals(transId)){
	// api.iBackTobasket();
	// }
	// chipId="区域转移成功";
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	// @MessageMapping("/openBoot")
	// @SendTo("/user/openBoot/alone/getResponse")
	// public String openBoot(Principal principal) {
	// System.out.println("开启固件更新--------");
	// String chipId="";
	// try {
	// api.openBootLoader(0);
	// chipId="开启固件更新成功";
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	@MessageMapping("/firuploads")
	@SendTo("/user/firuploads/alone/getResponse")
	public String firupload(@RequestBody String filestr, Principal principal) {
		System.out.println(sendnum + ":固件上传--------");
		return filestr;
	}

	private int sendnum = 0;
	// @MessageMapping("/firupload/{imgName}/{allnum}/{loadnum}")
	// @SendTo("/user/firupload/alone/getResponse")
	// public String firupload(@DestinationVariable("imgName") String
	// imgName,@DestinationVariable("allnum") String
	// allnum,@DestinationVariable("loadnum") String loadnum,@RequestBody String
	// filestr,Principal principal) {
	// System.out.println(sendnum+":固件上传--------");
	// sendnum++;
	// String chipId="";
	// File files=null;
	// String saveFile=System.getProperty("user.dir")+"\\qcit\\"+imgName;//储存的地址
	// files=new File(saveFile);
	// BufferedWriter bw=null;
	// FileWriter fw=null;
	// try {
	// if(loadnum.equals("1")){
	// if(!files.exists()){
	// System.out.println("文件不存在!");
	// files.createNewFile();
	// }else{
	// files.delete();
	// files.createNewFile();
	// }
	// }else if(allnum.equals(loadnum)){
	// System.out.println("写文件结束");
	// sendnum=0;
	// }
	// fw=new FileWriter(files,true);
	// bw=new BufferedWriter(fw);
	// bw.write(filestr);
	// bw.flush();//注意必须刷新缓冲区 否则无法正确写入文件
	// chipId=loadnum+"_上传结束";
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally {
	// if (bw != null) {
	// try {
	// bw.close();
	// fw.close();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	// }
	// }
	// return chipId;
	// }
	// @MessageMapping("/updateBoot/{fileName}/{ip}")
	// @SendTo("/user/updateBoot/alone/getResponse")
	// public String updateBoot(@DestinationVariable("fileName") String
	// fileName,@DestinationVariable("ip") String ip,Principal principal) {
	// System.out.println("固件更新啦--------");
	// String chipId="";
	// try {
	// String command=System.getProperty("user.dir")+"\\tftp.exe -i -v "+ip+"
	// PUT "+System.getProperty("user.dir")+"\\qcit\\"+fileName;
	// System.out.println("command="+command);
	// boolean cmdflag=CmdExecUtil.runExec(command);
	// if(cmdflag == true){
	// chipId="固件更新成功";
	// }else{
	// chipId="固件更新失败";
	// }
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }

//	@MessageMapping("/loadCoord/{opt}")
//	@SendTo("/user/loadCoord/alone/getResponse")
//	public String loadCoord(@DestinationVariable("opt") String opt, Principal principal) {
//		System.out.println("删除设备定标--------");
//		String chipId = "";
//		try {
//			Map<Integer, List<ElementNode>> calibList = api.getMotorSpeed();
//			long plength = 0;
//			Set<Entry<Integer, List<ElementNode>>> entryset = calibList.entrySet();
//			// System.out.println("map 中全部的键值对");
//			for (Entry<Integer, List<ElementNode>> entry : entryset) {
//				if (entry.getKey() == 0) {
//					List<ElementNode> elementNode = entry.getValue();
//					for (ElementNode elem : entry.getValue()) {
//						String elemvalue = elem.getRootElement() + "." + elem.getNode() + "." + elem.getAttrnode();
//						if ("0".equals(opt)) {// 下载到本地
//							long ss = api.getParameter(0, elemvalue);
//							api.setProperty(0, "qcit_location", elemvalue, ss + "");
//
//						} else if ("1".equals(opt)) {// 上传到设备
//							// long value = api.getProperty(0, "qcit_location",
//							// elemvalue);//获取定标值
//							api.setParameter(0, elemvalue, Long.valueOf(elem.getValue()));
//							chipId = "上传本地参数到设备成功";
//						}
//					}
//				}
//				// System.out.println(entry.getKey() + " " + entry.getValue());
//			}
//			if ("0".equals(opt)) {// 下载到本地
//				api.getMotorSpeed();
//				chipId = "下载设备参数到本地成功";
//			} else if ("1".equals(opt)) {// 上传到设备
//				chipId = "上传本地参数到设备成功";
//			}
//
//		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String msg = sw.toString();
//			return msg;
//		}
//		return chipId;
//	}

//	@MessageMapping("/motorOpt/{motorId}/{coord}/{opt}")
//	@SendTo("/user/motorOpt/alone/getResponse")
//	public String openTftp(@DestinationVariable("motorId") String motorId, @DestinationVariable("coord") String coord,
//			@DestinationVariable("opt") String opt, Principal principal) {
//		System.out.println("电机控制--------");
//		String chipId = "";
//		try {
//			int motor = Integer.valueOf(motorId);
//			if ("0".equals(opt)) {
//				api.motorStep(0, motor, new Coordinate(Long.valueOf(coord)).inverse());
//				chipId = "回零位成功";
//			} else if ("1".equals(opt)) {
//				api.motorStep(0, motor, new Coordinate(Long.valueOf(coord)));
//				chipId = "出零位出成功";
//			} else if ("2".equals(opt)) {
//				api.motorMove(0, motor, new Coordinate(Long.valueOf(coord)));
//				chipId = "GO成功";
//			} else if ("3".equals(opt)) {
//				api.motorSeekZero(0, motor);
//				chipId = "SeekZero成功";
//			} else if ("4".equals(opt)) {
//				api.motorReset(0, motor);
//				chipId = "Reset成功";
//			}
//		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String msg = sw.toString();
//			return msg;
//		}
//		return chipId;
//	}
//
//	@MessageMapping("/searchMotor/{motorId}")
//	@SendTo("/user/searchMotor/alone/getResponse")
//	public String writeCoord(@DestinationVariable("motorId") List motorId, Principal principal) {
//		System.out.println("获取电机信息--------");
//		String chipId = "";
//		try {
//			Map<String, String> map1 = new HashMap<String, String>();
//			String kpString = "";
//			for (int i = 0; i < motorId.size(); i++) {
//				int ch = Integer.valueOf(motorId.get(i) + "");
//				Map<String, String> map = new HashMap<String, String>();
//				MotorLostStep lostStep = api.getMotorLostStep(0, ch);
//				// 查询零位传感器
//				long zeroSenor = api.getSensorStatus(0, ch + 49);
//				// 查询防撞传感器
//				// long falgEmmr=api.getEmmrSensor(0, ch);
//				// 查询零位坐标
//				long zenorLen = api.getMotorCoordinateZero(0, ch).getCoordinate();
//				// 查询当前坐标
//				long coordinate = api.getCurrentCoordinate(0, ch).getCoordinate();
//				map.put("forward", lostStep.getForward().getCoordinate() + "");
//				map.put("reverse", lostStep.getReverse().getCoordinate() + "");
//				map.put("zeroFlag", getserFlag(zeroSenor) + "");
//				map.put("zero", zenorLen + "");
//				map.put("coord", coordinate + "");
//				kpString = JSONObject.fromObject(map).toString();
//				map1.put(ch + "", kpString);
//			}
//			chipId = JSONObject.fromObject(map1).toString();
//		} catch (Exception e) {
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String msg = sw.toString();
//			return msg;
//		}
//		return chipId;
//	}

	// @MessageMapping("/searchMotor/{motorId}")
	// @SendTo("/user/searchMotor/alone/getResponse")
	// public String writeCoord(@DestinationVariable("motorId") String
	// motorId,Principal principal) {
	// System.out.println("获取电机信息--------");
	// String chipId="";
	// try {
	// String kpString="";
	// int ch=Integer.valueOf(motorId);
	// Map<String,String> map=new HashMap<String,String>();
	// MotorLostStep lostStep= api.getMotorLostStep(0, ch);
	// //查询零位传感器
	// long zeroSenor=api.getSensorStatus(0, ch+49);
	// //查询防撞传感器
	// //long falgEmmr=api.getEmmrSensor(0, ch);
	// //查询零位坐标
	// long zenorLen=api.getMotorCoordinateZero(0, ch).getCoordinate();
	// //查询当前坐标
	// long coordinate=api.getCurrentCoordinate(0, ch).getCoordinate();
	// map.put("forward", lostStep.getForward().getCoordinate()+"");
	// map.put("reverse", lostStep.getReverse().getCoordinate()+"");
	// map.put("zeroFlag", getserFlag(zeroSenor)+"");
	// map.put("zero", zenorLen+"");
	// map.put("coord", coordinate+"");
	// chipId=JSONObject.fromObject(map).toString();
	// } catch (Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// String msg=sw.toString();
	// return msg;
	// }
	// return chipId;
	// }
	public boolean getserFlag(long value) {
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

}

@Data
class LoadMessage {
	private String fileName;
	private String ipStr;
}
