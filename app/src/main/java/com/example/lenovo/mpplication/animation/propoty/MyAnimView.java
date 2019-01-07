package com.example.lenovo.mpplication.animation.propoty;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/9/19.
 */
public class MyAnimView extends BaseView {

	public static final int RADIUS = 50;
	public static final int VALUE_ANIMATOR = 12;
	public static final int OBJECT_ANIMATOR = 13;

	private Point currentPoint;

	private Paint mPaint;

	public MyAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLUE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (currentPoint == null) {
			currentPoint = new Point(RADIUS, RADIUS);
			drawCircle(canvas);
			Point startPoint = new Point(RADIUS, RADIUS);
			Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
			ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
			anim.setInterpolator(new myInterpolation());
			switch (mType){
				case VALUE_ANIMATOR:
				anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							currentPoint = (Point) animation.getAnimatedValue();
							invalidate();
						}
					});
					anim.setDuration(5000);
					anim.start();
					break;
				case OBJECT_ANIMATOR:
					anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							currentPoint = (Point) animation.getAnimatedValue();
							invalidate();
						}
					});
					ObjectAnimator anim1 = ObjectAnimator.ofObject(this, "color",
							new ColorEvaluator(), "#0000FF", "#FF0000","#38d325");
					anim1.setInterpolator(new myInterpolation());
					AnimatorSet animatorSet = new AnimatorSet();
					animatorSet.play(anim).with(anim1);
					animatorSet.setDuration(5000);
					animatorSet.start();
					break;
			}
		} else {
			drawCircle(canvas);
		}
	}

	private void drawCircle(Canvas canvas) {
		float x = currentPoint.x;
		float y = currentPoint.y;
		canvas.drawCircle(x, y, RADIUS, mPaint);
	}

	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
		mPaint.setColor(Color.parseColor(color));
//		invalidate();
	}

	@Override
	public void resetView(int type) {
		super.resetView(type);
		currentPoint = null;
	}
}
