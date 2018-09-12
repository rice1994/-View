package com.example.lenovo.mpplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.animation.PropertyActivity;
import com.example.lenovo.mpplication.animation.tween.TweenActivity;

/**
 * 结束的视图样式：平移、缩放、旋转 & 透明度样式
 * 即补间动画的动画效果就是：平移、缩放、旋转 & 透明度动画
 */
public class AnimationsTransitionsActivity extends Activity {

	@BindView(R.id.valueAnimator)
	Button mValueAnimator;
	@BindView(R.id.btn)
	Button mBtn;
	private String TAG = AnimationsTransitionsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animations_transitions);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.valueAnimator,R.id.tween ,R.id.property})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.valueAnimator:

				break;
			case R.id.tween:
				startActivity(new Intent(this, TweenActivity.class));

				break;
			case R.id.property:
				startActivity(new Intent(this, PropertyActivity.class));

				break;
		}
	}
}
