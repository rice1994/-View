package com.example.lenovo.mpplication.animation.tween;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.*;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class TweenActivity extends AppCompatActivity {

	@BindView(R.id.btn)
	Button mBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tween);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.translate, R.id.scale, R.id.rotate, R.id.alpha, R.id.set})
	public void onClick(View view) {
		Animation animation;
		switch (view.getId()) {
			case R.id.translate:
				//xml实现
				animation = AnimationUtils.loadAnimation(this, R.anim.translate);
				//代码实现
				animation = new TranslateAnimation(0, 500, 0, 500);
				animation.setDuration(2000);
				// 步骤3:播放动画
				mBtn.setAnimation(animation);
				break;
			case R.id.scale:
				animation = AnimationUtils.loadAnimation(this, R.anim.scale);
				animation = new ScaleAnimation(0, 2, 0, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(2000);
				mBtn.setAnimation(animation);
				break;
			case R.id.rotate:
				animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
				animation = new RotateAnimation(0, 235, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setDuration(2000);
				mBtn.setAnimation(animation);
				break;
			case R.id.alpha:
				animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
				animation = new AlphaAnimation(1, 0);
				animation.setDuration(2000);
				mBtn.setAnimation(animation);
				break;
			case R.id.set:
				animation = AnimationUtils.loadAnimation(this, R.anim.set);
//				mBtn.startAnimation(setAnimation);
				// 组合动画设置
				AnimationSet setAnimation = new AnimationSet(true);
				// 步骤1:创建组合动画对象(设置为true)
				// 步骤2:设置组合动画的属性
				// 特别说明以下情况
				// 因为在下面的旋转动画设置了无限循环(RepeatCount = INFINITE)
				// 所以动画不会结束，而是无限循环
				// 所以组合动画的下面两行设置是无效的
				setAnimation.setRepeatMode(Animation.RESTART);
				setAnimation.setRepeatCount(1);// 设置了循环一次,但无效
				// 步骤3:逐个创建子动画(方式同单个动画创建方式,此处不作过多描述)
				// 子动画1:旋转动画
				Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotate.setDuration(1000);
				rotate.setRepeatMode(Animation.RESTART);
				rotate.setRepeatCount(Animation.INFINITE);
				// 子动画2:平移动画
				Animation translate = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, -0.5f,
						TranslateAnimation.RELATIVE_TO_PARENT, 0.5f,
						TranslateAnimation.RELATIVE_TO_SELF, 0
						, TranslateAnimation.RELATIVE_TO_SELF, 0);
				translate.setDuration(10000);

				// 子动画3:透明度动画
				Animation alpha = new AlphaAnimation(1, 0);
				alpha.setDuration(3000);
				alpha.setStartOffset(7000);

				// 子动画4:缩放动画
				Animation scale1 = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				scale1.setDuration(1000);
				scale1.setStartOffset(4000);

				// 步骤4:将创建的子动画添加到组合动画里
				setAnimation.addAnimation(alpha);
				setAnimation.addAnimation(rotate);
				setAnimation.addAnimation(translate);
				setAnimation.addAnimation(scale1);

				// 步骤5:播放动画
				mBtn.setAnimation(setAnimation);
				break;
		}
	}
}
