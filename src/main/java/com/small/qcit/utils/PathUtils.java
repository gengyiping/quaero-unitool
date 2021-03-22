package com.small.qcit.utils;

import java.io.File;
import org.springframework.util.ClassUtils;
/**
 * @author 李建 on 2019/12/4
 * 路径工具类
 * 功能：
 *      1、获取存放服务器文件列表的资源文件路径
 *      2、获取服务器存放文件的目录路径
 */
public class PathUtils {

    /**
     * 获取服务器存放文件的目录路径
     * @return 目录路径（String)
     */
    public static String getFileDir(){
        String path= ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/file";
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        return path;
    }
    public static void main(String[] args) {
		System.out.println(getFileDir());
	}
}
