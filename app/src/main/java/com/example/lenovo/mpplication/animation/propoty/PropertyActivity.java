package com.example.lenovo.mpplication.animation.propoty;

import android.animation.*;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.animation.AnimateLayoutChangesActivity;
import com.example.lenovo.mpplication.animation.TransitionFrameworkActivity;
import com.example.lenovo.mpplication.animation.tween.ZoomActivity;

/**
 * ValueAnimator是属性动画的核心类，灵活强大，需要开发人员自己实现动画需求，并且它只是对值进行了一个平滑的动画过渡。
 * 而ObjectAnimator是ValueAnimator的子类，可以对目标对象的某个属性值进行修改，也就是说某个对象执行动画，同时不再需要实现它ValueAnimator.AnimatorUpdateListener，因为动画属性会自动更新
 * 通过目标对象属性的setter函数更新属性值，如果没有setter函数，那么将会通过反射去更新。
 * <p>
 * translationX和translationY：这些属性控制View所在的位置，作为由其布局容器设置的左侧和顶部坐标的增量。
 * rotation,, rotationX和rotationY：这些属性控制2D（rotation属性）中的旋转和围绕轴点的3D。
 * scaleX和scaleY：这些属性控制View围绕其轴心点的2D缩放。
 * pivotX和pivotY：这些属性控制枢轴点的位置，围绕该枢轴点进行旋转和缩放变换。默认情况下，轴心点位于对象的中心。
 * x和y：这些是简单的实用程序属性，用于描述View在其容器中的最终位置，作为左值和顶值以及translationX和translateY值的总和。
 * alpha：表示视图上的Alpha透明度。默认情况下，此值为1（不透明），值为0表示完全透明（不可见）。
 */
public class PropertyActivity extends AppCompatActivity {

	private static final String TAG = PropertyActivity.class.getSimpleName();
	@BindView(R.id.valueAnimator)
	Button mValueAnimator;
	@BindView(R.id.ObjectAnimator)
	Button mObjectAnimator;
	@BindView(R.id.AnimatorSet)
	Button mAnimatorSet;
	@BindView(R.id.AnimationView)
	Button mAnimationView;
	@BindView(R.id.stateListAnimator)
	Button mStateListAnimator;
	@BindView(R.id.AnimatedVectorDrawable)
	ImageView mAnimatedVectorDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property);
		ButterKnife.bind(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Drawable drawable = mAnimatedVectorDrawable.getDrawable();
		if (drawable instanceof Animatable)
			((Animatable) drawable).start();
	}

	@OnClick({R.id.valueAnimator, R.id.ObjectAnimator, R.id.AnimatorSet, R.id.stateListAnimator
			, R.id.Specify_keyframes, R.id.ViewPropertyAnimator, R.id.animations_in_XML, R.id.move_view
			, R.id.fling_animation, R.id.zoom_animation, R.id.SpringAnimation, R.id.animateLayoutChanges
			, R.id.transition_framework})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.valueAnimator:
				ValueAnimator animator;
				//可以通过ofFloat、ofInt等静态工厂函数构建ValueAnimator
				animator = ValueAnimator.ofInt(0, 100);
				//设置动画的自定义类型，类型估值器
				//例如如下是：
				// fraction：0~1，startValue=0，endValue=100
				// fraction：0~1，startValue=100，endValue=200
				animator = ValueAnimator.ofObject(new TypeEvaluator() {
					@Override
					public Object evaluate(float fraction, Object startValue, Object endValue) {
						Log.e(TAG, "evaluate: fraction=" + fraction);
						Log.e(TAG, "evaluate: startValue=" + startValue);
						Log.e(TAG, "evaluate: endValue=" + endValue);
						return fraction * (int) endValue;
					}
				}, 0, 100, 200);
				animator = ValueAnimator.ofFloat(0f, 100f);
				animator.setDuration(1000);
				//每次更新属性值时就会调用onAnimationUpdate函数，在这里可以获得新的属性值
				//十分灵活，只操作属性值，可以运用于任何对象之上
				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float animatedValue = (float) animation.getAnimatedValue();
						Log.e(TAG, "onAnimationUpdate: animatedValue=" + animatedValue);
						mAnimationView.setTranslationX(animatedValue);
						if (animatedValue == 200) {
							LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mAnimationView.getLayoutParams();
							if (params != null) {
								String s = "leftMargin = " + params.leftMargin + " rightMargin = " + params.rightMargin
										+ " getLeft = " + mAnimationView.getLeft() + " getRight = " + mAnimationView.getRight() + " getWidth = " + mAnimationView.getWidth();
								Log.i(TAG, s);
							}
							int[] pos = new int[2];
							//以父控件为原点的mBtn坐标
							mAnimationView.getLocationOnScreen(pos);
							//以屏幕为原点的mBtn坐标
							int[] pos1 = new int[2];
							mAnimationView.getLocationInWindow(pos1);
							Rect rect = new Rect();
							Rect rect1 = new Rect();
							//mBtn宽高
							mAnimationView.getLocalVisibleRect(rect);
							//mBtn在屏幕中偏移量
							mAnimationView.getGlobalVisibleRect(rect1);
							mAnimationView.getGlobalVisibleRect(rect1);
						}
					}
				});
				animator.start();
				break;
			case R.id.ObjectAnimator:
				ObjectAnimator animator1 = ObjectAnimator.ofFloat(mAnimationView, "translationX", 100f);
				animator1.addListener(new Animator.AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
						Log.e(TAG, "onAnimationStart: ");
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						Log.e(TAG, "onAnimationEnd: ");

					}

					@Override
					public void onAnimationCancel(Animator animation) {
						Log.e(TAG, "onAnimationCancel: ");

					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						Log.e(TAG, "onAnimationRepeat: ");

					}
				});
				animator1.setDuration(1000);
				animator1.start();
				break;
			case R.id.AnimatorSet:
				AnimatorSet animatorSet = new AnimatorSet();
				ObjectAnimator translationX = ObjectAnimator.ofFloat(mAnimationView, "translationX", 200);
				ObjectAnimator scaleY = ObjectAnimator.ofFloat(mAnimationView, "scaleY", 0.5f, 1.5f, 1f);
				ObjectAnimator rotation = ObjectAnimator.ofFloat(mAnimationView, "rotation", 0, 270, 90, 180, 0);
				animatorSet.play(translationX).before(rotation).after(scaleY);
				animatorSet.setDuration(3000);
				animatorSet.start();
				break;
			case R.id.stateListAnimator:
				StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(this, R.drawable.animate_scale);
				mStateListAnimator.setStateListAnimator(stateListAnimator);
				break;
			case R.id.Specify_keyframes:
				PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX", 0, 600);
				PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY", 0, 600);
				ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(mAnimationView, xHolder, yHolder);
				animator2.setDuration(1000);
				animator2.start();
