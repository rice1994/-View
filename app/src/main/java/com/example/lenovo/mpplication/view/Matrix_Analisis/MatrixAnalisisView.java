package com.example.lenovo.mpplication.view.Matrix_Analisis;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.view.base.BaseView;

import java.util.Arrays;

/**
 * Matrix详解
 *
 *
 * 方法类别	相关API	摘要
 基本方法	equals hashCode toString toShortString	比较、 获取哈希值、 转换为字符串
 数值操作	set reset setValues getValues	设置、 重置、 设置数值、 获取数值
 数值计算	mapPoints mapRadius mapRect mapVectors	计算变换后的数值
 设置(set)	setConcat setRotate setScale setSkew setTranslate	设置变换
 前乘(pre)	preConcat preRotate preScale preSkew preTranslate	前乘变换
 后乘(post)	postConcat postRotate postScale postSkew postTranslate	后乘变换
 特殊方法	setPolyToPoly setRectToRect rectStaysRect setSinCos	一些特殊操作
 矩阵相关	invert isAffine(API21) isIdentity	求逆矩阵、 是否为仿射矩阵、 是否为单位矩阵 …


 */
public class MatrixAnalisisView   extends BaseView  {
	private static final String TAG = MatrixAnalisisView.class.getSimpleName();
	public static final int setPolyToPoly=1;
	public static final int setRectToRect=2;
	public static final int Camera=3;
	private float[] src;
	private float[] dst;
	private Matrix matrix;

	private int testPoint = 4;
	private int triggerRadius = 180;    // 触发半径为180px
	private Bitmap bitmap;
	private Paint paint;

	public MatrixAnalisisView(Context context) {
		super(context);
		mapPoints();
		mapRadius();
		mapRect();
		mapVectors();
	}

