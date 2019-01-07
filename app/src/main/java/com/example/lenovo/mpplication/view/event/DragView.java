package com.example.lenovo.mpplication.view.event;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/6/7.
 */
public class DragView extends BaseView {
	String TAG = "Gcs";

	Bitmap mBitmap;         // 图片
	RectF mBitmapRectF;     // 图片所在区域
	Matrix mBitmapMatrix;   // 控制图片的 matrix

	boolean canDrag = false;
	PointF lastPoint = new PointF(0, 0);

	public DragView(Context context) {
		this(context, null);
	}

	public DragView(Context context, AttributeSet attrs) {
		super(context, attrs);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.outWidth = 960/2;
		options.outHeight = 800/2;

		mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.arrow, options);
		mBitmapRectF = new RectF(0,0,mBitmap.getWidth(), mBitmap.getHeight());
		mBitmapMatrix = new Matrix();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				// ▼ 判断是否是第一个手指 && 是否包含在图片区域内

				Log.i(TAG, "event --getActionIndex----"+event.getActionIndex());
				Log.i(TAG, "event --getPointerId("+event.getActionIndex()+")----"+event.getPointerId(event.getActionIndex()));
				Log.i(TAG, "event --contains--"+ mBitmapRectF.contains((int)event.getX(), (int)event.getY()));
				Log.i(TAG, "event --contains-getX-"+event.getX());
				Log.i(TAG, "event --contains-getY-"+event.getY());
				Log.i(TAG, "event --contains-mBitmapRectF-left-"+mBitmapRectF.left);
				Log.i(TAG, "event --contains-mBitmapRectF-right-"+mBitmapRectF.right);
				Log.i(TAG, "event --contains-mBitmapRectF-top-"+mBitmapRectF.top);
				Log.i(TAG, "event --contains-mBitmapRectF-bottom-"+mBitmapRectF.bottom);
				if (event.getPointerId(event.getActionIndex()) == 0 && mBitmapRectF.contains((int)event.getX(), (int)event.getY())){
					canDrag = true;
					lastPoint.set(event.getX(), event.getY());
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				// ▼ 判断是否是第一个手指
				if (event.getPointerId(event.getActionIndex()) == 0){
					canDrag = false;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				// 如果存在第一个手指，且这个手指的落点在图片区域内
				if (canDrag) {
					// ▼ 注意 getX 和 getY
					int index = event.findPointerIndex(0);
					// Log.i(TAG, "index="+index);
					mBitmapMatrix.postTranslate(event.getX(index)-lastPoint.x, event.getY(index)-lastPoint.y);
					lastPoint.set(event.getX(index), event.getY(index));
					// 更新图片区域
					mBitmapRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
					mBitmapMatrix.mapRect(mBitmapRectF);

					invalidate();
				}
				break;
		}

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, mBitmapMatrix, new Paint());
	}
}
