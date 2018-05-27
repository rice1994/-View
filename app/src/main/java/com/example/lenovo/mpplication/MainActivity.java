package com.example.lenovo.mpplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.lenovo.mpplication.view.basic_operation_of_Path.BasicOperationOfPathActivity;
import com.example.lenovo.mpplication.view.bessel_curve.BesselCurveActivity;
import com.example.lenovo.mpplication.view.canvas_image_text.CanvasImageTextActivity;
import com.example.lenovo.mpplication.view.path_finish.PathFinishActivity;
import com.example.lenovo.mpplication.view.setPathEffect.SetPathEffectActivity;
import com.example.lenovo.mpplication.view.view1.CanvasActivity;
import com.example.lenovo.mpplication.view.view2.Canvas2Activity;

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

	@OnClick({R.id.canvas_draw_1, R.id.canvas_draw_2, R.id.canvas_image_text,
			R.id.basic_operation_of_Path, R.id.setPathEffect, R.id.bessel_curve,R.id.path_finish})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.canvas_draw_1:
				//Canvas之绘制图形
				startActivity(new Intent(this, CanvasActivity.class));
				break;
			//Canvas之画布操作
			case R.id.canvas_draw_2:
				startActivity(new Intent(this, Canvas2Activity.class));
				break;
			case R.id.canvas_image_text:
				startActivity(new Intent(this, CanvasImageTextActivity.class));
				break;
			case R.id.basic_operation_of_Path:
				startActivity(new Intent(this, BasicOperationOfPathActivity.class));
				break;
			case R.id.setPathEffect:
				startActivity(new Intent(this, SetPathEffectActivity.class));
				break;
			case R.id.bessel_curve:
				startActivity(new Intent(this, BesselCurveActivity.class));
				break;
			case R.id.path_finish:
				startActivity(new Intent(this, PathFinishActivity.class));
				break;
		}
	}
}
