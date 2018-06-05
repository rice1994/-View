package com.example.lenovo.mpplication.view.Matrix_Analisis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class MatrixAnalisisActivity extends Activity {

	@BindView(R.id.view)
	MatrixAnalisisView mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matrix_analisis);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.setPolyToPoly, R.id.setRectToRect, R.id.Camera})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.setPolyToPoly:
				mView.resetView(MatrixAnalisisView.setPolyToPoly);
				break;
			case R.id.setRectToRect:
				mView.resetView(MatrixAnalisisView.setRectToRect);

				break;
			case R.id.Camera:
				mView.resetView(MatrixAnalisisView.Camera);

				break;
		}
	}
}
