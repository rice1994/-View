package com.example.lenovo.mpplication.view.PathMeasure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;


public class PathMeasureActivity extends Activity {

	@BindView(R.id.vie)
	PathMeasureView mVie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path_measure);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.base, R.id.getPosTan, R.id.getMatrix})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.base:
				mVie.resetView(PathMeasureView.base);
				break;
			case R.id.getPosTan:
				mVie.resetView(PathMeasureView.getPosTan);

				break;
			case R.id.getMatrix:
				mVie.resetView(PathMeasureView.getMatrix);

				break;
		}
	}
}
