package com.example.lenovo.mpplication.multithreading.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTest {
	Lock mLock = new ReentrantLock();
	private ArrayList<Integer> arrayList = new ArrayList<Integer>();

	public static void main(String[] args)  {
		final TryLockTest test = new TryLockTest();

		new Thread(){
			public void run() {
				test.insert(Thread.currentThread());
			};
		}.start();

		new Thread(){
			public void run() {
				test.insert(Thread.currentThread());
			};
		}.start();
	}

	public void insert(Thread thread) {
		if(mLock.tryLock()) {
			try {
				System.out.println(thread.getName()+"得到了锁");
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				System.out.println(thread.getName()+"释放了锁");
				mLock.unlock();
			}
		} else {
			System.out.println(thread.getName()+"获取锁失败");
		}
	}
}
