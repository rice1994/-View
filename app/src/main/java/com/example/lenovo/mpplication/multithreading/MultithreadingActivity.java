package com.example.lenovo.mpplication.multithreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.multithreading.Synchronized.SynchronizedActivity;
import com.example.lenovo.mpplication.multithreading.waitAndNotify.WaitAndNotifyActivity;

public class MultithreadingActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multithreading);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.Synchronized, R.id.button5, R.id.button6, R.id.button7, R.id.button8})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.Synchronized:
				startActivity(new Intent(this, SynchronizedActivity.class));
				break;
			case R.id.button5:
				startActivity(new Intent(this, WaitAndNotifyActivity.class));

				break;
			case R.id.button6:

				break;
			case R.id.button7:

				break;
			case R.id.button8:
				break;
		}
	}
}
