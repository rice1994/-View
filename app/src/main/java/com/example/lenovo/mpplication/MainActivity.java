package com.example.lenovo.mpplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.view.canvas_image_text.CanvasImageTextActivity;
import com.example.lenovo.mpplication.view.basic_operation_of_Path.BasicOperationOfPathActivity;

public class MainActivity extends Activity {

	@BindView(R.id.canvas_image_text)
	Button mCanvasImageText;
	@BindView(R.id.basic_operation_of_Path)
	Button mBasicOperationOfPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.canvas_image_text, R.id.basic_operation_of_Path})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.canvas_image_text:
				startActivity(new Intent(this, CanvasImageTextActivity.class));
				break;
			case R.id.basic_operation_of_Path:
				startActivity(new Intent(this, BasicOperationOfPathActivity.class));
				break;
		}
	}
}
