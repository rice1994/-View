package com.example.lenovo.mpplication.multithreading.waitAndNotify;

public class WaitTest {

	static class ThreadA1 extends Thread{

		public ThreadA1(String name) {
			super(name);
		}

		public void run() {
			synchronized (this) {
				System.out.println(Thread.currentThread().getName()+" call notify()");
				// 唤醒当前的wait线程
				notify();
				System.out.println(Thread.currentThread().getName()+" call notify()111");

			}
		}
	}

	public static void main(String[] args) {

		ThreadA1 t1 = new ThreadA1("t1");

		synchronized (t1) {
			try {
				// 启动“线程t1”
				System.out.println(Thread.currentThread().getName() + " start t1");
				t1.start();

				// 主线程等待t1通过notify()唤醒。
				System.out.println(Thread.currentThread().getName() + " wait()");
				//wait()的作用是让“当前线程”等待，而“当前线程”是指正在cpu上运行的线程！
				t1.wait();

				System.out.println(Thread.currentThread().getName() + " continue");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
