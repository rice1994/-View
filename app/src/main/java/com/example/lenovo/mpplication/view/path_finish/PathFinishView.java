package com.example.lenovo.mpplication.view.path_finish;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Toast;

import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Path之完结篇
 *
 * 重置路径
 * 重置Path有两个方法，分别是reset和rewind，两者区别主要有一下两点：
 * 方法	   是否保留FillType设置	是否保留原有数据结构
 * reset	  是	                     否
 * rewind	  否                     是
 */
public class PathFinishView extends BaseView {
	public static final int XXX = 1;
	public static final int RXXX = 2;
	public static final int EVEN_ODD = 3;
	public static final int INVERSE_EVEN_ODD = 4;
	public static final int WINDING = 5;
	public static final int INVERSE_WINDING = 6;
	public static final int OP_DEMO = 7;
	public static final int OP = 8;
	public static final int BOUNDS = 9;

	public PathFinishView(Context context) {
		super(context);
	}

	public PathFinishView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mType) {
			case XXX:
				XXX(canvas);
				break;
			case RXXX:
				RXXX(canvas);

				break;
			case EVEN_ODD:
				EVEN_ODD(canvas);

				break;
			case INVERSE_EVEN_ODD:
				INVERSE_EVEN_ODD(canvas);

				break;
			case WINDING:
				WINDING(canvas);

				break;
			case INVERSE_WINDING:				INVERSE_WINDING(canvas);

				break;
			case OP_DEMO:				OP_DEMO(canvas);

				break;
			case OP:
				OP(canvas);

				break;
			case BOUNDS:
				BOUNDS(canvas);
				break;
		}
	}

	/**
	 * void computeBounds (RectF bounds, boolean exact)
	 *
	 * 参数	    作用
	 * bounds	测量结果会放入这个矩形
	 * exact	是否精确测量，目前这一个参数作用已经废弃，一般写true即可。
	 */
	private void BOUNDS(Canvas canvas) {
		canvas.translate(mWidth/2,mHeight/2);
		mPath.reset();
		mPath.lineTo(100,-50);
		mPath.lineTo(100,50);
		mPath.close();
		mPath.addCircle(-100,0,100, Path.Direction.CW);
		RectF bounds = new RectF();
		mPath.computeBounds(bounds,true);
		canvas.drawPath(mPath,mBluePaint);
		canvas.drawRect(bounds,mStrokeRedPaint);
	}

	/**
	 * 逻辑名称	             类比	说明
	 * DIFFERENCE	         差集	Path1中减去Path2后剩下的部分
	 * REVERSE_DIFFERENCE	 差集	Path2中减去Path1后剩下的部分
	 * INTERSECT	         交集	Path1与Path2相交的部分
	 * UNION	             并集	包含全部Path1和Path2
	 * XOR	                 异或	包含Path1与Path2但不包括两者相交的部分
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	private void OP(Canvas canvas) {
		canvas.translate(250,0);
		int x=80;
		int r=100;
		Path path1 = new Path();
		Path path2 = new Path();
		path1.addCircle(-x,0,100, Path.Direction.CW);
		path2.addCircle(x,0,100, Path.Direction.CW);

		canvas.translate(0,300);
		mPath.op(path1,path2, Path.Op.DIFFERENCE);
		canvas.drawPath(mPath,mBluePaint);
		canvas.drawText("DIFFERENCE",250,0,mBlackPaint);

		canvas.translate(0,300);
		mPath.op(path1,path2, Path.Op.REVERSE_DIFFERENCE);
		canvas.drawPath(mPath,mBluePaint);
		canvas.drawText("REVERSE_DIFFERENCE",250,0,mBlackPaint);

		canvas.translate(0, 300);
		mPath.op(path1, path2, Path.Op.INTERSECT);
		canvas.drawPath(mPath, mBluePaint);
		canvas.drawText("INTERSECT", 250, 0, mBlackPaint);

		canvas.translate(0,300);
		mPath.op(path1,path2, Path.Op.UNION);
		canvas.drawPath(mPath,mBluePaint);
		canvas.drawText("UNION",250,0,mBlackPaint);

		canvas.translate(0, 300);
		mPath.op(path1, path2, Path.Op.XOR);
		canvas.drawPath(mPath, mBluePaint);
		canvas.drawText("XOR", 250, 0, mBlackPaint);
	}

	/**
	 *
	 */
	private void OP_DEMO(Canvas canvas) {
		canvas.translate(mWidth / 2, mHeight / 2);

		Path path1 = new Path();
		Path path2 = new Path();
		Path path3 = new Path();
		Path path4 = new Path();

		path1.addCircle(0, 0, 200, Path.Direction.CW);
		path2.addRect(0, -200, 200, 200, Path.Direction.CW);
		path3.addCircle(0, -100, 100, Path.Direction.CW);
		path4.addCircle(0, 100, 100, Path.Direction.CCW);


		path1.op(path2, Path.Op.DIFFERENCE);
		path1.op(path3, Path.Op.UNION);
		path1.op(path4, Path.Op.DIFFERENCE);

		canvas.drawPath(path1, mBluePaint);
	}

	/**
	 * 反非零环绕数规则
	 */
	private void INVERSE_WINDING(Canvas canvas) {
		canvas.save();
		canvas.translate(mWidth / 2, mHeight / 4);
		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		mPath.addCircle(0, 0, 100, Path.Direction.CW);
		mPath.setFillType(Path.FillType.INVERSE_WINDING);
		canvas.drawPath(mPath, mBluePaint);

		canvas.restore();
		canvas.translate(mWidth / 2, mHeight * 3 / 4);
		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		mPath.addCircle(0, 0, 100, Path.Direction.CCW);
		mPath.setFillType(Path.FillType.INVERSE_WINDING);
		canvas.drawPath(mPath, mBluePaint);
	}

	/**
	 * 非零环绕数规则
	 */
	private void WINDING(Canvas canvas) {
		canvas.save();
		canvas.translate(mWidth / 2, mHeight / 4);
		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		mPath.addCircle(0, 0, 100, Path.Direction.CW);
		mPath.setFillType(Path.FillType.WINDING);
		canvas.drawPath(mPath, mBluePaint);

		canvas.restore();
		canvas.translate(mWidth / 2, mHeight * 3 / 4);
		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		mPath.addCircle(0, 0, 100, Path.Direction.CCW);
		mPath.setFillType(Path.FillType.WINDING);
		canvas.drawPath(mPath, mBluePaint);
	}

	/**
	 * 反奇偶规则
	 */
	private void INVERSE_EVEN_ODD(Canvas canvas) {
		canvas.save();
		canvas.translate(mWidth / 2, mHeight / 4);
		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
		canvas.drawPath(mPath, mBluePaint);

	}

	/**
	 * 方法:奇偶规则	      判定条件:奇数表示在图形内，偶数表示在图形外
	 * 解释：从任意位置p作一条射线， 若与该射线相交的图形边的数目为奇数，则p是图形内部点，否则是外部点。
	 * <p>
	 * 方法:非零环绕数规则	  判定条件:若环绕数为0表示在图形外，非零表示在图形内
	 * 解释：将计数器初始化为0，每当这个线段与路径上的直线或曲线相交时，就改变计数器的值，
	 * 如果是与路径顺时针相交时，那么计数器就加1， 如果是与路径逆时针相交时，那么计数器就减1.
	 * 如果计数器始终不为0，那么此区域就在路径范围里面，在调用fill()方法时，浏览器就会对其进行填充。
	 * 如果最终值是0，那么此区域就不在路径范围内，浏览器就不会对其进行填充。
	 * <p>
	 * 奇偶规则
	 */
	private void EVEN_ODD(Canvas canvas) {
		canvas.save();
		canvas.translate(mWidth / 2, mHeight / 4);
		mPath.reset();
		mPath.addCircle(0, 0, 200, Path.Direction.CW);
		mPath.setFillType(Path.FillType.EVEN_ODD);
		canvas.drawPath(mPath, mBluePaint);
	}

	/**
	 * rXxx方法的坐标使用的是相对位置(基于当前点的位移)，而之前方法的坐标是绝对位置(基于当前坐标系的坐标)。
	 */
	private void RXXX(Canvas canvas) {
		mPath.reset();
		mPath.moveTo(100, 100);
		mPath.lineTo(100, 200);
		canvas.drawPath(mPath, mStrokeBluePaint);
	}

	private void XXX(Canvas canvas) {
		mPath.reset();
		mPath.moveTo(100, 100);
		mPath.rLineTo(100, 200);
		canvas.drawPath(mPath, mStrokeBluePaint);
	}
}
