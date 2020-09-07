package com.small.qcit.test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TestController {


 @RequestMapping("/testSpringBoot")
 @ResponseBody
 public String testSpringBoot() {
 return "测试成功";
}
}