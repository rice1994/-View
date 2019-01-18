package com.example.lenovo.mpplication.multithreading.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerText {
	public static void main(String[] args) {
		System.out.println("main  "+Thread.currentThread());
		Timer timer = new Timer(true);

		timer.schedule(new MyTimerTask(),3000,1000);
		try {
			Thread.sleep(4000);
			System.out.println("main 睡眠结束 ");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static class MyTimerTask extends TimerTask{

		int i=1;
		@Override
		public void run() {
				System.out.println("schedule(TimerTask task, 3000,1000)  "+i++);
		}
	}
}
