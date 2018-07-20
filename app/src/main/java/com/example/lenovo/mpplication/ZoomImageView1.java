package com.example.lenovo.mpplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;

/**
 * Created by fan on 2018/7/6.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class ZoomImageView1 extends AppCompatImageView implements
		ViewTreeObserver.OnGlobalLayoutListener, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {
	private static final String TAG = ZoomImageView1.class.getSimpleName();
	private final ScaleGestureDetector mScaleGestureDetector;
	private final int mTouchSlop;
	private Matrix mScaleMatrix;
	private GestureDetector mGestureDetector;
	//双击放大大小
	private float mMidScale;
	//标准大小
	private float mInitScale;
	private boolean mOnce = true;
	private float mMaxScale;
	private RectF mMatrixRectF;
	private float mLastX;
	private boolean isFirst=true;
	private float mLastY;
	private boolean isCheckTopAndBottom;
	private boolean isCheckLeftAndRight;
	private int mLastPointCount;
	private boolean isCanDrag;

	public ZoomImageView1(Context context) {
		this(context, null);
	}

	public ZoomImageView1(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ZoomImageView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mScaleMatrix = new Matrix();
		setScaleType(ScaleType.MATRIX);
		setOnTouchListener(this);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mScaleGestureDetector = new ScaleGestureDetector(context, this);
		mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				float x = e.getRawX();
				float y = e.getRawY();
				postDelayed(new AutoScaleRunnable(x, y), 16);
				return true;
			}
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.e(TAG, "onMeasure: ");

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.e(TAG, "onSizeChanged: ");
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		Log.e(TAG, "onLayout: ");

	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);		Log.e(TAG, "onDraw: ");

	}


	private float getScale() {
		float[] values = new float[9];
		mScaleMatrix.getValues(values);
		return values[android.graphics.Matrix.MSCALE_X];
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeOnGlobalLayoutListener(this);
	}

	/**
	 * ---------------
	 * Constructor--onFinishInflate--onMeasure--onSizeChanged--onLayout--addOnGlobalLayoutListener
	 * Constructor：构造函数
	 * onFinishInflate：布局初始化
	 * onMeasure：测量控件宽高/调用requestLayout回调
	 * onSizeChanged：当控件宽高                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           发生变化时回调
	 * onLayout：当确定控件的位置时回调
	 * addOnGlobalLayoutListener：当View的位置确定完后会回调改监听方法，只要onLayout方法调用了,那么addOnGlobalLayoutListener的监听器就会监听到
	 */
	@Override
	public void onGlobalLayout() {		Log.e(TAG, "onGlobalLayout: ");

		if (mOnce) {
			int width = getWidth();
			int height = getHeight();
			Drawable drawable = getDrawable();
			if (drawable == null) {
				return;
			}
			float scale = 1.0f;
			int dw = drawable.getIntrinsicWidth();
			int dh = drawable.getIntrinsicHeight();
			if (dh > height && dw < width)
				scale = height * 1.0f / dh;
			else if (dh < height && dw > width)
				scale = width * 1.0f / dw;
			else if ((dw > width && dh > height) || (dw < width && dh < height))
				scale = Math.min(height * 1.0f / dh, width * 1.0f / dw);
			mInitScale = scale;
			mMidScale = mInitScale * 2;
			mMaxScale = mInitScale * 4;
			int dx = getWidth() / 2 - dw / 2;
			int dy = getHeight() / 2 - dh / 2;
			mScaleMatrix.postTranslate(dx, dy);
			mScaleMatrix.postScale(mInitScale, mInitScale, getWidth() / 2, getHeight() / 2);
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
			mOnce = false;
		}

	}

	/**
	 * 判断是否能触发move
	 *
	 * @param dx
	 * @param dy
	 * @return
	 */
	private boolean isMoveAction(float dx, float dy) {
		return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
	}

	/**
	 * 在缩放的时候进行边界的控制，已经我们的位置的控制
	 */
	private void checkBorderAndCenterWhenScale() {
		RectF rect = getMatrixRectF();
		float delteX = 0;
		float delteY = 0;

		int width = getWidth();
		int height = getHeight();
		if (rect.width() >= width) {
			if (rect.left > 0)
				delteX = -rect.left;
			if (rect.right < width)
				delteX = width - rect.right;
		}
		if (rect.height() >= height) {
			if (rect.top > 0)
				delteY = -rect.top;
			if (rect.bottom < height)
				delteY = height - rect.bottom;
		}
		//如果宽度或者高度小于控件的宽或者高，则让其居中
		if (rect.width() < width) {
			delteX = width * 1.0f / 2f - rect.right + rect.width() * 1.0f / 2f;
		}
		if (rect.height() < height) {
			delteY = height * 1.0f / 2f - rect.bottom + rect.height() * 1.0f / 2f;
		}

		mScaleMatrix.postTranslate(delteX, delteY);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event))
			return true;
		mScaleGestureDetector.onTouchEvent(event);
		float x = 0;
		float y = 0;
		int pointerCount = event.getPointerCount();
		for (int i = 0; i < pointerCount; i++) {
			x += event.getX(i);
			y += event.getY(i);
		}
		x /= pointerCount;
		y /= pointerCount;
		if(mLastPointCount != pointerCount){
			mLastPointCount = pointerCount;
			mLastX = x;
			mLastY = y;
			isCanDrag = false;
		}
		RectF rectF = getMatrixRectF();
		if(rectF.height()>getHeight() || rectF.width()>getWidth()){
			if(getParent() instanceof ViewGroup){
				getParent().requestDisallowInterceptTouchEvent(true);
			}
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				float dx = x - mLastX;
				float dy = y - mLastY;
				if (!isCanDrag) {
					isCanDrag = isMoveAction(dx, dy);
				}
				if (isCanDrag) {

					if (getDrawable() != null) {
						isCheckLeftAndRight = isCheckTopAndBottom = true;
						//如果宽度小于控件的宽度，不允许横向移动
						if (rectF.width() < getWidth()) {
							isCheckLeftAndRight = false;
							dx = 0;
						}
						//如果高度小于控件的宽度，不允许纵向移动
						if (rectF.height() < getHeight()) {
							isCheckTopAndBottom = false;
							dy = 0;
						}
						mScaleMatrix.postTranslate(dx, dy);
						checkBorderWhenTranslate();
						setImageMatrix(mScaleMatrix);
					}
				}
				mLastX=x;
				mLastY=y;
				break;
			case MotionEvent.ACTION_UP:
				mLastPointCount = 0;
				break;
			case MotionEvent.ACTION_CANCEL:
				mLastPointCount = 0;
				break;
		}

		return true;
	}

	/**
	 * 当移动时，进行边界检查
	 */
	private void checkBorderWhenTranslate() {
		RectF rect = getMatrixRectF();
		float deltaX = 0;
		float deltaY = 0;
		int width = getWidth();
		int height = getHeight();
		if (isCheckTopAndBottom && rect.top > 0) {
			deltaY = -rect.top;
		}
		if (isCheckTopAndBottom && rect.bottom < height) {
			deltaY = height - rect.bottom;
		}
		if (isCheckLeftAndRight && rect.left > 0) {
			deltaX = -rect.left;
		}
		if (isCheckLeftAndRight && rect.right < width) {
			deltaX = width - rect.right;
		}
		mScaleMatrix.postTranslate(deltaX,deltaY);

	}

	public RectF getMatrixRectF() {
		Matrix matrix = mScaleMatrix;
		Drawable drawable = getDrawable();
		RectF rectF = new RectF(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		if (drawable != null) {
			matrix.mapRect(rectF);
		}
		return rectF;
	}

	/**
	 * @return 是否被处理
	 */
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float scale = getScale();
		float scaleFactor = detector.getScaleFactor();
		Log.e(TAG, "onScale: scaleFactor=" + scaleFactor);
		if (getDrawable() == null) {
			return true;
		}
		//缩放范围的控制
		//scaleFactorM<1.0f：缩小，scaleFactorM>1.0f：放大
		if ((scaleFactor < 1.0f && scale > mInitScale) || (scaleFactor > 1.0f && scale < mMaxScale)) {
			//缩小，不能小于最小值
			if (scale * scaleFactor < mInitScale)
				scaleFactor = mInitScale / scale;
				//放大，不能大于最大值
			else if (scale * scaleFactor > mMaxScale)
				scaleFactor = mMaxScale / scale;
			mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
		}
		return false;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {

	}

	private class AutoScaleRunnable implements Runnable {
		private final float mScale;
		private float mTargetScale;
		private final float x;
		private final float y;
		private final float BIGGER = 1.07f;
		private final float SMALL = 0.93f;
		private float tmpScale;

		public AutoScaleRunnable(float x, float y) {
			mScale = getScale();
			this.x = x;
			this.y = y;
			if (mScale < mMidScale) {
				mTargetScale = mMidScale;
				tmpScale = BIGGER;
			} else {
				mTargetScale = mInitScale;
				tmpScale = SMALL;
			}
		}

		@Override
		public void run() {
			mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
			float currentScale = getScale();
			//正在放大或者缩小
			if ((tmpScale > 1.0f && currentScale < mTargetScale) ||
					(tmpScale < 1.0f && currentScale > mTargetScale)) {
				postDelayed(this, 16);
			} else {
				float scale = mTargetScale / currentScale;
				mScaleMatrix.postScale(scale, scale, x, y);
				checkBorderAndCenterWhenScale();
				setImageMatrix(mScaleMatrix);
			}
		}
	}
}
