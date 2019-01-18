package com.example.lenovo.mpplication.multithreading.share;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		int n = 4;
		final CyclicBarrier barrier =	new CyclicBarrier(n, new Runnable() {
			@Override
			public void run() {
				System.out.println("当前线程"+Thread.currentThread().getName());
			}
		});
		for(int i=0;i<n;i++){
			if(i==n-1){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			new Writer(barrier).start();
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
//					try {
//						Thread.sleep(5000);      //以睡眠来模拟写入数据操作
//						System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
//						barrier.wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					System.out.println("所有线程写入完毕，继续处理其他任务...");
//				}
//			}).start();
		}
	}

	static class Writer extends Thread {
		private CyclicBarrier cyclicBarrier;

		public Writer(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run() {
			System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
			try {
				Thread.sleep(5000);      //以睡眠来模拟写入数据操作
				System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
				try {
					cyclicBarrier.await(2000, TimeUnit.MILLISECONDS);
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println("所有线程写入完毕，继续处理其他任务...");
		}
	}
}
