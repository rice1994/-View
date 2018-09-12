package com.example.lenovo.mpplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.drag.DrayAndDropActivity;

public class MainActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.custom_view, R.id.animations_transitions, R.id.resource_types})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.custom_view:
				startActivity(new Intent(this,CustomViewActivity.class));
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
		}
	}

}