//				Keyframe scaleFrame1 = Keyframe.ofFloat(0f, 1.0f);
//				Keyframe scaleFrame2 = Keyframe.ofFloat(0.5f, 2.0f);
//				Keyframe scaleFrame3 = Keyframe.ofFloat(1.0f, 1.0f);
//				PropertyValuesHolder scaleX1 = PropertyValuesHolder.ofKeyframe("scaleX", scaleFrame1, scaleFrame2, scaleFrame3);
//				PropertyValuesHolder scaleY1 = PropertyValuesHolder.ofKeyframe("scaleY", scaleFrame1, scaleFrame2, scaleFrame3);
//				ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(mAnimationView, scaleX1, scaleY1);
//				animator3.setDuration(2000);
//				animator3.start();
				break;
			case R.id.ViewPropertyAnimator:
//				PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 50f);
//				PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);
//				ObjectAnimator.ofPropertyValuesHolder(mAnimationView, pvhX, pvhY).start();
				//优雅方式：类似ObjectAnimator,会改变对象属性值
				//	mAnimationView.animate().x(50f).y(100f).setDuration(1000);
				mAnimationView.animate().alpha(0.5f).setDuration(1000);
				mAnimationView.animate().scaleX(2f).scaleY(2f).setDuration(1000);
				mAnimationView.animate().scaleX(2f).scaleY(2f).setDuration(1000);
				mAnimationView.animate().translationX(300).translationY(300).setDuration(1000);
				break;
			case R.id.animations_in_XML:
				//ValueAnimator - <animator>
				//ObjectAnimator - <objectAnimator>
				//AnimatorSet - <set>
				AnimatorSet animator3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_objectanimator);
				animator3.setTarget(mAnimationView);
				animator3.start();
				ValueAnimator animator4 = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.value_animator);
				animator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float animatedValue = (float) animation.getAnimatedValue();
						mAnimationView.setTranslationX(animatedValue);
					}
				});
				animator4.start();

				break;
			case R.id.move_view:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					Path path = new Path();
					path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true);
					ObjectAnimator animation = ObjectAnimator.ofFloat(mAnimationView, View.X, View.Y, path);
					animation.setDuration(2000);
					animation.start();

//					Path path = new Path();
//					path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true);
//					PathInterpolator pathInterpolator = new PathInterpolator(path);
//					ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 100f);
//					animation.setInterpolator(pathInterpolator);
//					animation.start();
				} else {
					// Create animator without using curved path
				}

				break;
			case R.id.fling_animation:
				FlingAnimation fling = new FlingAnimation(mAnimationView, DynamicAnimation.SCROLL_X);
				fling.setStartVelocity(5).setMinValue(0).setMaxValue(50).setFriction(1.1f).start();
				break;
			case R.id.zoom_animation:
				startActivity(new Intent(this, ZoomActivity.class));
				break;
			case R.id.SpringAnimation:
				//	startActivity(new Intent(this, ZoomActivity.class));
				break;
			case R.id.animateLayoutChanges:
				startActivity(new Intent(this, AnimateLayoutChangesActivity.class));
				break;
			case R.id.transition_framework:
				startActivity(new Intent(this, TransitionFrameworkActivity.class));
				break;
		}
	}

	private void ss() {
		//设置动画的自定义类型，类型估值器
		// fraction：0~1，startValue=0，endValue=100
		// fraction：0~1，startValue=100，endValue=200
		ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
			@Override
			public Object evaluate(float fraction, Object startValue, Object endValue) {
				return fraction * (int) endValue;
			}
		}, 0, 100, 200);
		animator.setDuration(1000);
		//每次更新属性值时就会调用onAnimationUpdate函数，在这里可以获得新的属性值
		//十分灵活，只操作属性值，可以运用于任何对象之上
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float animatedValue = (float) animation.getAnimatedValue();
				Log.e(TAG, "onAnimationUpdate: animatedValue=" + animatedValue);
				mAnimationView.setTranslationX(animatedValue);

			}
		});
		animator.start();
	}
}
