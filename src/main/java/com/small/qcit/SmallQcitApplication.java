package com.small.qcit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//@ComponentScan("com.quaero.base")
@SpringBootApplication
@EnableAsync
public class SmallQcitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallQcitApplication.class, args);
//		SomeMethod m = new SomeMethod();
//		SomeThread t = new SomeThread(m);
//		t.start();
//		new Thread() {
//			public void run() {
//				while(true){
//					m.m1();
//				}
//			}
//		}.start();	
//		while (true) {
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("Current method " + m.getCurrentMethodName());
//		}
	}

}
