package com.example.lenovo.mpplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.activity.AActivity;
import com.example.lenovo.mpplication.data.DataActivity;
import com.example.lenovo.mpplication.drag.DrayAndDropActivity;
import com.example.lenovo.mpplication.event.EventActivity;
import com.example.lenovo.mpplication.intent.IntentActivity;
import com.example.lenovo.mpplication.multithreading.MultithreadingActivity;
import com.example.lenovo.mpplication.rv.RvActivity;

public class MainActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.custom_view, R.id.animations_transitions,
			R.id.resource_types, R.id.event, R.id.activity, R.id.intent,
			R.id.data, R.id.rv_location, R.id.multithreading})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.custom_view:
//				startActivity(new Intent(this,CustomViewActivity.class));
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
				sendIntent.setType("text/plain");
				startActivity(Intent.createChooser(sendIntent, "getResources().getText(R.string.send_to)"));
			//	startActivity(new Intent(this,BlurActivity.class));
				break;
			case R.id.animations_transitions:
				startActivity(new Intent(this,AnimationsTransitionsActivity.class));
//				AlertDialog.Builder builder = new AlertDialog.Builder(this);
//				builder.setMessage("2018/08/17 情人节快乐！！")
//						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								Toast.makeText(MainActivity.this,"确定按钮点击啦！",Toast.LENGTH_SHORT).show();
//							}
//						})
//						.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								Toast.makeText(MainActivity.this,"取消按钮点击啦！",Toast.LENGTH_SHORT).show();
//							}
//						});
//				builder.create();
//				builder.show();
				break;
			case R.id.resource_types:
			//	startActivity(new Intent(this, AnimationsTransitionsActivity.class));
				startActivity(new Intent(this, DrayAndDropActivity.class));
//				FireMissilesDialogFragment fireMissilesDialogFragment = new FireMissilesDialogFragment();
//				fireMissilesDialogFragment.showNow(getSupportFragmentManager(),"ss");
				break;
			case R.id.event:
				startActivity(new Intent(this, EventActivity.class));
				break;
			case R.id.data:
				startActivity(new Intent(this, DataActivity.class));
				break;	case R.id.activity:
				startActivity(new Intent(this, AActivity.class));
				break;
			case R.id.intent:
				startActivity(new Intent(this, IntentActivity.class));
				break;
			case R.id.rv_location:
				startActivity(new Intent(this, RvActivity.class));
				break;
			case R.id.multithreading:
				startActivity(new Intent(this, MultithreadingActivity.class));
				break;
		}
	}

}
