package com.example.lenovo.mpplication.view.path_finish;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lenovo.mpplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PathFinishActivity extends Activity {

	@BindView(R.id.view)
	PathFinishView mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path_finish);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.Xxx, R.id.rXxx, R.id.EVEN_ODD, R.id.INVERSE_EVEN_ODD, R.id.WINDING, R.id.INVERSE_WINDING, R.id.op_demo, R.id.op, R.id.bounds})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.Xxx:
				mView.resetView(PathFinishView.XXX);
				break;
			case R.id.rXxx:				mView.resetView(PathFinishView.RXXX);

				break;
			case R.id.EVEN_ODD:				mView.resetView(PathFinishView.EVEN_ODD);

				break;
			case R.id.INVERSE_EVEN_ODD:				mView.resetView(PathFinishView.INVERSE_EVEN_ODD);

				break;
			case R.id.WINDING:				mView.resetView(PathFinishView.WINDING);

				break;
			case R.id.INVERSE_WINDING:				mView.resetView(PathFinishView.INVERSE_WINDING);

				break;
			case R.id.op_demo:				mView.resetView(PathFinishView.OP_DEMO);

				break;
			case R.id.op:				mView.resetView(PathFinishView.OP);

				break;
			case R.id.bounds:				mView.resetView(PathFinishView.BOUNDS);

				break;
		}
	}
}
