package com.small.qcit.test;

public class SomeMethod {

	private String currentMethodName;

	public void m1() {
		setCurrentMethodName("m1");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("m1 executing .....");
	}

	public void m2() {
		setCurrentMethodName("m2");
		System.out.println("m2 executing .....");
	}

	public void m3() {
		setCurrentMethodName("m3");
		System.out.println("m3 executing .....");
	}

	public void m4() {
		setCurrentMethodName("m4");
		System.out.println("m4 executing .....");
	}

	public String getCurrentMethodName() {
		return currentMethodName;
	}

	public void setCurrentMethodName(String currentMethodName) {
		this.currentMethodName = currentMethodName;
	}

}