package com.example.lenovo.mpplication.view.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fan on 2018/5/4.
 */
public class BaseView extends View {

	public Path mPath;
	public Paint mStrokeGrayPaint;
	public Paint mStrokeYellowPaint;
	public Paint mStrokeGreenPaint;
	public Paint mStrokeRedPaint;
	public Paint mStrokeBluePaint;
	public Paint mBlackPaint;
	public Paint mBluePaint;
	public int mType;
	public int mWidth;
	public int mHeight;

	public BaseView(Context context) {
		super(context);
	}

	public BaseView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mPath = new Path();
		mBlackPaint = new Paint();
		mBlackPaint.setColor(Color.BLACK);
		mBlackPaint.setStrokeWidth(5);
		mBlackPaint.setStyle(Paint.Style.FILL);
		mBlackPaint.setStyle(Paint.Style.FILL);
		mBlackPaint.setTextSize(50);              // 设置字体大小

		mBluePaint = new Paint();
		mBluePaint.setTextSize(50);
		mBluePaint.setColor(Color.BLUE);
		mBluePaint.setStrokeWidth(5);
		mBluePaint.setStyle(Paint.Style.FILL);
		mBluePaint.setTextSize(50);              // 设置字体大小
		mStrokeBluePaint = new Paint();             // 创建画笔
		mStrokeBluePaint.setColor(Color.BLUE);           // 画笔颜色 - 蓝色
		mStrokeBluePaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
		mStrokeBluePaint.setStrokeWidth(10);              // 边框宽度 - 10
		mStrokeRedPaint = new Paint();             // 创建画笔
		mStrokeRedPaint.setColor(Color.RED);
		mStrokeRedPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
		mStrokeRedPaint.setStrokeWidth(10);              // 边框宽度 - 10
		mStrokeYellowPaint = new Paint();             // 创建画笔
		mStrokeYellowPaint.setColor(Color.YELLOW);
		mStrokeYellowPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
		mStrokeYellowPaint.setStrokeWidth(10);              // 边框宽度 - 10
		mStrokeGreenPaint = new Paint();             // 创建画笔
		mStrokeGreenPaint.setColor(Color.GREEN);
		mStrokeGreenPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
		mStrokeGreenPaint.setStrokeWidth(10);              // 边框宽度 - 10
		mStrokeGrayPaint = new Paint();             // 创建画笔
		mStrokeGrayPaint.setColor(Color.GRAY);
		mStrokeGrayPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
		mStrokeGrayPaint.setStrokeWidth(10);              // 边框宽度 - 10
	}

	public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mHeight = h;
		mWidth = w;
	}

	public void resetView(int type) {
		mType = type;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
