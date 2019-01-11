package com.example.lenovo.mpplication.multithreading.yield;

public class JoinTest {
	private static Object obj = new Object();

	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getName() + "  run");

		ThreadA t1 = new ThreadA("t1");
		t1.start();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (obj) {


			try {
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(Thread.currentThread().getName() + "  continue");
		}
	}

	static class ThreadA extends Thread {
		public ThreadA(String name) {
			super(name);
		}

		public void run() {

			synchronized (obj) {

				try {
					for (int i = 0; i < 10; i++) {
						System.out.println(Thread.currentThread().getName() + " run");
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
