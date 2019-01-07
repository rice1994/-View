package com.example.lenovo.mpplication.multithreading.Synchronized;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

/**
 *
 synchronized的基本规则:
 第一条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程对“该对象”的该“synchronized方法”或者“synchronized代码块”的访问将被阻塞。
 第二条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程仍然可以访问“该对象”的非同步代码块。
 第三条: 当一个线程访问“某对象”的“synchronized方法”或者“synchronized代码块”时，其他线程对“该对象”的其他的“synchronized方法”或者“synchronized代码块”的访问将被阻塞。

 synchronized方法 和 synchronized代码块:synchronized代码块可以更精确的控制冲突限制访问区域，有时候表现更高效率。


 synchronized (this){

 }

 实例锁 和 全局锁:

 简单来说：实例锁是对象锁，全局锁是类锁（有static）

 */
public class SynchronizedActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_synchronized);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.btn, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
	public void onClick(View view) {
		final Something x = new Something();
		final Something y = new Something();
		switch (view.getId()) {
			case R.id.btn:

				MyRunnable myRunnable = new MyRunnable();
				Thread thread1 = new Thread(myRunnable, "Thread1");
				Thread thread2 = new Thread(myRunnable, "Thread2");
				thread1.start();
				thread2.start();
				break;
			case R.id.btn1:
				System.out.println("------x.isSyncA()与x.isSyncB()----不能被同时访问--");
				new Thread(new Runnable() {
					@Override
					public void run() {
						x.isSyncA();

					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						x.isSyncB();

					}
				}).start();
				break;
			case R.id.btn2:
				System.out.println("------ x.isSyncA()与y.isSyncA()---同时访问---");
				new Thread(new Runnable() {
					@Override
					public void run() {
						x.isSyncA();

					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						y.isSyncA();

					}
				}).start();
				break;
			case R.id.btn3:
				System.out.println("------  x.cSyncA()与y.cSyncB())----不能被同时访问--");
				new Thread(new Runnable() {
					@Override
					public void run() {
						x.cSyncA();

					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						y.cSyncB();

					}
				}).start();
				break;
			case R.id.btn4:
				System.out.println("------  x.isSyncA()与Something.cSyncA()----同时访问--");
				new Thread(new Runnable() {
					@Override
					public void run() {
						x.isSyncA();

					}
				}).start();
				new Thread(new Runnable() {
					@Override
					public void run() {
						Something.cSyncA();
					}
				}).start();
				break;
		}
	}
}
