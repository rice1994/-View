package com.example.lenovo.mpplication.multithreading.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	public static void main(String[] args)  {
		final ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();

		new Thread(){
			public void run() {
				test.read(Thread.currentThread());
			}
		}.start();

		new Thread(){
			public void run() {
				test.write(Thread.currentThread());
			}
		}.start();

	}

	public void read(Thread thread) {
		rwl.readLock().lock();
		try {
			long start = System.currentTimeMillis();

			while(System.currentTimeMillis() - start <= 1) {
				System.out.println(thread.getName()+"正在进行读操作");
			}
			System.out.println(thread.getName()+"读操作完毕");
		} finally {
			rwl.readLock().unlock();
		}
	}

	public void write(Thread thread) {
		rwl.writeLock().lock();
		try {
			long start = System.currentTimeMillis();

			while (System.currentTimeMillis() - start <= 1) {
				System.out.println(thread.getName() + "正在进行写操作");
			}
			System.out.println(thread.getName() + "写操作完毕");
		} finally {
			rwl.writeLock().unlock();
		}
	}
}
