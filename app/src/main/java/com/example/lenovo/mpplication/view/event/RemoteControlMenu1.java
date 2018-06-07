package com.example.lenovo.mpplication.view.event;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/6/7.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class RemoteControlMenu1 extends BaseView {
	private Region[] regions;
	private Path[] paths;
	int CENTER = 0;
	int UP = 1;
	int RIGHT = 2;
	int DOWN = 3;
	int LEFT = 4;
	int mDefauColor = 0xFF4E5268;
	int mTouchedColor = 0xFFDF9C81;
	private Paint mDeafultPaint;
	private int touchFlag = -1;
	private Matrix mMatrix;
	private MenuListener mListener;
	private float[] pts;

	public RemoteControlMenu1(Context context) {
		super(context);
	}

	public RemoteControlMenu1(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mDeafultPaint = new Paint();
		mDeafultPaint.setColor(mDefauColor);
		mDeafultPaint.setAntiAlias(true);
		mMatrix = new Matrix();
		regions = new Region[5];
		paths = new Path[5];
		Region globalRegion = new Region(-mWidth, -mHeight, mWidth, mHeight);
		int minWidth = mWidth > mHeight ? mHeight : mWidth;
		minWidth *= 0.8;

		int br = minWidth / 2;
		RectF bigCircle = new RectF(-br, -br, br, br);

		int sr = minWidth / 4;
		RectF smallCircle = new RectF(-sr, -sr, sr, sr);
		float bigSweepAngle = 84;
		float smallSweepAngle = -80;
		paths[CENTER] = new Path();

		int addArcStartAngle = -40;
		int arcToStartAngle = addArcStartAngle + 80;
		for (int i = 0; i < paths.length; i++) {
			if (i == CENTER)
				paths[CENTER].addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
			else {
				paths[i] = new Path();
				paths[i].addArc(bigCircle, addArcStartAngle, bigSweepAngle);
				paths[i].arcTo(smallCircle, arcToStartAngle, smallSweepAngle);
				paths[i].close();
				addArcStartAngle = arcToStartAngle + 10;
				arcToStartAngle = addArcStartAngle + 80;
			}
			regions[i] = new Region();
			regions[i].setPath(paths[i], globalRegion);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		pts = new float[2];
		pts[0] = event.getRawX();
		pts[1] = event.getRawY();

		mMatrix.mapPoints(pts);

		int x = (int) pts[0];
		int y = (int) pts[1];
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touchFlag = getTouchedPath(x, y);
				break;
			case MotionEvent.ACTION_MOVE:
				touchFlag = getTouchedPath(x, y);
				break;
			case MotionEvent.ACTION_UP:
				if (mListener != null)
					mListener.onCliched(getTouchedPath(x, y));
				touchFlag = -1;

				break;
		}
		invalidate();
		return true;

	}

	// 获取当前触摸点在哪个区域
	int getTouchedPath(int x, int y) {
		for (int i = 0; i < regions.length; i++) {
			if (regions[i].contains(x, y))
				return i;
		}
		return -1;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(mWidth / 2, mHeight / 2);
//		if(pts != null)
////		canvas.drawCircle(pts[0],pts[1],50,mBluePaint);
		// 获取测量矩阵(逆矩阵)
		if (mMatrix.isIdentity()) {
			canvas.getMatrix().invert(mMatrix);
		}

		for (int i = 0; i < paths.length; i++) {
			canvas.drawPath(paths[i], mDeafultPaint);
		}
		if (touchFlag != -1) {
			// 绘制触摸区域颜色
			mDeafultPaint.setColor(mTouchedColor);
			canvas.drawPath(paths[touchFlag], mDeafultPaint);
			mDeafultPaint.setColor(mDefauColor);
		}
	}

	public void setListener(MenuListener listener) {
		mListener = listener;
	}

	// 点击事件监听器
	public interface MenuListener {
		void onCliched(int style);
	}
}
