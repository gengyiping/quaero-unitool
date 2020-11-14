package com.small.qcit.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class test {
	public static ExecutorService executor=Executors.newFixedThreadPool(20);
	public static void main(String[] args) {
		Future<Integer> inject = null;
		try {
			inject.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//            inject = executor.submit(() -> move2cache(0));
//           try {
//			int  cacheRemain = inject.get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	public static int move2cache(int num){
		return 0;
	}

}