	public MatrixAnalisisView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mapPoints();
		mapRadius();
		mapRect();
		mapVectors();
	}

	@Override
	public void resetView(int type) {
		super.resetView(type);
		switch (mType) {
			case setPolyToPoly:
				setPolyToPoly();
				break;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		switch (mType) {
			case setPolyToPoly:
				canvas.translate(100, 100);
				canvas.drawBitmap(bitmap, matrix, new Paint());
				for (int i = 0; i < testPoint * 2; i += 2) {
					canvas.drawPoint(dst[i], dst[i + 1], paint);
				}
				break;
			case setRectToRect:
				setRectToRect(canvas);
				break;
			case Camera:
				Camera(canvas);
				break;

		}
	}

	private void Camera(Canvas canvas) {
		setRectToRect(canvas);
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 计算中心点（这里是使用view的中心作为旋转的中心点）
				final float centerX = v.getWidth() / 2.0f;
				final float centerY = v.getHeight() / 2.0f;
				//括号内参数分别为（上下文，开始角度，结束角度，x轴中心点，y轴中心点，深度，是否扭曲）
				final Rotate3dAnimation rotation = new Rotate3dAnimation(getContext(),0, 180, centerX, centerY, 0f, true);
				rotation.setDuration(3000);
				rotation.setFillAfter(true);
				rotation.setInterpolator(new LinearInterpolator());
				v.startAnimation(rotation);
			}
		});
	}

	/**
	 *
	 boolean setRectToRect (RectF src,           // 源区域
	 RectF dst,                  // 目标区域
	 Matrix.ScaleToFit stf)      // 缩放适配模式

	 ScaleToFit 是一个枚举类型，共包含了四种模式:

	 CENTER	居中，对src等比例缩放，将其居中放置在dst中。
	 START	顶部，对src等比例缩放，将其放置在dst的左上角。
	 END	底部，对src等比例缩放，将其放置在dst的右下角。
	 FILL	充满，拉伸src的宽和高，使其完全填充满dst。
	 */
	private void setRectToRect(Canvas canvas){
		bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.beauty00);
		RectF src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF dst = new RectF(0, 0, mWidth, mHeight);
		matrix = new Matrix();
		matrix.setRectToRect(src,dst, Matrix.ScaleToFit.CENTER);
		canvas.drawBitmap(bitmap,matrix,new Paint());
	}
	/**
	 * boolean setPolyToPoly (
	 float[] src,    // 原始数组 src [x,y]，存储内容为一组点
	 int srcIndex,   // 原始数组开始位置
	 float[] dst,    // 目标数组 dst [x,y]，存储内容为一组点
	 int dstIndex,   // 目标数组开始位置
	 int pointCount) // 测控点的数量 取值范围是: 0到4
	 */
	private void setPolyToPoly() {
		bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow);

		float[] f = {0, 0,                                    // 左上
				bitmap.getWidth(), 0,                          // 右上
				bitmap.getWidth(), bitmap.getHeight(),        // 右下
				0, bitmap.getHeight()};                         // 左下

		src = f.clone();
		dst = f.clone();
		matrix =new Matrix();
		matrix.setPolyToPoly(src,0,dst,0,src.length >> 1);
		paint= new Paint();
		paint.setColor(0xffd19165);
		paint.setStrokeWidth(50);
		paint.setStrokeCap(Paint.Cap.ROUND);
	}

	private void setMatrix() {
		matrix.reset();
		matrix.setPolyToPoly(src,0,dst,0,src.length >> 1);
	}



		@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (mType){
			case setPolyToPoly:
				for(int i=0;i<testPoint*2;i+=2){
					if(Math.abs(event.getX()-dst[i])<triggerRadius && Math.abs(event.getY()-dst[i+1])<triggerRadius){
						dst[i]=event.getX();
						dst[i+1]=event.getY();
						setMatrix();
						invalidate();
						return true;
					}
				}
				break;
		}
		return super.onTouchEvent(event);
	}


	/**
	 * mapVectors 与 mapPoints 基本上是相同的，可以直接参照上面的mapPoints使用方法。
	 * 而两者唯一的区别就是mapVectors不会受到位移的影响，这符合向量的定律
	 *
	 * void mapVectors (float[] vecs)
	 * void mapVectors (float[] dst, float[] src)
	 * void mapVectors (float[] dst, int dstIndex, float[] src, int srcIndex, int vectorCount)
	 */
	private void mapVectors() {
		Log.i(TAG, "--------------------mapVectors(dst, src)-----------------");
		float[] src = new float[]{1000, 800};
		float[] dst = new float[2];
		Matrix matrix = new Matrix();
		matrix.setScale(0.5f,1f);
		matrix.postTranslate(100,100);
		// 计算向量, 不受位移影响
		matrix.mapVectors(dst, src);
		Log.i(TAG, "mapVectors: "+Arrays.toString(dst));
		// 计算点
		matrix.mapPoints(dst, src);
		Log.i(TAG, "mapPoints: "+Arrays.toString(dst));

		/* 结果：
		mapVectors: [500.0, 800.0]
		mapPoints: [600.0, 900.0]*/
	}

	/**
	 * boolean mapRect (RectF rect)
	 * 测量rect并将测量结果放入rect中，返回值是判断矩形经过变换后是否仍为矩形。
	 * boolean mapRect (RectF dst, RectF src)
	 * 测量src并将测量结果放入dst中，返回值是判断矩形经过变换后是否仍为矩形,和之前没有什么太大区别，此处就不啰嗦了。
	 */
	private void mapRect() {
		Log.i(TAG, "--------------------mapRect(rect)-----------------");
		RectF rect = new RectF(400, 400, 1000, 800);
		Matrix matrix = new Matrix();
		matrix.setScale(0.5f,1f);
		matrix.postSkew(1,0);
		Log.i(TAG, "mapRadius: "+rect.toString());
		boolean result = matrix.mapRect(rect);
		Log.i(TAG, "mapRadius: "+rect.toString());
		Log.e(TAG, "isRect: "+ result);

		/* 结果：
		mapRadius: RectF(400.0, 400.0, 1000.0, 800.0)
		mapRadius: RectF(600.0, 400.0, 1300.0, 800.0)
		isRect: false*/
	}

	/**
	 * float mapRadius (float radius)
	 */
	private void mapRadius() {
		Log.i(TAG, "--------------------mapRadius(radius)-----------------");
		float radius = 100;
		float result = 0;
		// 构造一个matrix，x坐标缩放0.5
		Matrix matrix = new Matrix();
		matrix.setScale(0.5f,1f);
		Log.i(TAG, "mapRadius: "+radius);
		result = matrix.mapRadius(radius);
		Log.i(TAG, "mapRadius: "+result);
		/* 结果：
		mapRadius: 100.0
		mapRadius: 70.71068*/
	}

	/**
	 * 数值计算--mapPoints
	 *
	 * 计算一组点基于当前Matrix变换后的位置，(由于是计算点，所以参数中的float数组长度一般都是偶数的,若为奇数，则最后一个数值不参与计算)。
	 *
	 * 1.mapPoints
	 * void mapPoints (float[] pts)
	 * 方法仅有一个参数，pts数组作为参数传递原始数值，计算结果仍存放在pts中。
	 * void mapPoints (float[] dst, float[] src)
	 * src作为参数传递原始数值，计算结果存放在dst中，src不变。
	 void mapPoints (float[] dst, int dstIndex,float[] src, int srcIndex, int pointCount)
	 */
	private void mapPoints() {
		// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
		float[] pts = new float[]{0, 0, 80, 100, 400, 300};
		Matrix matrix = new Matrix();
		matrix.setScale(0.5f,1f);
		Log.i(TAG, "--------------------mapPoints(pts)-----------------");
		// 输出pts计算之前数据
		Log.i(TAG, "before: "+ Arrays.toString(pts));
		//pts数组作为参数传递原始数值，计算结果仍存放在pts中。
		matrix.mapPoints(pts);
		// 输出pts计算之后数据
		Log.i(TAG, "after : "+ Arrays.toString(pts));

		/* 结果：
		before: [0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
		after : [0.0, 0.0, 40.0, 100.0, 200.0, 300.0]
		*/

		// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
		float[] src = new float[]{0, 0, 80, 100, 400, 300};
		float[] dst = new float[6];
		matrix.reset();
		matrix.setScale(0.5f, 1f);
		Log.i(TAG, "--------------------mapPoints(dst,src)-----------------");
		// 输出计算之前数据
		Log.i(TAG, "before: src="+ Arrays.toString(src));
		Log.i(TAG, "before: dst="+ Arrays.toString(dst));
		// 调用map方法计算,src作为参数传递原始数值，计算结果存放在dst中，src不变。
		matrix.mapPoints(dst,src);
		// 输出计算之后数据
		Log.i(TAG, "after : src="+ Arrays.toString(src));
		Log.i(TAG, "after : dst="+ Arrays.toString(dst));

		/* 结果：
		before: src=[0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
		before: dst=[0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
		after : src=[0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
		after : dst=[0.0, 0.0, 40.0, 100.0, 200.0, 300.0]
		*/

		// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
		src = new float[]{0, 0, 80, 100, 400, 300};
		dst = new float[6];

		matrix.reset();
		matrix.setScale(0.5f, 1f);
		Log.i(TAG, "--------------------mapPoints(dst,0,src,2,2)-----------------");
		// 输出计算之前数据
		Log.i(TAG, "before: src="+ Arrays.toString(src));
		Log.i(TAG, "before: dst="+ Arrays.toString(dst));
		//可以指定只计算一部分数值。
		//dst	目标数据
		//dstIndex	目标数据存储位置起始下标
		//src	源数据
		//srcIndex	源数据存储位置起始下标
		//pointCount	计算的点个数
		matrix.mapPoints(dst,0,src,2,2);
		// 输出计算之后数据
		Log.i(TAG, "after : src="+ Arrays.toString(src));
		Log.i(TAG, "after : dst="+ Arrays.toString(dst));
		/* 结果：
		before: src=[0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
		before: dst=[0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
		after : src=[0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
		after : dst=[40.0, 100.0, 200.0, 300.0, 0.0, 0.0]*/
	}
}
