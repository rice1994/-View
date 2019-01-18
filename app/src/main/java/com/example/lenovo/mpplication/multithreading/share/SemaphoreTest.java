package com.example.lenovo.mpplication.multithreading.share;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	public static void main(String[] args) {
		int count =8;
		final Semaphore semaphore = new Semaphore(5);

		for(int i=0;i<count;i++){
			new Worker(i,semaphore).start();
		}

	}

	static class Worker extends Thread{
		private int num;
		private Semaphore semaphore;
		public Worker(int num,Semaphore semaphore){
			this.num = num;
			this.semaphore = semaphore;
		}

		@Override
		public void run() {
			super.run();
			try {
				semaphore.acquire();
				System.out.println("工人"+this.num+"占用一个机器在生产...");
				Thread.sleep(1000);
				System.out.println("工人"+this.num+"释放出机器");
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
