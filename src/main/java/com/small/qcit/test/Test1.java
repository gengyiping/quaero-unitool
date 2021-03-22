package com.small.qcit.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class Test1 {
	/**
	  * @param args
	  * @throws IOException 
	  */
//	 public static void main(String[] args) throws IOException {
//
//	  FileReader fr=new FileReader("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V177.bin");
//	  
//	  FileWriter fw=new FileWriter("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1771.bin");
//	  int ch=0;
//	  while((ch=fr.read())!=-1){//单个字符进行读取
//	   fw.write(ch);//单个字符进行写入操作
//	  }
//	  fw.close();
//	  fr.close();
//	 }
	

	 /**
	  * @param args
	 * @throws IOException 
	  */
//	 private static final int BUFFER_SIZE = 1024;
//	 public static void main(String[] args) {
//
//	  FileReader fr = null;
//	  FileWriter fw = null;
//	  try {
//	   fr = new FileReader("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V177.bin");//工程所在目录
//	   fw = new FileWriter("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1772.bin");
//	   char buf[] = new char[BUFFER_SIZE];
//	   int len = 0;
//	   while ((len = fr.read(buf)) != -1) {
//	    fw.write(buf, 0, len);
//	   }
//	  } catch (Exception e) {
//	   // TODO: handle exception
//	  } finally {
//	   if (fr != null) {
//	    try {
//	     fr.close();
//	    } catch (IOException e) {
//	     System.out.println("读写失败");
//	    }
//	   }
//	   if (fw != null) {
//	    try {
//	     fw.close();
//	    } catch (IOException e) {
//	     System.out.println("读写失败");
//	    }
//	   }
//	  }
//
//	 }
	
//	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
//		//读取文件(字节流)
//        InputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V177.bin");
//        //写入相应的文件
//        OutputStream out = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1773.bin");
//        //读取数据
//        //一次性取多少字节
//        byte[] bytes = new byte[2048];
//        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
//        int n = -1;
//        //循环取出数据
//        while ((n = in.read(bytes,0,bytes.length)) != -1) {
//            //转换成字符串
//            String str = new String(bytes,0,n,"GBK"); //这里可以实现字节到字符串的转换，比较实用
//            System.out.println(str);
//            //写入相关文件
//            out.write(bytes, 0, n);
//        }
//        //关闭流
//        in.close();
//        out.close();
//	}
	
//	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
//		 //读取文件(缓存字节流)
//	    BufferedInputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V177.bin"));
//	    //写入相应的文件
//	    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1774.bin"));
//	    //读取数据
//	    //一次性取多少字节
//	    byte[] bytes = new byte[2048];
//	    //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
//	    int n = -1;
//	    //循环取出数据
//	    while ((n = in.read(bytes,0,bytes.length)) != -1) {
//	        //转换成字符串
//	        String str = new String(bytes,0,n,"GBK");
//	        System.out.println(str);
//	        //写入相关文件
//	        out.write(bytes, 0, n);
//	    }
//	    //清楚缓存
//	    out.flush();
//	    //关闭流
//	    in.close();
//	    out.close();
//	}
	
	public static void main(String[] args) throws IOException {
		 //读取文件(字符流)
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V177.bin"),"GBK"));//这里主要是涉及中文
        //BufferedReader in = new BufferedReader(new FileReader("d:\\1.txt")));
        //写入相应的文件
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\qcit-221-release-V1775.bin"),"GBK"));
        //BufferedWriter out = new BufferedWriter(new FileWriter("d:\\2.txt"))；
        //读取数据
        //循环取出数据
        String str = null;
        while ((str = in.readLine()) != null) {
            System.out.println(str);
            //写入相关文件
            out.write(str);
            out.newLine();
        }
        
        //清楚缓存
        out.flush();
        //关闭流
        in.close();
        out.close();
	}

}
