package com.example.lenovo.mpplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class BActivity extends AppCompatActivity {

	private static final String TAG = AActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		ButterKnife.bind(this);
		Log.e(TAG, "onCreate: B");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e(TAG, "onNewIntent: B");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart: B");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume: B");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause: B");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop: B");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy: B");
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
		Log.e(TAG, "onSaveInstanceState: B");
	}

	@OnClick(R.id.back_A)
	public void onClick() {
		startActivity(new Intent(this,AActivity.class));
	}
}
