package com.example.lenovo.mpplication.view.setPathEffect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class SetPathEffectActivity extends Activity {

	@BindView(R.id.view)
	SetPathEffectView mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_path_effect);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.basic_operation_of_Path, R.id.CornerPathEffect, R.id.DiscretePathEffect, R.id.DashPathEffect, R.id.PathDashPathEffect, R.id.ComposePathEffect, R.id.SumPathEffect})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.basic_operation_of_Path:
				mView.resetView(SetPathEffectView.basic_operation_of_Path);
				break;
			case R.id.CornerPathEffect:
				mView.resetView(SetPathEffectView.CornerPathEffect1);

				break;
			case R.id.DiscretePathEffect:
				mView.resetView(SetPathEffectView.DiscretePathEffect1);

				break;
			case R.id.DashPathEffect:
				mView.resetView(SetPathEffectView.DashPathEffect1);

				break;
			case R.id.PathDashPathEffect:
				mView.resetView(SetPathEffectView.PathDashPathEffect1);

				break;
			case R.id.ComposePathEffect:
				mView.setPhase(0);
				mView.resetView(SetPathEffectView.ComposePathEffect1);

				break;
			case R.id.SumPathEffect:
				mView.resetView(SetPathEffectView.SumPathEffect1);

				break;
		}
	}
}
