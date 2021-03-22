package com.small.qcit.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.method.QCITApi5;

@RestController
public class testVue {
	//public  QCITApi5 api5=new QCITApi5();
    @GetMapping("/init")
    public String init() throws Exception{
    	QCITApi5 api5=new QCITApi5();
    	api5.init(1, false);
        return "200";
    }
    @GetMapping("/Reset")
    public String Reset(@RequestParam("**") String mail, @RequestParam("**") String password){
      
       
        return "regist successs";
    }
}