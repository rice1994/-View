package com.example.lenovo.mpplication.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lenovo.mpplication.R;

public class EventActivity extends AppCompatActivity {

	private static final String TAG = EventActivity.class.getSimpleName();
	@BindView(R.id.btn)
	MyButton mBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		ButterKnife.bind(this);
		mBtn.getParent().requestDisallowInterceptTouchEvent(false);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.e(TAG, "dispatchTouchEvent: return=" + super.dispatchTouchEvent(ev));
		return super.dispatchTouchEvent(ev);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e(TAG, "onTouchEvent: return=" + super.onTouchEvent(event));
		return super.onTouchEvent(event);
	}

}
