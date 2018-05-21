package com.example.lenovo.mpplication.view.basic_operation_of_Path;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class BasicOperationOfPathActivity extends Activity {

	@BindView(R.id.bpv)
	BasePathView mBpv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_operation_of_path);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.basic_operation_of_Path, R.id.addXx_arcTo_of_Path,
			R.id.isXX_set_offset_of_Path, R.id.addArc_arcTo_of_Path,
			R.id.isEmpty_isRect_isConvex_set_offset_of_Path,
			R.id.path_application})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.basic_operation_of_Path:
				mBpv.resetView(BasePathView.basic_operation_of_Path);
				break;
			case R.id.addXx_arcTo_of_Path:
				mBpv.resetView(BasePathView.addXx_arcTo_of_Path);

				break;
			case R.id.isXX_set_offset_of_Path:
				mBpv.resetView(BasePathView.isXX_set_offset_of_Path);

				break;		case R.id.addArc_arcTo_of_Path:
				mBpv.resetView(BasePathView.addArc_arcTo_of_Path);

				break;		case R.id.isEmpty_isRect_isConvex_set_offset_of_Path:
				mBpv.resetView(BasePathView.isEmpty_isRect_isConvex_set_offset_of_Path);

				break;	case R.id.path_application:
				mBpv.resetView(BasePathView.path_application);

				break;
		}
	}
}
