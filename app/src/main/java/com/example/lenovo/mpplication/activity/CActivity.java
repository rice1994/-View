package com.example.lenovo.mpplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class CActivity extends AppCompatActivity {

	private static final String TAG = AActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_c);
		ButterKnife.bind(this);
		Log.e(TAG, "onCreate: C" );
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG, "onRestart: C");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e(TAG, "onNewIntent: C");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart: C");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume: C");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause: C");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop: C");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy: C");
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
		Log.e(TAG, "onSaveInstanceState: C");
	}

	@OnClick(R.id.A)
	public void onClick() {
		startActivity(new Intent(this,AActivity.class));
	}
}
