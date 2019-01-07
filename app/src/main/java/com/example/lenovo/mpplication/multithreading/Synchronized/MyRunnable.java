package com.example.lenovo.mpplication.multithreading.Synchronized;

/**
 * @author Qiushui
 */

public class MyRunnable implements Runnable {

	@Override
	public void run() {
		//synchronized代码块：
		synchronized (this) {
			try {
				for (int i = 0; i < 5; i++) {
					Thread.sleep(100); // 休眠100ms
					System.out.println(Thread.currentThread().getName() + " loop " + i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
