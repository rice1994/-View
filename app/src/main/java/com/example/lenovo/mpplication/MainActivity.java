package com.example.lenovo.mpplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.custom_view, R.id.animations_transitions})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.custom_view:
				startActivity(new Intent(this,CustomViewActivity.class));
				break;
			case R.id.animations_transitions:
				startActivity(new Intent(this,AnimationsTransitionsActivity.class));

				break;
		}
	}
}
