package com.small.qcit.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SomeThread extends Thread {

	private SomeMethod m;

	public SomeThread(SomeMethod m) {
		this.m = m;
	}

	@Override
	public void run() {

		Class<?> classz = null;
		try {
			classz = Class.forName("com.small.qcit.test.SomeMethod");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
//		Method[] declaredMethods = classz.getDeclaredMethods();
//		while (true) {
//			for (Method method : declaredMethods) {
//				if (!method.getName().startsWith("m")) {
//					continue;
//				}
//				try {
//					Thread.currentThread().sleep(2000);
//					method.invoke(m, null);
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//
//				}
//			}
//
//		}

	}
}
