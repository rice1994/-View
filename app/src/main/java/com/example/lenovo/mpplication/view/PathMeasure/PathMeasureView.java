package com.example.lenovo.mpplication.view.PathMeasure;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/5/28.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 * <p>
 * /**
 * Path & PathMeasure
 * 顾名思义，PathMeasure是一个用来测量Path的类，主要有以下方法:
 * <p>
 * 构造方法
 * 方法名	                                    释义
 * PathMeasure()	                            创建一个空的PathMeasure
 * PathMeasure(Path path, boolean forceClosed)	创建 PathMeasure 并关联一个指定的Path(Path需要已经创建完成)。
 * <p>
 * 在这里有两点需要明确:
 * 不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，即 Path 与 PathMeasure 关联之后，之前的的 Path 不会有任何改变。
 * forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与 PathMeasure 关联的时候设置 forceClosed 为 true 时，测量结果可能会比 Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。
 * <p>
 * 公共方法
 * 返回值	    方法名	 释义
 * void	    setPath(Path path, boolean forceClosed)	关联一个Path
 * boolean	isClosed()	是否闭合
 * float	    getLength()	获取Path的长度
 * boolean	nextContour()	跳转到下一个轮廓
 * boolean	getPosTan(float distance, float[] pos, float[] tan)	获取指定长度的位置坐标及该点切线值
 * boolean	getMatrix(float distance, Matrix matrix, int flags)	获取指定长度的位置坐标及该点Matrix
 * boolean	getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)	截取片段
 * 参数	             作用	                            备注
 * 返回值(boolean)	判断截取是否成功	                true 表示截取成功，结果存入dst中，false 截取失败，不会改变dst中内容
 * startD          	开始截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
 * stopD	            结束截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
 * dst	            截取的 Path 将会添加到 dst 中	    注意: 是添加，而不是替换
 * startWithMoveTo	起始点是否使用 moveTo	            用于保证截取的 Path 第一个点位置不变
 */
public class PathMeasureView extends BaseView {
	public final static int base = 1;
	public final static int getPosTan = 2;
	public final static int getMatrix = 3;
	private double currentValue;

	public PathMeasureView(Context context) {
		super(context);
	}

