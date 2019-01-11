package com.example.lenovo.mpplication.multithreading.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptiblyTest {
	Lock mLock = new ReentrantLock();
	private ArrayList<Integer> arrayList = new ArrayList<Integer>();

	public static void main(String[] args) {
		final LockTest test = new LockTest();
		new Thread() {
			@Override
			public void run() {
				super.run();
				test.insert(Thread.currentThread());

			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				super.run();
				test.insert(Thread.currentThread());

			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				super.run();
				test.insert(Thread.currentThread());

			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				super.run();
				test.insert(Thread.currentThread());

			}
		}.start();
	}

	public void insert(Thread thread) {
		mLock.lock();
		try {
			System.out.println(thread.getName() + "得到了锁");
			for (int i = 0; i < 5; i++) {
				arrayList.add(i);
			}
		} catch (Exception e) {

		} finally {
			System.out.println(thread.getName() + "释放了锁");
			mLock.unlock();

		}
	}
}