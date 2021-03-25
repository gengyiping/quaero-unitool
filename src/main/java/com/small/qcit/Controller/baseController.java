package com.small.qcit.Controller;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;
import com.quaero.base.ElementNode;
import com.quest.common.util.CmdExecUtil;
import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.module.Axis;
import com.quest.software.bus4j.module.ExecuteException;
import com.quest.software.bus4j.module.motor.Coordinate;
import com.quest.software.bus4j.module.motor.MotorLostStep;
import com.small.qcit.domain.dto.User;
import com.small.qcit.service.MessageService;
import com.small.qcit.service.UploadService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@RestController
@Slf4j
public class baseController extends optContorller {
	@Resource
	private MessageService messageService;
	 @Resource
	private UploadService uploadService;

	public void successSend(String url, String message) {

	}

	@MessageMapping("/readloacl")
	// @SendTo("/user/readloacl/alone/getResponse")
	public void readloacl(User user) {
		
		System.out.println("读取本地ip--------");
		String chipId = "";
		try {
			chipId = api.getPropertys(0, "Ip_qcit_location", "QCIT.MB.ipPort");
			successSend("/user/"+user.getIp()+"/readloacl/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/writeloacl/{ipPort}")
	public void writeloacl(@DestinationVariable("ipPort") String ipPort, User user) {
		System.out.println("写入本地ip--------");
		String chipId = "";
		try {
			api.setProperty(0, "Ip_qcit_location", "QCIT.MB.ipPort", ipPort);
			chipId = "Ip写入本地成功！";
			successSend("/user/"+user.getIp()+"/writeloacl/alone/getResponse", chipId, user);
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
			try {
				api.createIp(0, ips[0]);
			} catch (JsonSyntaxException | CmdCodeException | ErrCodeException | InstantiationException
					| IllegalAccessException | ExecuteException e) {
				Exception e1=e;
				errorSend(e, user);
				StringWriter sw = new StringWriter();    
				PrintWriter pw = new PrintWriter(sw);    
				e1.printStackTrace(pw);
				if(e1.toString().contains("0x80030003")){
				api.setProperty(0, "Ip_qcit_location", "QCIT.MB.ipPort", ipPort);
				api.init();
				}
			}
			chipId = "Ip写入设备完成！";
			successSend("/user/"+user.getIp()+"/writeEquipment/alone/getResponse", chipId, user);
		} catch (Exception e2) {
			errorSend(e2, user);
		}
	}

	@MessageMapping("/readEquipment")
	public void readEquipment(User user) {
		System.out.println("读取设备ip--------");
		String chipId = "";
		try {
			chipId = api.getIp(0);
			successSend("/user/"+user.getIp()+"/readEquipment/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
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
			long sensor24 = api.getSensorStatus(0, 24);
			map.put("24", getSensor(sensor24));
			sensorList = JSONObject.fromObject(map).toString();
			successSend("/user/"+user.getIp()+"/sensorSearch/alone/getResponse", sensorList, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	@MessageMapping("/openscan/{sacanval}")
	public void openscan(@DestinationVariable("sacanval") String sacanval,User user) {
		System.out.println("---条码仪操作-----");
		String chipId = "";
		try {
			if("0".equals(sacanval)) {
				api.barcodeCtrl(0, 0, 3);
				chipId="开启成功";
			}
			if("1".equals(sacanval)) {
				api.barcodeCtrl(0, 0, 1);
				chipId="关闭成功";
			}
			successSend("/user/"+user.getIp()+"/openscan/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/setbarCode/{barcode}")
	public String setBarCode(User user,@DestinationVariable("barcode") String barcode) {
		System.out.println("设置条码仪参数--------");
		String chipId = "";
		try {
			String val=barcode+"\r\n";
			api.setScannerParam(0, 0, val, 0xffff);
			chipId="设置条码仪参数成功";
			successSend("/user/"+user.getIp()+"/setbarCode/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
		return chipId;
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
			successSend("/user/"+user.getIp()+"/reset/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/basket/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/chip/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/openTftp/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/openRemote/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/transferMove/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	
	@MessageMapping("/ledcontrl/{ledId}/{heightlight}/{lowlight}")
	public void ledcontrl(@DestinationVariable("ledId") String ledId,@DestinationVariable("heightlight") String heightlight,@DestinationVariable("lowlight") String lowlight,User user) {
		System.out.println("led控制--------");
		String chipId = "";
		try {
			long led=api.ctrlLed(0, Integer.valueOf(ledId), Integer.valueOf(heightlight), Integer.valueOf(lowlight));
			chipId = "控制led成功";
			successSend("/user/"+user.getIp()+"/ledcontrl/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/openBoot/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}

	@MessageMapping("/updateBoot/{fileName}/{ip}")
	public void updateBoot(@DestinationVariable("fileName") String fileName, @DestinationVariable("ip") String ip,
			User user) throws Exception {
		System.out.println("固件更新啦--------");
		String chipId = "";
		String kfir=uploadService.getFirPath();
		if(kfir==null) {
			successSend("/user/"+user.getIp()+"/updateBoot/alone/getResponse", "无上传固件", user);
			return;
		}
		try {
			api.openBootLoader(0);
			String command = System.getProperty("user.dir") + "\\tftp.exe -i -v " + ip + " PUT "
					+kfir;
			System.out.println("command=" + command);
			Thread.sleep(5000);
			boolean cmdflag = CmdExecUtil.runExec(command);
			if (cmdflag == true) {
				chipId = "固件更新成功";
				File dil=new File(kfir);
				dil.delete();
			} else {
				chipId = "固件更新失败";
			}
			successSend("/user/"+user.getIp()+"/updateBoot/alone/getResponse", chipId, user);

		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	@MessageMapping("/initialUpdate/{fileName}/{ip}")
	public void initialUpdate(@DestinationVariable("fileName") String fileName, @DestinationVariable("ip") String ip,
			User user) throws Exception {
		System.out.println("首次固件更新啦--------");
		String chipId = "";
		String kfir=uploadService.getFirPath();
		if(kfir==null) {
			successSend("/user/"+user.getIp()+"/initialUpdate/alone/getResponse", "无上传固件", user);
			return;
		}
		try {
			//api.openBootLoader(0);
			String command = System.getProperty("user.dir") + "\\tftp.exe -i -v " + ip + " PUT "
					+kfir;
			System.out.println("command=" + command);
			//Thread.sleep(5000);
			boolean cmdflag = CmdExecUtil.runExec(command);
			if (cmdflag == true) {
				chipId = "固件更新成功";
				File dil=new File(kfir);
				dil.delete();
			} else {
				chipId = "固件更新失败";
			}
			successSend("/user/"+user.getIp()+"/initialUpdate/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	
	private String filepath="";
	@MessageMapping("/firuploadpath")
	public void firuploadpath( @RequestBody Filepath path, User user) {
		System.out.println("固件更新啦--------");
		String chipId = "";
		try {
			filepath=path.getUrl();
			System.out.println("filepath="+filepath);
			successSend("/user/"+user.getIp()+"/updateBoot/alone/getResponse", chipId, user);

		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	
    private Map<String, String> filemap;
    private List<String> filenum=new ArrayList<String>();
    private int filesize;
	@MessageMapping("/firupload/{imgName}/{allnum}/{loadnum}")
	public void firupload(@DestinationVariable("imgName") String imgName, @DestinationVariable("allnum") String allnum,
			@DestinationVariable("loadnum") String loadnum, @RequestBody String filestr, User user) throws SerialException, SQLException {
		//System.out.println(loadnum + ":固件上传--------"+allnum);
		String chipId = "";
		File files = null;
		//String imgName1=imgName.replace(".bin", ".text");
		String saveFile = System.getProperty("user.dir") + "\\qcit\\" + imgName;// 储存的地址
		files = new File(saveFile);
		try {
			if(!filenum.contains(loadnum)){
				if (loadnum.equals("1")) {
					filenum.clear();
					filemap=new HashMap<>();
					filesize=0;
					filenum.add(loadnum);
					//sendnum=Integer.valueOf(loadnum);
					if (!files.exists()) {
						System.out.println("文件不存在!");
					} else {
						files.delete();
					}
					files.createNewFile();
					filesize+=filestr.length();
					System.out.println("第一次传输的文件内容："+filestr);
					
					filemap.put(loadnum, filestr);
//					fw = new FileWriter(files, true);
//					bw = new BufferedWriter(fw);
//					bw.write(filestr);
//					bw.flush();// 注意必须刷新缓冲区 否则无法正确写入文件
					chipId = loadnum + "_上传进行中";
					//successSend("/user/"+user.getIp()+"/firupload/alone/getResponse", chipId, user);
					if((filenum.size()+"").equals(allnum)){
						writeBin(files,saveFile, user);
					}
				} else if (allnum.equals(loadnum)&&!loadnum.equals("1")) {
					//sendnum=Integer.valueOf(loadnum);
					filenum.add(loadnum);
					filemap.put(loadnum, filestr);
					filesize+=filestr.length();
					//System.out.println("文件内容大小"+filestr.length());
					//System.out.println("写文件结束"+filenum.size());
					if((filenum.size()+"").equals(allnum)){
						writeBin(files,saveFile, user);
					}
//					fw = new FileWriter(files, true);
//					bw = new BufferedWriter(fw);
//					fos=new FileOutputStream(saveFile);//传入文件所在路径
//					byte output[]=olStr.getBytes();
//					fos.write(output);
//			        osw=new OutputStreamWriter(fos,"UTF-8"); //将fos作为参数传入osw，并且设置字符编码
//			        bw = new BufferedWriter(osw);
//					bw.write(toBinary(olStr));
//					bw.flush();// 注意必须刷新缓冲区 否则无法正确写入文件
					
					
					chipId = loadnum + "_上传结束";
					successSend("/user/"+user.getIp()+"/firupload/alone/getResponse", chipId, user);
				}
//				else if((sendnum+"").equals(loadnum)){
//				}
				else{
					filenum.add(loadnum);
					filesize+=filestr.length();
					//System.out.println("文件内容大小"+filestr.length());
					//sendnum=Integer.valueOf(loadnum);
					filemap.put(loadnum, filestr);
					chipId = loadnum + "_上传进行中";
					//successSend("/user/"+user.getIp()+"/firupload/alone/getResponse", chipId, user);
					if((filenum.size()+"").equals(allnum)){
						writeBin(files,saveFile, user);
					}
				}
				
				// chipId=loadnum+"_上传结束";
			}
		} catch (IOException e) {
			errorSend(e, user);
		} finally {
//			if (bw != null) {
//				try {
//					//fos.close();
//					bw.close();
//					fw.close();
//				} catch (IOException e1) {
//					errorSend(e1, user);
//				}
//			}
		}
	}
	public void writeBin(File files,String saveFile,User user) throws SerialException, SQLException{
		String olStr="";
		for(int i=1;i<=filenum.size();i++){
			for(Map.Entry<String, String> d:filemap.entrySet())
			 {
				if(d.getKey().equals(i+"")){
//					fw = new FileWriter(files, true);
//					bw = new BufferedWriter(fw);
					String strc=d.getValue();
					olStr+=strc;
					
					//String new_str = new String(str.getBytes("gbk"), "ANSI");
					//String final_str = new String(new_str.getBytes("gbk"), "utf-8");
//					bw.write(str);
//					bw.flush();// 注意必须刷新缓冲区 否则无法正确写入文件
				//	System.out.println("keynum="+d.getKey());
				}
			 }
		}
		//System.out.println("olStr="+olStr);
		System.out.println("大小="+olStr.length());
		BufferedWriter bw = null;
		FileWriter fw = null;
		FileOutputStream fos=null;   //节点类
		OutputStreamWriter osw=null;  //字符类
		try {
//			fos=new FileOutputStream(saveFile);//传入文件所在路径
//	        osw=new OutputStreamWriter(fos,"utf-8"); //将fos作为参数传入osw，并且设置字符编码
////		fw = new FileWriter(files, true);
////		fw.write(olStr);
//
//		bw=new BufferedWriter(osw);
//		bw.write(olStr);
//		bw.flush();
//		bw.close();
////			fw.close();
//		fos.close();
//		osw.close();
//		
//			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\qcit\\" + "qcit-221-release-V1741.bin"));
//			byte[] strbyte=olStr.getBytes();
//			out.write(strbyte);
			
			Blob b = new SerialBlob(olStr.getBytes());
			InputStream in=b.getBinaryStream();
	      OutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1781.bin");
	      //读取数据
	      //一次性取多少字节
	      byte[] bytes = new byte[1024];
	      //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
	      int n = -1;
	      //循环取出数据
	      while ((n = in.read(bytes,0,bytes.length)) != -1) {
	          //转换成字符串
	          String str = new String(bytes,0,n,"gb2312"); //这里可以实现字节到字符串的转换，比较实用
	          System.out.println(str);
	          //写入相关文件
	          out.write(bytes, 0, n);
	      }
	      //关闭流
	      in.close();
	      out.close();

			//			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(saveFile));//无内容写入
//			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(saveFile),"GBK");//有差异
//			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(saveFile),"utf-8");//无内容写入
//			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(saveFile),"Unicode");//差异较大
//			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(saveFile),"ANSI");//无内容写入
//		    //读取数据
//		    //一次性取多少字节
//		  String[] nostr= olStr.split("\n\r\n");
//		  for(int i=0;i<nostr.length;i++) {
//			byte[] bytes = new byte[nostr[i].length()];
//			//if(i<(nostr.length-1)) {
//			//	out.write(nostr[i]+"\n\r\n");
//			//}else {
//				out.write(nostr[i]);
//			//}
			
//		//String newLine = System.getProperty("line.separator");
//		//    out.write(newLine.getBytes());
//		   }
//	    out.close();
		//String cbString=StrToBinstr(olStr);
		
		//bw.write(olStr, 0, olStr.length());
		//bw.flush();// 注意必须刷新缓冲区 否则无法正确写入文件
	} catch (IOException e) {
		errorSend(e, user);
	} finally {
//		if (bw != null) {
//			try {
//				//fos.close();
//				bw.close();
//				//fw.close();
//			} catch (IOException e1) {
//				errorSend(e1, user);
//			}
//		}
	}
	}
//		@MessageMapping("/firuploadd/{imgName}/{allnum}/{loadnum}")
//		public void firuploadd(@DestinationVariable("imgName") String imgName, @DestinationVariable("allnum") String allnum,
//				@DestinationVariable("loadnum") String loadnum, @RequestBody InputStream filestr, User user) {
//			//System.out.println(loadnum + ":固件上传--------"+allnum);
//			String chipId = "";
//			File files = null;
//			//String imgName1=imgName.replace(".bin", ".text");
//			String saveFile = System.getProperty("user.dir") + "\\qcit\\" + imgName;// 储存的地址
//			files = new File(saveFile);
//			DataInputStream dis=new DataInputStream(filestr);
//			try {
//				if(!filenum.contains(loadnum)){
//					if (loadnum.equals("1")) {
//						filenum.clear();
//						filemap=new HashMap<>();
//						filesize=0;
//						filenum.add(loadnum);
//						//sendnum=Integer.valueOf(loadnum);
//						if (!files.exists()) {
//							System.out.println("文件不存在!");
//						} else {
//							files.delete();
//						}
//						files.createNewFile();
//						System.out.println("打印字符="+dis.readUTF());
//						System.out.println("打印长度="+dis.readLong());
//						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\qcit\\" + "qcit-221-release-V1742.bin"));
//						byte[] strbyte=new byte[5120];
//						int lenth=0;
//						while((lenth=dis.read(strbyte, 0, strbyte.length))!=-1) {
//							out.write(strbyte,0,lenth);
//						}
//						
//					} 
//					
//					// chipId=loadnum+"_上传结束";
//				}
//			} catch (IOException e) {
//				errorSend(e, user);
//			} finally {
//			}
//		}
	@MessageMapping("/firuploads/{imgName}")
	public void firuploads(@DestinationVariable("imgName") String imgName,@RequestBody String filestr , User user) throws IOException, SQLException {
//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\qcit\\" + "qcit-221-release-V1745.bin"));
//		out.write(filestr);
//		out.flush();
//		out.close();
		final Base64.Decoder decpder=Base64.getDecoder();
		String codestr=new String(decpder.decode(filestr.replace("data:application/octet-stream;base64,", "")));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\qcit\\" + "qcit-221-release-V1280.bin"));
		byte[] strbyte=codestr.getBytes();
		out.write(strbyte);
		out.flush();
		out.close();
		
//		Blob b = new SerialBlob(filestr.getBytes("gb2312"));
//		InputStream in=b.getBinaryStream();
//      OutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1780.bin");
//      //读取数据
//      //一次性取多少字节
//      byte[] bytes = new byte[1024];
//      //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
//      int n = -1;
//      //循环取出数据
//      while ((n = in.read(bytes,0,bytes.length)) != -1) {
//          //转换成字符串
//          String str = new String(bytes,0,n,"gb2312"); //这里可以实现字节到字符串的转换，比较实用
//          System.out.println(str);
//          //写入相关文件
//          out.write(bytes, 0, n);
//      }
//      //关闭流
//      in.close();
//      out.close();
      
      
//		byte[] buffer=new byte[1024];
//		int len=0;
//		while((len=in.read(buffer))!=-1){
//			fs.write(buffer, 0, len);
//			fs.flush();
//		}
//		fs.close();
		
		
//		OutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1778.bin");
//		out.write(filestr);
//		out.flush();
//		out.close();
	}
	public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }
	//将Unicode字符串转换成二进制字符串，以空格相隔
    private String StrToBinstr(String str) {
       char[] strChar=str.toCharArray();
       String result="";
       for(int i=0;i<strChar.length;i++){
           result +=Integer.toBinaryString(strChar[i])+ " ";
       }
       return result;
    }
	//把字符串转成二进制码
	public static String toBinary(String str){
        //把字符串转成字符数组
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            //toBinaryString(int i)返回变量的二进制表示的字符串
            //toHexString(int i) 八进制
            //toOctalString(int i) 十六进制
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }
	public static String toString(String binary) {
        String[] tempStr=binary.split(" ");
           char[] tempChar=new char[tempStr.length];
           for(int i=0;i<tempStr.length;i++) {
              tempChar[i]=BinstrToChar(tempStr[i]);
           }
           return String.valueOf(tempChar);
   }
	 //将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {       
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];   
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
	public static char BinstrToChar(String binStr){
        int[] temp=BinstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }   
        return (char)sum;
   }
	@MessageMapping("/deleteCoord/{coodPoint}")
	public void deleteCoord(@DestinationVariable("coodPoint") String coodPoint, User user) {
		System.out.println("删除设备定标--------");
		String chipId = "";
		try {
			api.deleteParameter(0, coodPoint);
			chipId = "删除设备定标成功";
			successSend("/user/"+user.getIp()+"/deleteCoord/alone/getResponse", chipId, user);
		} catch (Exception e) {
			errorSend(e, user);
		}
	}
	
	@MessageMapping("/loadCoord/{opt}")
	//@SendTo("/user/"+user.getIp()+"/loadCoord/alone/getResponse")
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
			successSend("/user/"+user.getIp()+"/loadCoord/alone/getRespons", chipId, user);
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
			String serId=api.getFirVersion(0, 1);
			if ("0".equals(opt)) {// 读取参数
				cood = api.getProperty(0, "qcit_location_"+serId, coodPoint);
				if (cood < -2000000000)
					cood = 0;
			} else if ("1".equals(opt)) {// 读取设备参数
				cood = api.getParameter(0, coodPoint);
				if (cood < -2000000000)
					cood = 0;
			} else if ("2".equals(opt)) {// 读取坐标
				cood = api.getProperty(0, "qcit_location_"+serId, coodPoint);
				if (cood < -2000000000)
					cood = 0;
				if("QCIT.Config.InBelt".equals(coodPoint)||"QCIT.Config.BeltToTrack".equals(coodPoint)||"QCIT.Config.TrackToProg".equals(coodPoint)||"QCIT.Config.iProToBack".equals(coodPoint)) {
					api.motorStep(0, motor, new Coordinate(cood));
				}else {
					api.motorMove(0, motor, new Coordinate(cood));
				}
				
			} else if ("3".equals(opt)) {// 读取设备参数坐标
				cood = api.getParameter(0, coodPoint);
				if (cood < -2000000000)
					cood = 0;
				if("QCIT.Config.InBelt".equals(coodPoint)||"QCIT.Config.BeltToTrack".equals(coodPoint)||"QCIT.Config.TrackToProg".equals(coodPoint)||"QCIT.Config.iProToBack".equals(coodPoint)) {
					api.motorStep(0, motor, new Coordinate(cood));
				}else {
					api.motorMove(0, motor, new Coordinate(cood));
				}
			}
			chipId = cood + "";
			successSend("/user/"+user.getIp()+"/readCoord/alone/getResponse", chipId, user);
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
			String serId=api.getFirVersion(0, 1);
			int motor = Integer.valueOf(motorId);
			if ("0".equals(opt)) {// 保存坐标参数到本地
				api.setProperty(0, "qcit_location_"+serId, coodPoint, coord);
			} else if ("1".equals(opt)) {// 保存设备参数
				api.setParameter(0, coodPoint, Long.valueOf(coord));
				api.setProperty(0, "qcit_location_"+serId, coodPoint, coord);
			} else if ("2".equals(opt)) {// 保存坐标到本地
				api.setProperty(0, "qcit_location_"+serId, coodPoint, Axis.fromId(motor));
			} else if ("3".equals(opt)) {// 保存坐标到设备
				long len = api.getCurrentCoordinate(0, motor).getCoordinate();
				api.setParameter(0, coodPoint, len);
				api.setProperty(0, "qcit_location_"+serId, coodPoint, Axis.fromId(motor));
			}
			 String file1="qcit_location"+"_"+serId;
			 api.getMotorSpeed(file1);//获取坐标参数
			chipId = "保存成功";
			successSend("/user/"+user.getIp()+"/writeCoord/alone/getResponse", chipId, user);
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
			successSend("/user/"+user.getIp()+"/motorOpt/alone/getResponse", chipId, user);

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
			successSend("/user/"+user.getIp()+"/searchMotor/alone/getResponse", chipId, user);
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
//	@MessageMapping("/firuploads")
//	@SendTo("/user/firuploads/alone/getResponse")
//	public String firupload(@RequestBody String filestr, Principal principal) {
//		System.out.println(sendnum + ":固件上传--------");
//		return filestr;
//	}

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
class Filepath {
	/**
	 * 
	 */
	private String url;
}

@Data
class LoadMessage {
	private String fileName;
	private String ipStr;
}
