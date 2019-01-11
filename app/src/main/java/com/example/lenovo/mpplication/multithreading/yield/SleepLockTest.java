package com.example.lenovo.mpplication.multithreading.yield;

public class SleepLockTest {
	private static Object obj = new Object();

	public static void main(String[] args) {
		ThreadA t1 = new ThreadA("t1");
		ThreadA t2 = new ThreadA("t2");
		t1.start();
		t2.start();
	}

	static class ThreadA extends Thread {
		public ThreadA(String name) {
			super(name);
		}

		public void run() {

			synchronized (obj) {

				try {
					for (int i = 0; i < 20; i++) {
						System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i);
						// i整除4时，调用yield
						if (i % 4 == 0)
							Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
