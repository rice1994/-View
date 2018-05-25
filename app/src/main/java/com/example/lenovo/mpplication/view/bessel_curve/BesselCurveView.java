package com.example.lenovo.mpplication.view.bessel_curve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/5/24.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 * <p>
 * 贝塞尔曲线的运用是十分广泛的，
 * 可以说贝塞尔曲线奠定了计算机绘图的基础(因为它可以将任何复杂的图形用精确的数学语言进行描述
 */
public class BesselCurveView extends BaseView {
	public final static int curve1 = 1;
	public final static int curve2 = 2;
	public final static int control1 = 3;
	public final static int control2 = 33;
	public final static int curve_demo = 4;
	private Point start;
	private Point end;
	private PointF control;
	private PointF control_1;
	private PointF control_2;

	public BesselCurveView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		start = new Point();
		end = new Point();
		control = new PointF();
		control_1 = new PointF();
		control_2 = new PointF();
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int centerX = w / 2;
		int centerY = h / 2;

		start.x = centerX - 200;
		end.x = centerX + 200;
		start.y = centerY;
		end.y = centerY;
		control.x = centerX;
		control.y = centerY - 200;

		control_1.x = centerX - 150;
		control_1.y = centerY - 200;
		control_2.x = centerX + 150;
		control_2.y = centerY - 200;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mType) {
			case curve1:
				curve1(canvas);
				break;
			case curve2:
				curve2(canvas);
				break;
			case control1:
				curve3(canvas);
				break;
			case control2:
				curve3(canvas);
				break;
			case curve_demo:
				curve_demo(canvas);
				break;
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (mType) {
			case curve2:
				control.x = event.getX();
				control.y = event.getY();
				invalidate();
				return true;
			case control1:
			case control2:
				if(mType == control1){
					control_1.x = event.getX();
					control_1.y = event.getY();
				}else {
					control_2.x = event.getX();
					control_2.y = event.getY();
				}
				invalidate();
				return true;
			case curve_demo:
				break;
		}
		return super.onTouchEvent(event);
	}


	private void curve_demo(Canvas canvas) {

	}

	private void curve3(Canvas canvas) {

		canvas.drawPoint(start.x, start.y, mStrokeGrayPaint);
		canvas.drawPoint(end.x, end.y, mStrokeGrayPaint);
		canvas.drawPoint(control_1.x, control_1.y, mStrokeGrayPaint);
		canvas.drawPoint(control_2.x, control_2.y, mStrokeGrayPaint);

		canvas.drawLine(start.x, start.y, control_1.x, control_1.y, mStrokeGrayPaint);
		canvas.drawLine(end.x, end.y, control_2.x, control_2.y, mStrokeGrayPaint);
		canvas.drawLine(control_1.x, control_1.y, control_2.x, control_2.y, mStrokeGrayPaint);

		mPath.reset();
		mPath.moveTo(start.x, start.y);
		mPath.cubicTo(control_1.x, control_1.y, control_2.x, control_2.y, end.x, end.y);
		canvas.drawPath(mPath, mStrokeRedPaint);
	}


	/**
	 *
	 */
	private void curve2(Canvas canvas) {

		canvas.drawPoint(start.x, start.y, mStrokeGrayPaint);
		canvas.drawPoint(end.x, end.y, mStrokeGrayPaint);
		canvas.drawPoint(control.x, control.y, mStrokeGrayPaint);

		canvas.drawLine(start.x, start.y, control.x, control.y, mStrokeGrayPaint);
		canvas.drawLine(end.x, end.y, control.x, control.y, mStrokeGrayPaint);

		mPath.reset();
		mPath.moveTo(start.x, start.y);
		mPath.quadTo(control.x, control.y, end.x, end.y);
		canvas.drawPath(mPath, mStrokeRedPaint);
	}

	private void curve1(Canvas canvas) {
		canvas.drawLine(10, 10, 300, 500, mStrokeBluePaint);
	}
}
