package com.example.lenovo.mpplication.multithreading.waitAndNotify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class WaitAndNotifyActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait_and_notify);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn1:
				WaitTest.ThreadA1 t1 = new WaitTest.ThreadA1("t1");
				synchronized(t1) {
					try {
						// 启动“线程t1”
						System.out.println(Thread.currentThread().getName()+" start t1");
						t1.start();

						// 主线程等待t1通过notify()唤醒。
						System.out.println(Thread.currentThread().getName()+" wait()");
						t1.wait();

						System.out.println(Thread.currentThread().getName()+" continue");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			case R.id.btn2:
				break;
			case R.id.btn3:
				break;
		}
	}
}
