package com.example.lenovo.mpplication.view.view1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class BaseView extends View {
	public static final int DrawPoint = 1;
	public static final int DrawLine = 2;
	public static final int DrawRect = 3;
	public static final int DrawRoundRect = 4;
	public static final int DrawOval = 5;
	public static final int DrawCircle = 6;
	public static final int centerOvalArc = 8;
	public static final int ovalArc = 9;
	public static final int centerCircleArc = 10;
	public static final int circleArc = 11;
	public static final int style = 12;
	public static final int tu = 13;

	private Paint mBlackPaint;
	private int mStyle;
	private Paint mBlueBaint;
	private Paint mRedBaint;

	/**
	 * 第一个构造方法时在代码中创建view的时候可以使用的
	 */
	public BaseView(Context context) {
		super(context);
		init();
	}

	/**
	 * 而第二个构造方法则是在xml中创建view的时候使用的。
	 */
	public BaseView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mBlackPaint = new Paint();
		mBlackPaint.setColor(Color.BLACK);
		mBlackPaint.setStyle(Paint.Style.FILL);
		mBlackPaint.setStrokeWidth(10f);
		mBlueBaint = new Paint();
		mBlueBaint.setColor(Color.BLUE);
		mBlueBaint.setStrokeWidth(10f);
		mRedBaint = new Paint();
		mRedBaint.setColor(Color.RED);
		mRedBaint.setStrokeWidth(10f);
	}

	/**
	 * UNSPECIFIED	00	默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
	 * EXACTLY	01	表示父控件已经确切的指定了子View的大小。
	 * AT_MOST	10	表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
	 * <p>
	 * MeasureSpec 的 getSize是获取数值， getMode是获取模式。
	 * <p>
	 * 如果对View的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec);
	 * 要调用 setMeasuredDimension( widthsize, heightsize); 这个函数。
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		int widthsize = MeasureSpec.getSize(widthMeasureSpec);
		int widthmode = MeasureSpec.getMode(widthMeasureSpec);
		int heightsize = MeasureSpec.getSize(heightMeasureSpec);
		int heightmode = MeasureSpec.getMode(heightMeasureSpec);
	}

	/**
	 * Q: 在测量完View并使用setMeasuredDimension函数之后View的大小基本上已经确定了，
	 * 那么为什么还要再次确定View的大小呢？
	 * A: 这是因为View的大小不仅由View本身控制，而且受父控件的影响，
	 * 所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

	}

	/**
	 * 确定布局的函数是onLayout，它用于确定子View的位置，在自定义ViewGroup中会用到，
	 * 他调用的是子View的layout函数。
	 * 在自定义ViewGroup中，onLayout一般是循环取出子View，
	 * 然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。child.layout(l, t, r, b);
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mStyle) {
			case DrawPoint:
				drawPoint(canvas);
				break;
			case DrawLine:
				drawLine(canvas);
				break;
			case DrawRect:
				drawRect(canvas);
				break;
			case DrawRoundRect:
				drawRoundRect(canvas);
				break;
			case DrawOval:
				drawRoundOval(canvas);
				break;
			case DrawCircle:
				drawRoundCircle(canvas);
			case centerOvalArc:
			case ovalArc:
			case centerCircleArc:
			case circleArc:
				drawRoundArc(canvas);
				break;
			case style:
				drawRoundStyle(canvas);
			case tu:
				drawRoundTu(canvas);
				break;
		}
	}

	/**
	 * save	保存当前画布状态
	 * restore	回滚到上一次保存的状态
	 * translate	相对于当前位置位移
	 * rotate	旋转
	 */
	private void drawRoundTu(Canvas canvas) {

	}

	/**
	 * STROKE                //描边
	 * FILL                  //填充
	 * FILL_AND_STROKE       //描边加填充
	 */
	private void drawRoundStyle(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(40);
		//描边
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(200,200,100,paint);
		//填充
		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(200,500,100,paint);
		//描边加填充
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		canvas.drawCircle(200,800,100,paint);
	}

	/**
	 * 绘制圆弧
	 * <p>
	 * // 第一种
	 * public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle,
	 * boolean useCenter, @NonNull Paint paint){}
	 * // 第二种
	 * public void drawArc(float left, float top, float right, float bottom, float startAngle,
	 * float sweepAngle, boolean useCenter, @NonNull Paint paint) {}
	 * <p>
	 * startAngle  // 开始角度
	 * sweepAngle  // 扫过角度
	 * useCenter   // 是否使用中心
	 */
	private void drawRoundArc(Canvas canvas) {
		// 绘制背景矩形
		RectF rectF = new RectF(100, 100, 800, 400);
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		switch (mStyle) {
			//椭圆，使用中心
			case centerOvalArc:
				canvas.drawRect(rectF, paint);
				canvas.drawArc(rectF, 0, 90, false, mBlueBaint);
				break;
			//椭圆，不使用中心
			case ovalArc:
				canvas.drawRect(rectF, paint);
				canvas.drawArc(rectF, 0, 90, true, mBlueBaint);
				break;
			//正圆，使用中心
			case centerCircleArc:
				rectF = new RectF(100, 100, 600, 600);
				canvas.drawRect(rectF, paint);
				canvas.drawArc(rectF, 0, 150, true, mBlueBaint);
				break;
			//正圆，不使用中心
			case circleArc:
//				rectF = new RectF(100,700,600,1200);
				rectF = new RectF(100, 100, 600, 600);
				canvas.drawRect(rectF, paint);
				canvas.drawArc(rectF, -0, 150, false, mBlueBaint);
				break;
		}
	}

	/**
	 * 绘制圆
	 * <p>
	 * 绘制圆形有四个参数，前两个是圆心坐标，第三个是半径，最后一个是画笔。
	 */
	private void drawRoundCircle(Canvas canvas) {
		// 绘制一个圆心坐标在(500,500)，半径为400 的圆。
		canvas.drawCircle(500, 500, 400, mBlueBaint);
	}

	/**
	 * 绘制椭圆
	 * <p>
	 * 实际上在rx为宽度的一半，ry为高度的一半时，刚好是一个椭圆，
	 * 通过上面我们分析的原理推算一下就能得到，而当rx大于宽度的一半，ry大于高度的一半时，
	 * 实际上是无法计算出圆弧的，所以drawRoundRect对大于该数值的参数进行了限制(修正)，
	 * 凡是大于一半的参数均按照一半来处理。
	 */
	private void drawRoundOval(Canvas canvas) {
		// 第一种
		RectF rectF = new RectF(100, 100, 800, 400);
		canvas.drawOval(rectF, mBlueBaint);
		// 第二种
		canvas.drawOval(100, 500, 800, 800, mRedBaint);

	}

	/**
	 * 绘制点
	 */
	void drawPoint(Canvas canvas) {
		canvas.drawPoint(200, 200, mBlueBaint);
		canvas.drawPoints(new float[]{
				500, 500,
				500, 600,
				500, 700}, mBlackPaint);
	}

	void ss() {
		Log.i("ddfgdfd","");
		Log.i("ddfgdfd","");
		Log.i("ddfgdfd","");
		Log.i("ddfgdfd","");
	}
	/**
	 * 绘制直线
	 */
	void drawLine(Canvas canvas) {
		// 在坐标(300,300)(500,600)之间绘制一条直线
		canvas.drawLine(300, 300, 500, 600, mBlueBaint);
		// 绘制一组线 每四数字(两个点的坐标)确定一条线
		canvas.drawLines(new float[]{
				100, 200, 200, 200,
				100, 300, 200, 300}, mBlackPaint);
	}

	/**
	 * 绘制矩形
	 * <p>
	 * Canvas提供了三种重载方法，
	 * 第一种就是提供四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形进行绘制。
	 * 其余两种是先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)，然后传递给Canvas绘制，
	 * 如下：
	 * <p>
	 * 两者最大的区别就是精度不同，Rect是int(整形)的，而RectF是float(单精度浮点型)的.
	 */
	void drawRect(Canvas canvas) {
		// 在坐标(300,300)(500,600)之间绘制一条直线

		canvas.drawRect(100, 100, 200, 200, mBlueBaint);
		// 绘制一组线 每四数字(两个点的坐标)确定一条线
		Rect rect = new Rect(200, 100, 300, 200);
		canvas.drawRect(rect, mRedBaint);
		RectF rectF = new RectF(100, 200, 300, 300);
		canvas.drawRect(rectF, mBlackPaint);
	}

	/**
	 * 圆角半径
	 * <p>
	 * drawRoundRect(RectF rect, float rx, float ry, Paint paint)
	 * <p>
	 * rx,ry是椭圆半径
	 */
	void drawRoundRect(Canvas canvas) {
		//第一种
		RectF rectF = new RectF(100, 100, 800, 400);
		canvas.drawRoundRect(rectF, 30, 30, mBlueBaint);
		canvas.drawRoundRect(100, 500, 800, 800, 30, 30, mRedBaint);
	}


	public void resetView(int style) {
		mStyle = style;
		invalidate();
	}

}
