package com.small.qcit.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.small.qcit.domain.vo.ResponseVO;
import com.small.qcit.service.UploadService;

import net.sf.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

/**
 * 上传文件
 *
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/fir")
    public ResponseVO uploadImage(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", uploadService.uploadImage(multipartFile));
        return new ResponseVO(jsonObject);
    }
    
    @PostMapping("/sysinfo")
    public ResponseVO sysinfo() throws Exception {
    	File file=null;
    	String filp="";
    	int inum=0;
    	while(inum>-16) {//获取
    		String logTime=getTime(inum);
        	filp=System.getProperty("user.dir")+"\\quest\\qcitsLog\\info."+logTime+".log";
        	file=new File(filp);
        	if(file.exists()) {
        		break;
        	}else {
        		inum--;
        	}
    	}
    	  JSONObject jsonObject = new JSONObject();
    	if(inum==-16) {
    		jsonObject.put("filstr", "无系统文件");
    	}
    	Path path = Paths.get(filp);
    	byte[] data = Files.readAllBytes(path);
    	String result = new String(data, "utf-8");
        jsonObject.put("path", result);

        return new ResponseVO(jsonObject);
    }
    
    public String  getTime(int itime) {
    	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, itime);    //得到前一天
		Date date = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(df.format(date));
		return df.format(date);
    }
    public static void main(String[] args) {
	}
}
