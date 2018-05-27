package com.example.lenovo.mpplication.view.view2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.lenovo.mpplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Canvas2Activity extends Activity {

	@BindView(R.id.cView)
	Canvas2View cView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canvas2);
		ButterKnife.bind(this);

	}
	@OnClick({R.id.translate,R.id.translate1, R.id.scale1, R.id.scale2, R.id.scale3, R.id.rotate, R.id.rotate1, R.id.rotate2, R.id.skew_45x, R.id.skew_45y, R.id.skew_45xy, R.id.save_and_restore, R.id.cView})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.translate:
				cView.resetView(Canvas2View.translate);
				break;
			case R.id.translate1:
				cView.resetView(Canvas2View.translate1);
				break;
			case R.id.scale1:
				cView.resetView(Canvas2View.scale1);
				break;
			case R.id.scale2:
				cView.resetView(Canvas2View.scale2);
				break;
			case R.id.scale3:
				cView.resetView(Canvas2View.scale3);
				break;
			case R.id.rotate:
				cView.resetView(Canvas2View.rotate);
				break;
			case R.id.rotate1:
				cView.resetView(Canvas2View.rotate1);
				break;
			case R.id.rotate2:
				cView.resetView(Canvas2View.rotate2);
				break;
			case R.id.skew_45x:
				cView.resetView(Canvas2View.skew_45x);
				break;
			case R.id.skew_45y:
				cView.resetView(Canvas2View.skew_45y);
				break;
			case R.id.skew_45xy:
				cView.resetView(Canvas2View.skew_45xy);
				break;
			case R.id.save_and_restore:
				cView.resetView(Canvas2View.save_and_restore);
				break;
		}
	}
}
