package com.example.lenovo.mpplication.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by fan on 2018/9/25.
 */
public class MyButton extends android.support.v7.widget.AppCompatButton {
	private static final String TAG = MyButton.class.getSimpleName();

	public MyButton(Context context) {
		super(context);
	}

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.e(TAG, "dispatchTouchEvent: return="+ super.dispatchTouchEvent(event));
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e(TAG, "onTouchEvent: return="+ super.onTouchEvent(event));
		return super.onTouchEvent(event);
	}
}
