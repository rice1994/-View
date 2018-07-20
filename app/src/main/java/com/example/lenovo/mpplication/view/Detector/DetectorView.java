package com.example.lenovo.mpplication.view.Detector;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/6/7.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class DetectorView extends BaseView{
	private static final String TAG = DetectorView.class.getSimpleName();
	private  GestureDetector gestureDetector;
	private  ScaleGestureDetector scaleGestureDetector;

	public DetectorView(Context context) {
		super(context);
	}

	public DetectorView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		 gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
			/**
			 * OnGestureListener的监听事件
			 */
			@Override
			public boolean onDown(MotionEvent e) {
				Log.i(TAG, "onDown: 按下");
				return super.onDown(e);
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				Log.i(TAG, "onFling: 抛掷");
				return super.onFling(e1, e2, velocityX, velocityY);
			}

			@Override
			public void onLongPress(MotionEvent e) {
				Log.i(TAG, "onLongPress: 长按");
				super.onLongPress(e);
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				Log.i(TAG, "onScroll: 滚动");
				return super.onScroll(e1, e2, distanceX, distanceY);
			}

			@Override
			public void onShowPress(MotionEvent e) {
				Log.i(TAG, "onShowPress: 按住");
				super.onShowPress(e);
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				Log.i(TAG, "onSingleTapUp: 抬起");
				return super.onSingleTapUp(e);
			}

			/**
			 * OnDoubleTapListener
			 */
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				Log.i(TAG, "onDoubleTap: ");
				return super.onDoubleTap(e);
			}

			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				Log.i(TAG, "onDoubleTapEvent: ");
				return super.onDoubleTapEvent(e);
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				Log.i(TAG, "onSingleTapConfirmed: ");
				return super.onSingleTapConfirmed(e);
			}
		});
		 scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				Log.i(TAG, "onScale: ");
				return false;
			}

			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				Log.i(TAG, "onScaleBegin: ");
				return false;
			}

			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
				Log.i(TAG, "onScaleEnd: ");
			}
		});
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(gestureDetector.onTouchEvent(event))
			return true;
		if (scaleGestureDetector.onTouchEvent(event))
			return true;
		return super.onTouchEvent(event);
	}
}
