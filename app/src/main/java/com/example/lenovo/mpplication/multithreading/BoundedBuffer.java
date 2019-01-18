package com.example.lenovo.mpplication.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();

	final Object[] items = new Object[5];
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException {
		lock.lock();

		try {
			while (count == items.length) {
				notFull.wait();
			}
		} finally {
			lock.unlock();    // 释放锁
		}

	}


}