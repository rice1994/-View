package com.example.lenovo.mpplication.view.view1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


import com.example.lenovo.mpplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *Canvas之绘制图形
 */
public class CanvasActivity extends Activity {

	@BindView(R.id.baseView)
	BaseView mBaseView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_view);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.drawPoint, R.id.drawLine, R.id.drawRect, R.id.drawRoundRect, R.id.drawOval, R.id.drawCircle,
			R.id.center_oval_arc, R.id.oval_arc, R.id.center_circle_arc, R.id.circle_arc,R.id.style, R.id.tu})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.drawPoint:
				mBaseView.resetView(BaseView.DrawPoint);
				break;
			case R.id.drawLine:
				mBaseView.resetView(BaseView.DrawLine);
				break;
			case R.id.drawRect:
				mBaseView.resetView(BaseView.DrawRect);
				break;
			case R.id.drawRoundRect:
				mBaseView.resetView(BaseView.DrawRoundRect);
				break;
			case R.id.drawOval:
				mBaseView.resetView(BaseView.DrawOval);
				break;
			case R.id.drawCircle:
				mBaseView.resetView(BaseView.DrawCircle);
				break;
			case R.id.center_oval_arc:
				mBaseView.resetView(BaseView.centerOvalArc);
				break;
			case R.id.oval_arc:
				mBaseView.resetView(BaseView.ovalArc);
				break;
			case R.id.center_circle_arc:
				mBaseView.resetView(BaseView.centerCircleArc);
				break;
			case R.id.circle_arc:
				mBaseView.resetView(BaseView.circleArc);
				break;
			case R.id.style:
				mBaseView.resetView(BaseView.style);
				break;
			case R.id.tu:
				mBaseView.resetView(BaseView.tu);
				break;
		}
	}
}
