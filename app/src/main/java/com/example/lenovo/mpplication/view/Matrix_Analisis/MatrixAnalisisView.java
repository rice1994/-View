package com.example.lenovo.mpplication.view.Matrix_Analisis;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

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

 Matrix 相关的重要知识：

 1.一开始从Canvas中获取到到Matrix并不是初始矩阵，而是经过偏移后到矩阵，且偏移距离就是距离屏幕左上角的位置。
 这个可以用于判定View在屏幕上的绝对位置，View可以根据所处位置做出调整。

 2.构造Matrix时使用的是矩阵乘法，前乘(pre)与后乘(post)结果差别很大。

 3.受矩阵乘法影响，后面的执行的操作可能会影响到之前的操作。
 使用时需要注意构造顺序。

 */
public class MatrixAnalisisView   extends BaseView  {
	private static final String TAG = MatrixAnalisisView.class.getSimpleName();
	private Bitmap mBitmap;             // 要绘制的图片
	private Matrix mPolyMatrix;         // 测试setPolyToPoly用的Matrix
	private float[] src;
	private float[] dst;
	private int triggerRadius = 180;    // 触发半径为180px
	private int testPoint=4;
	private Paint pointPaint;

	public MatrixAnalisisView(Context context) {
		super(context);
		init();
	}

	public MatrixAnalisisView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();

	}

	void init(){
		initBitmapAndMatrix();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mapPoints();
		mapRadius();
		mapRect();
		mapVectors();
		xx(canvas);
	}
	/**
	 * 特殊方法
	 * boolean setPolyToPoly (
	 * float[] src,    // 原始数组 src [x,y]，存储内容为一组点
	 * int srcIndex,   // 原始数组开始位置
	 * float[] dst,    // 目标数组 dst [x,y]，存储内容为一组点
	 * int dstIndex,   // 目标数组开始位置
	 * int pointCount) // 测控点的数量 取值范围是: 0到4
	 *
	 * Poly全称是Polygon
	 */
	private void initBitmapAndMatrix() {
		mBitmap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.arrow);

		float[] temp = {0, 0,                                    // 左上
				mBitmap.getWidth(), 0,                          // 右上
				mBitmap.getWidth(), mBitmap.getHeight(),        // 右下
				0, mBitmap.getHeight()}; 		             // 左下// 左下
		src = temp.clone();
		dst = temp.clone();
		mPolyMatrix = new Matrix();
		resetPolyMatrix(4);

		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);
		pointPaint.setStrokeWidth(50);
		pointPaint.setColor(0xffd19165);
		pointPaint.setStrokeCap(Paint.Cap.ROUND);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_MOVE:
				float tempX = event.getX();
				float tempY = event.getY();

				for(int i=0;i<testPoint*2;i++){
					if(Math.abs(tempX-dst[i])<=triggerRadius &&
							Math.abs(tempY-dst[i+1])<=triggerRadius){
						dst[i]=tempX;
						dst[i+1]=tempY;
						break;
					}
				}
				resetPolyMatrix(4);
				invalidate();
		}

		return true;

	}

	public void resetPolyMatrix(int pointCount){
		mPolyMatrix.reset();
		// 核心要点

		mPolyMatrix.setPolyToPoly(src, 0, dst, 0, pointCount);
	}

	void xx(Canvas canvas){
		canvas.drawBitmap(mBitmap,mPolyMatrix,null);
		float[] dst = new float[8];
		mPolyMatrix.mapPoints(dst,src);


		// 绘制触控点
		for (int i=0; i<testPoint*2; i+=2 ) {
			canvas.drawPoint(dst[i], dst[i+1],pointPaint);
		}
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
