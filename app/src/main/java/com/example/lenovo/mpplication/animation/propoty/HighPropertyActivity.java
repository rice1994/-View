package com.example.lenovo.mpplication.animation.propoty;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class HighPropertyActivity extends AppCompatActivity {

	private static final String TAG = HighPropertyActivity.class.getSimpleName();
	@BindView(R.id.ValueAnimator)
	Button mValueAnimator;
	@BindView(R.id.ObjectAnimator)
	Button mObjectAnimator;
	@BindView(R.id.MyAnimView)
	MyAnimView mMyAnimView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_property);
		ButterKnife.bind(this);

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && Build.VERSION.SDK_INT >= 19) {
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	@OnClick({R.id.ValueAnimator, R.id.ObjectAnimator})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ValueAnimator:
//				ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
//					@Override
//					public Object evaluate(float fraction, Object startValue, Object endValue) {
//						Log.e(TAG, "evaluate: fraction=" + fraction);
//						Log.e(TAG, "evaluate: startValue=" + startValue);
//						Log.e(TAG, "evaluate: endValue=" + endValue);
//						return fraction * (int) endValue;
//					}
//				}, 0, 100, 200);
//				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//					@Override
//					public void onAnimationUpdate(ValueAnimator animation) {
//
//					}
//				});
//				animator.start();
				mMyAnimView.resetView(MyAnimView.VALUE_ANIMATOR);
				break;
			case R.id.ObjectAnimator:
				mMyAnimView.resetView(MyAnimView.OBJECT_ANIMATOR);

				break;
		}
	}
}
