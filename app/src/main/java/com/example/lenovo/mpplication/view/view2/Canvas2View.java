package com.example.lenovo.mpplication.view.view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Canvas之画布操作
 * <p>
 * 合理的使用画布操作可以帮助你用更容易理解的方式创作你想要的效果，这也是画布操作存在的原因。
 * PS: 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响。
 */
public class Canvas2View extends View {
	public static final int translate = 0;
	public static final int translate1 = 1;
	public static final int scale1 = 2;
	public static final int scale2 = 3;
	public static final int scale3 = 4;
	public static final int rotate = 5;
	public static final int rotate1 = 6;
	public static final int rotate2 = 7;
	public static final int skew_45x = 8;
	public static final int skew_45y = 9;
	public static final int skew_45xy = 10;
	public static final int save_and_restore = 11;
	private int with;
	private int height;
	private int mStyle;
	private Paint mBlackPaint;
	private Paint mBluePaint;
	private Paint mRedPaint;
	private Paint mGrayPaint;

	public Canvas2View(Context context) {
		super(context);
	}

	public Canvas2View(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initPain();
	}

	private void initPain() {
		mBlackPaint = new Paint();
		mBlackPaint.setColor(Color.BLACK);
		mBlackPaint.setStyle(Paint.Style.STROKE);
		mBlackPaint.setStrokeWidth(10);

		mBluePaint = new Paint();
		mBluePaint.setColor(Color.BLUE);
		mBluePaint.setStyle(Paint.Style.STROKE);
		mBluePaint.setStrokeWidth(10);

		mRedPaint = new Paint();
		mRedPaint.setColor(Color.RED);
		mRedPaint.setStyle(Paint.Style.STROKE);
		mRedPaint.setStrokeWidth(10);

		mGrayPaint = new Paint();
		mGrayPaint.setColor(Color.GRAY);
		mGrayPaint.setStyle(Paint.Style.FILL);


	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		with = w;
		height = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mStyle) {
			case translate:
				translate(canvas);
				break;
			case translate1:
				translate1(canvas);
				break;
			case scale1:
				scale1(canvas);
				break;
			case scale2:
				scale2(canvas);
				break;
			case scale3:
				scale3(canvas);
				break;
			case rotate:
				rotate(canvas);
				break;
			case rotate1:
				rotate1(canvas);
				break;
			case rotate2:
				rotate2(canvas);
				break;
			case skew_45x:
				skew45x(canvas);
				break;
			case skew_45y:
				skew45y(canvas);
				break;
			case skew_45xy:
				skew45xy(canvas);
				break;
			case save_and_restore:
				saveAndRestore(canvas);
				break;
		}
	}

	private void translate1(Canvas canvas) {
		// 刻度尺高度
		int DIVIDING_RULE_HEIGHT = 70;
		// 距离左间
		int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 10;
		// 第一条线距离边框距离
		int FIRST_LINE_MARGIN = 50;
		// 打算绘制的厘米数
		int DEFAULT_COUNT = 30;
		mRedPaint.setStrokeWidth(3);
		mBlackPaint.setStrokeWidth(3);
		mBluePaint.setStrokeWidth(3);
		int count = (with-FIRST_LINE_MARGIN)/9;
		int ruleHeigh;
		Paint paint;
		Rect rect = new Rect(0,0,with,DIVIDING_RULE_HEIGHT);
		canvas.drawRect(rect,mGrayPaint);
		canvas.translate(FIRST_LINE_MARGIN,0);
		for(int i=0;i<count;i++){
			if(i%10==0){
				ruleHeigh = 50;
				paint = mRedPaint;
			}else if (i%5==0){
				ruleHeigh = 40;
				paint = mBluePaint;
			}else {
				ruleHeigh = 30;
				paint = mBlackPaint;
			}
			canvas.drawLine(0,DIVIDING_RULE_HEIGHT,
					0,DIVIDING_RULE_HEIGHT-ruleHeigh,paint);
			canvas.translate(DEFAULT_COUNT,0);
		}
	}

	private void rotate2(Canvas canvas) {
		canvas.translate(with / 2, height / 2);
		canvas.drawCircle(0, 0, 400, mBlackPaint);
		for (int i = 0; i < 12 * 5; i++) {
			//canvas.save();
			//canvas.rotate(6 * i);
			canvas.rotate(6);
			if (i % 5 == 0) {
				canvas.drawLine(0, -400, 0, -300, mRedPaint);
			} else {
				canvas.drawLine(0, -400, 0, -330, mBluePaint);
			}
			//canvas.restore();
		}
	}

	/**
	 * public int save ()  保存全部状态
	 * public int save (int saveFlags)  根据saveFlags参数保存一部分状态
	 * <p>
	 * SaveFlags:
	 * ALL_SAVE_FLAG	             默认，保存全部状态
	 * CLIP_SAVE_FLAG	            保存剪辑区
	 * CLIP_TO_LAYER_SAVE_FLAG   	剪裁区作为图层保存
	 * FULL_COLOR_LAYER_SAVE_FLAG	保存图层的全部色彩通道
	 * HAS_ALPHA_LAYER_SAVE_FLAG	保存图层的alpha(不透明度)通道
	 * MATRIX_SAVE_FLAG	            保存Matrix信息( translate, rotate, scale, skew)
	 * <p>
	 * saveLayerXxx
	 * 注意：saveLayerXxx方法会让你花费更多的时间去渲染图像(图层多了相互之间叠加会导致计算量成倍增长)，使用前请谨慎，如果可能，尽量避免使用。
	 * 使用saveLayerXxx方法，也会将图层状态也放入状态栈中，同样使用restore方法进行恢复。
	 * <p>
	 * // 无图层alpha(不透明度)通道
	 * public int saveLayer (RectF bounds, Paint paint)
	 * public int saveLayer (RectF bounds, Paint paint, int saveFlags)
	 * public int saveLayer (float left, float top, float right, float bottom, Paint paint)
	 * public int saveLayer (float left, float top, float right, float bottom, Paint paint, int saveFlags)
	 * <p>
	 * // 有图层alpha(不透明度)通道
	 * public int saveLayerAlpha (RectF bounds, int alpha)
	 * public int saveLayerAlpha (RectF bounds, int alpha, int saveFlags)
	 * public int saveLayerAlpha (float left, float top, float right, float bottom, int alpha)
	 * public int saveLayerAlpha (float left, float top, float right, float bottom, int alpha, int saveFlags)
	 * <p>
	 * restore        状态回滚，就是从栈顶取出一个状态然后根据内容进行恢复。
	 * restoreToCount  弹出指定位置以及以上所有状态，并根据指定位置状态进行恢复。
	 * getSaveCount   获取保存的次数，即状态栈中保存状态的数量,不过请注意，该函数的最小返回值为1，即使弹出了所有的状态，返回值依旧为1，代表默认状态。
	 * <p>
	 * 常用格式
	 * save();      //保存状态
	 * ...          //具体操作
	 * restore();   //回滚到之前的状态
	 */
	private void saveAndRestore(Canvas canvas) {
		canvas.save();
		skew45xy(canvas);
		canvas.restore();
		skew45x(canvas);
	}

	/**
	 * 错切也是可叠加的，不过请注意，调用次序不同绘制结果也会不同
	 */
	private void skew45xy(Canvas canvas) {
		canvas.drawLines(new float[]{0, 0, with, 0, 0, 0, 0, height}, mBlackPaint);
		Rect rect = new Rect(0, 0, 300, 300);
		canvas.drawRect(rect, mBluePaint);
		canvas.skew(1, 0);
		canvas.skew(0, 1);
		canvas.drawRect(rect, mRedPaint);
	}

	private void skew45y(Canvas canvas) {
		canvas.drawLines(new float[]{0, 0, with, 0, 0, 0, 0, height}, mBlackPaint);
		Rect rect = new Rect(0, 0, 300, 300);
		canvas.drawRect(rect, mBluePaint);
		canvas.skew(0, 1);
		canvas.drawRect(rect, mRedPaint);
	}

	/**
	 * public void skew (float sx, float sy)
	 * 参数含义：
	 * float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
	 * float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
	 * <p>
	 * 变换后:
	 * X = x + sx * y
	 * Y = sy * x + y
	 */
	private void skew45x(Canvas canvas) {
		canvas.drawLines(new float[]{0, 0, with, 0, 0, 0, 0, height}, mBlackPaint);
		//canvas.translate(with / 2, height / 2);
		//canvas.drawLines(new float[]{-with / 2, 0, with / 2, 0, 0, -height / 2, 0, height / 2}, mBlackPaint);
		Rect rect = new Rect(0, 0, 300, 300);
		canvas.drawRect(rect, mBluePaint);
		canvas.skew(1, 0);
		canvas.drawRect(rect, mRedPaint);
	}

	private void rotate1(Canvas canvas) {
		canvas.translate(with / 2, height / 2);
		canvas.drawCircle(0, 0, 400, mBlackPaint);
		canvas.scale(0.9f, 0.9f);
		canvas.drawCircle(0, 0, 400, mBluePaint);

		canvas.scale(1 / 0.9f, 1 / 0.9f);
		for (int i = 0; i <= 360; i++) {
			canvas.drawLine(0, 360, 0, 400, mRedPaint);
			canvas.rotate(10);
		}
	}

	/**
	 * public void rotate (float degrees)
	 * public final void rotate (float degrees, float px, float py)
	 * <p>
	 * 和缩放一样，第二种方法多出来的两个参数依旧是控制旋转中心点的。
	 * 默认的旋转中心依旧是坐标原点：
	 */
	private void rotate(Canvas canvas) {
		canvas.translate(with / 2, height / 2);
		RectF rectF = new RectF(0, -400, 400, 0);
		canvas.drawRect(rectF, mBlackPaint);
		canvas.rotate(180);
		canvas.drawRect(rectF, mBluePaint);

		canvas.rotate(-180);
		canvas.rotate(180, 200, 0);
		canvas.drawRect(rectF, mRedPaint);
	}

	private void scale3(Canvas canvas) {
		canvas.translate(with / 2, height / 2);
		RectF rectF = new RectF(-400, -400, 400, 400);
		for (int i = 0; i < 10; i++) {
			canvas.scale(0.9f, 0.9f);
			canvas.drawRect(rectF, mBluePaint);
		}
	}

	/**
	 * 放缩是全部一起放缩，如：包括width
	 * <p>
	 * public void scale (float sx, float sy)
	 * public final void scale (float sx, float sy, float px, float py)
	 * 这两个方法中前两个参数是相同的分别为x轴和y轴的缩放比例。而第二种方法比前一种多了两个参数，
	 * 用来控制缩放中心位置的。
	 * <p>
	 * 缩放比例(sx,sy)取值范围详解：
	 * [-∞, -1)	    先根据缩放中心放大n倍，再根据中心轴进行翻转
	 * -1	        根据缩放中心轴进行翻转
	 * (-1, 0)	    先根据缩放中心缩小到n，再根据中心轴进行翻转
	 * 0	        不会显示，若sx为0，则宽度为0，不会显示，sy同理
	 * (0, 1)	    根据缩放中心缩小到n
	 * 1	        没有变化
	 * (1, +∞)	    根据缩放中心放大n倍
	 * <p>
	 * 缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴
	 * <p>
	 * PS:和位移(translate)一样，缩放也是可以叠加的。
	 */
	private void scale1(Canvas canvas) {
		// 将坐标系原点移动到画布正中心
		canvas.translate(with / 2, height / 2);
		// 矩形区域
		RectF rect = new RectF(0, -400, 400, 0);
		mBlackPaint.setStyle(Paint.Style.STROKE);
		mBlackPaint.setStrokeWidth(10);
		canvas.drawRect(rect, mBlackPaint);
		mBluePaint.setStyle(Paint.Style.STROKE);
		mBluePaint.setStrokeWidth(10);
		// 画布缩放
		canvas.scale(0.5f, 0.5f);
		canvas.drawRect(rect, mBluePaint);
		//画布放缩还原
		canvas.scale(2f, 2f);
		canvas.translate(-with / 2, -height / 2);
		RectF rect1 = new RectF(0, 0, 400, 400);
		canvas.drawRect(rect1, mBlackPaint);
		canvas.scale(0.5f, 0.5f, 200, 400);
		canvas.drawRect(rect1, mBluePaint);

	}

	private void scale2(Canvas canvas) {
		// 将坐标系原点移动到画布正中心
		canvas.translate(with / 2, height / 2);
		// 矩形区域
		RectF rect = new RectF(0, -400, 400, 0);
		mBlackPaint.setStyle(Paint.Style.STROKE);
		mBlackPaint.setStrokeWidth(10);
		canvas.drawRect(rect, mBlackPaint);
		mBluePaint.setStyle(Paint.Style.STROKE);
		mBluePaint.setStrokeWidth(10);
		// 画布缩放
		canvas.scale(-0.5f, -0.5f);
		canvas.drawRect(rect, mBlackPaint);

		//画布放缩还原
		//	canvas.scale(-2f,-2f);
		//	canvas.scale(-0.5f,-0.5f,200,0);

		//画布放缩还原
		canvas.translate(300 * (-2), 0);
		canvas.drawRect(rect, mBluePaint);

	}

	/**
	 * 请注意，位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动
	 * <p>
	 * 两次移动是可叠加的。
	 */
	private void translate(Canvas canvas) {

		canvas.translate(200, 200);
		canvas.drawCircle(0, 0, 100, mBlackPaint);
		canvas.translate(200, 200);
		canvas.drawCircle(0, 0, 100, mBluePaint);


	}

	public void resetView(int style) {
		mStyle = style;
		invalidate();
	}
}
