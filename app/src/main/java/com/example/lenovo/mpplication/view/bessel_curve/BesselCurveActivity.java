package com.example.lenovo.mpplication.view.bessel_curve;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class BesselCurveActivity extends Activity {

	@BindView(R.id.view)
	BesselCurveView mView;
	@BindView(R.id.rg)
	RadioGroup mRg;
	@BindView(R.id.demo)
	Bezier3 mDemo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bessel_curve);
		ButterKnife.bind(this);
		mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.control1)
					mView.resetView(BesselCurveView.control1);
				else
					mView.resetView(BesselCurveView.control2);

			}
		});
	}

	@OnClick({R.id.curve1, R.id.curve2, R.id.curve3, R.id.curve_demo})
	public void onClick(View view) {
		mRg.setVisibility(view.getId() == R.id.curve3 ? View.VISIBLE : View.GONE);
		if (view.getId() == R.id.curve_demo) {
			mView.setVisibility(View.GONE);
			mDemo.setVisibility(View.VISIBLE);
		}else {
			mView.setVisibility(View.VISIBLE);
			mDemo.setVisibility(View.GONE);
		}
		switch (view.getId()) {
			case R.id.curve1:
				mView.resetView(BesselCurveView.curve1);
				break;
			case R.id.curve2:
				mView.resetView(BesselCurveView.curve2);
				break;
			case R.id.curve3:
				mView.resetView(BesselCurveView.control1);
				break;
		}
	}
}
