package com.example.lenovo.mpplication.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class IntentActivity extends AppCompatActivity {
	String ACTION="com.example.lenovo.mpplication001.actiontest";
//	String ACTION="com.example.smyh006intent01.MY_ACTION";
	String CATEGORY="com.example.lenovo.mpplication001.categorytest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent);
		ButterKnife.bind(this);
	}

	@OnClick({ R.id.category ,R.id.data,R.id.type})
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
			case R.id.category:
				intent = new Intent();
				intent.setAction(ACTION);
				intent.addCategory(CATEGORY);
				startActivity(intent);
				//启动另一个Activity，（通过action属性进行查找）
//			            Intent intent = new Intent();
//			              //设置动作（实际action属性就是一个字符串标记而已）
//			             intent.setAction("com.example.smyh006intent01.MY_ACTION"); //方法：Intent android.content.Intent.setAction(String action)
//				            startActivity(intent);
				break;
			case R.id.data:
				intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				Uri data = Uri.parse("http://www.baidu.com");
				intent.setData(data);
				startActivity(intent);
				break;
			case R.id.type:
				intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setType("text/plain");
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				break;
		}
	}
}
