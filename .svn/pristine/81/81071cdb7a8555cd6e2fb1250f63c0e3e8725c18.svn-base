package com.small.qcit.test;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.method.QCITApi5;
import com.quest.software.bus4j.datatype.CmdCodeException;
import com.quest.software.bus4j.datatype.ErrCodeException;
import com.quest.software.bus4j.module.DataFormatException;
import com.quest.software.bus4j.module.ExecuteException;

@RestController
public class testVue {
	//public  QCITApi5 api5=new QCITApi5();
    @GetMapping("/init")
    public String init() throws ErrCodeException, CmdCodeException, IOException, DocumentException, ExecuteException, DataFormatException{
    	QCITApi5 api5=new QCITApi5();
    	api5.init(1, false);
        return "200";
    }
    @GetMapping("/Reset")
    public String Reset(@RequestParam("**") String mail, @RequestParam("**") String password){
      
       
        return "regist successs";
    }
}