	public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mType) {
			case base:
				xx(canvas);
				yy(canvas);
				break;
			case getPosTan:
				getPosTan(canvas);
				break;
			case getMatrix:
				getMatrix(canvas);
				break;
		}
	}

	/**
	 * *
	 * 构造方法
	 * 方法名	                                    释义
	 * PathMeasure()	                            创建一个空的PathMeasure
	 * PathMeasure(Path path, boolean forceClosed)	创建 PathMeasure 并关联一个指定的Path(Path需要已经创建完成)。
	 * <p>
	 * 在这里有两点需要明确:
	 * 不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，即 Path 与 PathMeasure 关联之后，之前的的 Path 不会有任何改变。
	 * forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与 PathMeasure 关联的时候设置 forceClosed 为 true 时，测量结果可能会比 Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。
	 * <p>
	 * void	    setPath(Path path, boolean forceClosed)	关联一个Path
	 * boolean	isClosed()	是否闭合
	 * float	getLength()	获取Path的长度
	 * boolean	nextContour()	跳转到下一个轮廓
	 * boolean	getMatrix(float distance, Matrix matrix, int flags)	获取指定长度的位置坐标及该点Matrix
	 */
	private void xx(Canvas canvas) {
		canvas.translate(mWidth / 2, 0);
		mPath.lineTo(0, 100);
		mPath.lineTo(100, 100);
		mPath.lineTo(100, 0);
		PathMeasure pathMeasure1 = new PathMeasure(mPath, false);
		PathMeasure pathMeasure2 = new PathMeasure(mPath, true);

		Log.e("TAG", "forceClosed=false---->" + pathMeasure1.getLength());
		Log.e("TAG", "forceClosed=true----->" + pathMeasure2.getLength());
	}

	/**
	 * void	    setPath(Path path, boolean forceClosed)	关联一个Path
	 * boolean	isClosed()	是否闭合
	 * float	getLength()	获取Path的长度
	 * boolean	getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)	截取片段
	 * 参数	             作用	                            备注
	 * 返回值(boolean)	判断截取是否成功	                true 表示截取成功，结果存入dst中，false 截取失败，不会改变dst中内容
	 * startD          	开始截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
	 * stopD	            结束截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
	 * dst	            截取的 Path 将会添加到 dst 中	    注意: 是添加，而不是替换
	 * startWithMoveTo	起始点是否使用 moveTo	            用于保证截取的 Path 第一个点位置不变
	 *
	 * 注意：
	 * 如果 startD、stopD 的数值不在取值范围 [0, getLength] 内，或者 startD == stopD 则返回值为 false，不会改变 dst 内容。
	 * 如果在安卓4.4或者之前的版本，在默认开启硬件加速的情况下，更改 dst 的内容后可能绘制会出现问题，请关闭硬件加速或者给 dst 添加一个单个操作，例如: dst.rLineTo(0, 0)
	 */
	private void yy(Canvas canvas) {
		canvas.translate(0, 300 + 50);
		canvas.save();
		canvas.translate(-mWidth / 4, 0);

		mPath.reset();
		mPath.addRect(-200, -200, 200, 200, Path.Direction.CW);
		canvas.drawPath(mPath, mStrokeGrayPaint);
		PathMeasure pathMeasure = new PathMeasure(mPath, true);
		canvas.translate(mWidth / 2, 0);
		Path path = new Path();
		pathMeasure.getSegment(200, 600, path, true);
		canvas.drawPath(path, mStrokeRedPaint);

		canvas.restore();
		canvas.translate(0, 600);
		canvas.translate(-mWidth / 4, 0);
		path = new Path();
		path.lineTo(-250, -250);
		pathMeasure.getSegment(200, 600, path, true);
		canvas.drawPath(path, mStrokeGrayPaint);

		canvas.translate(mWidth / 2, 0);
		path = new Path();
		path.lineTo(-250, -250);
		pathMeasure.getSegment(200, 600, path, false);
		canvas.drawPath(path, mStrokeGrayPaint);

		canvas.translate(-mWidth / 4, 300);
		mPath.reset();
		mPath.addRect(-300, -100, -100, 100, Path.Direction.CW);
		mPath.addRect(0, -200, 400, 200, Path.Direction.CW);
		canvas.drawPath(mPath, mStrokeGrayPaint);
		PathMeasure pathMeasure1 = new PathMeasure(mPath, false);
		float length1 = pathMeasure1.getLength();
		pathMeasure1.nextContour();
		float length2 = pathMeasure1.getLength();
		String text = "length1=" + length1 + ",length2=" + length2;
		Rect bounds = new Rect();
		mBluePaint.getTextBounds(text, 0, text.length(), bounds);
		canvas.drawText(text, -(bounds.right - bounds.left) / 2, 300, mBluePaint);
	}

	/**
	 * boolean	getPosTan(float distance, float[] pos, float[] tan)	获取指定长度的位置坐标及该点切线值
	 * <p>
	 * 参数	             作用	               备注
	 * 返回值(boolean)	 判断获取是否成功	    true表示成功，数据会存入 pos 和 tan 中，
	 * false  表示失败，pos 和 tan 不会改变
	 * distance	         距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
	 * pos	             该点的坐标值	         当前点在画布上的位置，有两个数值，分别为x，y坐标。
	 * tan	             该点的正切值	         当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
	 * <p>
	 * 注意角度公式： Math.atan2(tan[1], tan[0])*180/Math.PI
	 */
	private void getPosTan(Canvas canvas) {
		float[] pos = new float[2];                // 当前点的实际位置
		float[] tan = new float[2];                // 当前点的tangent值,用于计算图片所需旋转的角度
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;       // 缩放图片
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow, options);             // 箭头图片
		Matrix mMatrix = new Matrix();             // 矩阵,用于对图片进行一些操作
		canvas.translate(mWidth / 2, mHeight / 2);

		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		if (currentValue >= 1) {
			currentValue = 0;
		}
		currentValue += 0.005;
		PathMeasure pathMeasure = new PathMeasure(mPath, true);

		pathMeasure.getPosTan((float) (pathMeasure.getLength() * currentValue), pos, tan);
		mMatrix.reset();  // 重置Matrix
		float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度
		mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
		mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合
		canvas.drawPath(mPath, mStrokeGrayPaint);
		canvas.drawBitmap(mBitmap, mMatrix, mStrokeGrayPaint);
		invalidate();

		//1.通过 tan 得值计算出图片旋转的角度，tan 是 tangent 的缩写，即中学中常见的正切， 其中tan[0]是邻边边长，tan[1]是对边边长，而Math中 atan2 方法是根据正切是数值计算出该角度的大小,得到的单位是弧度(取值范围是 -pi 到 pi)，所以上面又将弧度转为了角度。
		//2.通过 Matrix 来设置图片对旋转角度和位移，这里使用的方法与前面讲解过对 canvas操作 有些类似，对于 Matrix 会在后面专一进行讲解，敬请期待。
		//3.页面刷新，页面刷新此处是在 onDraw 里面调用了 invalidate 方法来保持界面不断刷新，但并不提倡这么做，正确对做法应该是使用 线程 或者 ValueAnimator 来控制界面的刷新，关于控制页面刷新这一部分会在后续的 动画部分 详细讲解，同样敬请期待。
	}

	/**
	 * 这个方法是用于得到路径上某一长度的位置以及该位置的正切值的矩阵：
	 * boolean getMatrix (float distance, Matrix matrix, int flags)
	 * 方法各个参数释义：
	 * <p>
	 * 参数	            作用	                      备注
	 * 返回值(boolean)	判断获取是否成功	          true表示成功，数据会存入matrix中，false 失败，matrix内容不会改变
	 * distance	        距离 Path 起点的长度     	  取值范围: 0 <= distance <= getLength
	 * matrix         	根据 falgs 封装好的matrix	  会根据 flags 的设置而存入不同的内容
	 * flags	            规定哪些内容会存入到matrix中  可选择POSITION_MATRIX_FLAG(位置)、ANGENT_MATRIX_FLAG(正切)
	 */
	private void getMatrix(Canvas canvas) {
		float[] pos = new float[2];                // 当前点的实际位置
		float[] tan = new float[2];                // 当前点的tangent值,用于计算图片所需旋转的角度
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;       // 缩放图片
		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow, options);             // 箭头图片
		Matrix mMatrix = new Matrix();             // 矩阵,用于对图片进行一些操作
		canvas.translate(mWidth / 2, mHeight / 2);

		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		if (currentValue >= 1) {
			currentValue = 0;
		}
		currentValue += 0.005;
		PathMeasure pathMeasure = new PathMeasure(mPath, true);

		pathMeasure.getMatrix((float) (pathMeasure.getLength() * currentValue), mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
		mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);   // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
		canvas.drawPath(mPath, mStrokeGrayPaint);
		canvas.drawBitmap(mBitmap, mMatrix, mStrokeGrayPaint);
		invalidate();

		//1.对 matrix 的操作必须要在 getMatrix 之后进行，否则会被 getMatrix 重置而导致无效。
		//2.矩阵对旋转角度默认为图片的左上角，我们此处需要使用 preTranslate 调整为图片中心。
		//3.pre(矩阵前乘) 与 post(矩阵后乘) 的区别，此处请等待后续的文章或者自行搜索。
	}
}
