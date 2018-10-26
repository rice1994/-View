package com.example.lenovo.mpplication.event;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by fan on 2018/9/25.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class MyLinearLayout extends LinearLayout {
	private static final String TAG = MyLinearLayout.class.getSimpleName();

	public MyLinearLayout(Context context) {
		super(context);
	}

	public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.e(TAG, "dispatchTouchEvent: return="+super.dispatchTouchEvent(event));
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e(TAG, "onTouchEvent: return="+super.onTouchEvent(event));
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.e(TAG, "onInterceptTouchEvent: return="+super.onInterceptTouchEvent(ev));
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
		super.requestDisallowInterceptTouchEvent(disallowIntercept);
	}

}
