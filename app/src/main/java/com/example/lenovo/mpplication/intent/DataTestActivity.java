package com.example.lenovo.mpplication.intent;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class DataTestActivity extends AppCompatActivity {

	private static final String TAG = DataTestActivity.class.getSimpleName();
	@BindView(R.id.text)
	TextView mText;
	@BindView(R.id.btn)
	Button mBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text2);
		ButterKnife.bind(this);
		mText.setText(getIntent().getDataString());
		Log.e(TAG, "onCreate: A");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e(TAG, "onRestart: A");
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.e(TAG, "onNewIntent: A");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.e(TAG, "onStart: A");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "onResume: A");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "onPause: A");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e(TAG, "onStop: A");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e(TAG, "onDestroy: A");
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
		Log.e(TAG, "onSaveInstanceState: A");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.e(TAG, "onRestoreInstanceState: A");


	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.e(TAG, "onSaveInstanceState: A");

	}

	@OnClick(R.id.btn)
	public void onClick() {
		startActivity(new Intent(this,DataTestActivity.class));
	}
}
