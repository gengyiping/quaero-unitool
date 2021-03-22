package com.small.qcit.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.small.qcit.config.FileConfig;
import com.small.qcit.enums.CodeEnum;
import com.small.qcit.exception.ErrorCodeException;
import com.small.qcit.service.UploadService;
import com.small.qcit.utils.CheckUtils;
import com.small.qcit.utils.UUIDUtils;

import javax.annotation.Resource;
import java.io.File;

/**
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Resource
    private FileConfig fileConfig;
    private String firName;

    @Override
    public String uploadImage(MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new ErrorCodeException(CodeEnum.FAILED);
        }

        return execute(multipartFile);
    }

    private String execute(MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        String type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//        if (!CheckUtils.isImage(type)) {
//            throw new ErrorCodeException(CodeEnum.UPLOADED_FILE_IS_NOT_AN_IMAGE);
//        }
        if(firName!=null&&!"".equals(firName)) {//删除上一次的文件
        	  File file = new File(firName);
        	  file.delete();
        }
        String fileName = UUIDUtils.create() + "." + type;
        String respPath = fileName;
       //System.out.println("下载的路径："+fileConfig.getDirectoryMapping() + fileConfig.getUploadPath() + fileName);
       // File file = new File(fileConfig.getDirectoryMapping() + fileConfig.getUploadPath() + fileName);
        
        
        firName=System.getProperty("user.dir") + "\\qcit\\"+ fileName;
        File file = new File(firName);

//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
         if (!file.exists()) {
             //file.mkdirs();
             file.createNewFile();
         }

        multipartFile.transferTo(file);

        return respPath;
    }

	@Override
	public String getFirPath() throws Exception {
		 File file = new File(firName);
		if(!file.exists()) {
			return null;
		}
		return firName;
	}
}
