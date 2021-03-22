package com.small.qcit.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.small.qcit.service.BigFileServiceInterface;
import com.small.qcit.upload.Chunk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件Controller
 * 处理文件的上传与下载
 */
@RestController
@RequestMapping("/bigFile")
public class BigFileController {

    @Autowired
    BigFileServiceInterface bigFileServiceInterface;

    /**
     * 处理文件上传POST请求
     * 将上传的文件存放到服务器内
     * @param chunk 文件块
     * @param response 响应
     * @return 上传响应状态
     */
    @PostMapping("/fileUpload")
    public String uploadPost(@ModelAttribute Chunk chunk, HttpServletResponse response){
       return bigFileServiceInterface.fileUploadPost(chunk,response);
    }


}
