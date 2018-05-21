package com.example.lenovo.mpplication.view.basic_operation_of_Path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Path之基本操作
 * <p>
 * 请关闭硬件加速，以免引起不必要的问题！
 */
public class BasePathView extends BaseView {
	public final static int basic_operation_of_Path = 1;
	public final static int addXx_arcTo_of_Path = 2;
	public final static int isXX_set_offset_of_Path = 3;
	public final static int addArc_arcTo_of_Path = 4;
	public final static int isEmpty_isRect_isConvex_set_offset_of_Path=5;
	public final static int path_application=6;

	public BasePathView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public BasePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mType) {
			case basic_operation_of_Path:
				basic_operation_of_Path(canvas);
				break;
			case addXx_arcTo_of_Path:
				addXx_arcTo_of_Path(canvas);

				break;
			case isXX_set_offset_of_Path:
				isXX_set_offset_of_Path(canvas);
				break;
			case addArc_arcTo_of_Path:
				addArc_arcTo_of_Path(canvas);
				break;
				case isEmpty_isRect_isConvex_set_offset_of_Path:
					isEmpty_isRect_isConvex_set_offset_of_Path(canvas);
				break;
		}
	}

	/**
	 * public boolean isEmpty ()
	 * 判断path中是否包含内容。
	 * public boolean isRect (RectF rect)
	 * 判断path是否是一个矩形，如果是一个矩形的话，会将矩形的信息存放进参数rect中。
	 * public void set (Path src)
	 * 将新的path赋值到现有path。
	 * public void offset (float dx, float dy)
	 * public void offset (float dx, float dy, Path dst)
	 * 这个的作用也很简单，就是对path进行一段平移，它和Canvas中的translate作用很像，但Canvas作用于整个画布，而path的offset只作用于当前path。
	 * 第二个方法中最后的参数das是存储平移后的path的。
	 * dst状态	        效果
	 * dst不为空	    将当前path平移后的状态存入dst中，不会影响当前path
	 * dat为空(null)	平移将作用于当前path，相当于第一种方法
	 */
	private void isEmpty_isRect_isConvex_set_offset_of_Path(Canvas canvas) {
		Path path = new Path();
		Log.e("1",path.isEmpty()+"");

		path.lineTo(100,100);
		Log.e("2",path.isEmpty()+"");

		path.lineTo(0,400);
		path.lineTo(400,400);
		path.lineTo(400,0);
		path.lineTo(0,0);

		RectF rect = new RectF();
		boolean b = path.isRect(rect);
		Log.e("Rect","isRect:"+b+"| left:"+rect.left+"| top:"+rect.top+"| right:"+rect.right+"| bottom:"+rect.bottom);

		canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
		canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴

		path = new Path();                     // path添加一个矩形
		path.addRect(-200,-200,200,200, Path.Direction.CW);

		Path src = new Path();                      // src添加一个圆
		src.addCircle(0,0,100, Path.Direction.CW);

		path.set(src);                              // 大致相当于 path = src;

		canvas.drawPath(path,mStrokeRedPaint);

		path.offset(300,0);

		canvas.drawPath(path,mStrokeBluePaint);
	}

	/**
	 * 第三类(addArc与arcTo)
	 * // addArc
	 * public void addArc (RectF oval, float startAngle, float sweepAngle)
	 * // arcTo
	 * public void arcTo (RectF oval, float startAngle, float sweepAngle)
	 * public void arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
	 *
	 * 参数	                  摘要
	 * oval                   圆弧的外切矩形。
	 * startAngle             开始角度
	 * sweepAngle             扫过角度(-360 <= sweepAngle <360)
	 * forceMoveTo            是否强制使用MoveTo,也就是说，是否使用moveTo将变量移动到圆弧的起点位移,也就意味着：
	 *
	 * true	    将最后一个点移动到圆弧起点，即不连接最后一个点与圆弧起点	public void addArc (RectF oval, float startAngle, float sweepAngle)
	 * false	不移动，而是连接最后一个点与圆弧起点	public void arcTo (RectF oval, float startAngle, float sweepAngle)
	 *
	 * PS: sweepAngle取值范围是 [-360, 360)，不包括360，当 >= 360 或者 < -360 时将不会绘制任何内容， 对于360，你可以用一个接近的值替代，例如: 359.99。
	 *
	 * 名称	    作用	            区别
	 * addArc	添加一个圆弧到path	直接添加一个圆弧到path中
	 * arcTo	添加一个圆弧到path	添加一个圆弧到path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
	 */
	private void addArc_arcTo_of_Path(Canvas canvas) {
		canvas.translate(mWidth/2,300);
		canvas.scale(1,-1);
		Path path = new Path();
		path.lineTo(100,100);
		RectF rectF = new RectF(0, 0, 300, 300);
		path.addArc(rectF,0,270);
//		path.arcTo(rectF,0,270,true);
		canvas.drawPath(path,mStrokeBluePaint);

		canvas.translate(0,-300);
		path =  new Path();
		path.lineTo(100,100);
		path.arcTo(rectF,0,270);
//		path.arcTo(rectF,0,270,false);
		canvas.drawPath(path,mStrokeRedPaint);


	}

	/**
	 * // 第二类(Path) 多个Path合并
	 * public void addPath (Path src)
	 * public void addPath (Path src, float dx, float dy)
	 * 第二个方法比第一个方法多出来的两个参数是将src进行了位移之后再添加进当前path中。
	 * public void addPath (Path src, Matrix matrix)
	 * 第三个方法是将src添加到当前path之前先使用Matrix进行变换。
	 */
	private void isXX_set_offset_of_Path(Canvas canvas) {
		canvas.translate(mWidth / 2, 200);
		RectF rectF = new RectF(-100, -100, 100, 100);
		Path path = new Path();
		path.addRect(rectF, Path.Direction.CW);
		Path src = new Path();
		src.addCircle(0, -100, 50, Path.Direction.CW);
		path.addPath(src);
		canvas.drawPath(path, mStrokeGreenPaint);
	}


	/**
	 * addXxx与arcTo
	 * <p>
	 * 第一类(基本形状)
	 * <p>
	 * // 圆形
	 * public void addCircle (float x, float y, float radius, Path.Direction dir)
	 * // 椭圆
	 * public void addOval (RectF oval, Path.Direction dir)
	 * // 矩形
	 * public void addRect (float left, float top, float right, float bottom, Path.Direction dir)
	 * public void addRect (RectF rect, Path.Direction dir)
	 * // 圆角矩形
	 * public void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
	 * public void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
	 * <p>
	 * Direction的意思是 方向，趋势。 点进去看一下会发现Direction是一个枚举(Enum)类型，里面只有两个枚举常量，如下：
	 * <p>
	 * 类型	 解释	            翻译
	 * CW	 clockwise	        顺时针
	 * CCW	 counter-clockwise	逆时针
	 * <p>
	 * 顺时针和逆时针的作用:
	 * <p>
	 * 序号	  作用
	 * 1	  在添加图形时确定闭合顺序(各个点的记录顺序)
	 * 2	  对图形的渲染结果有影响(是判断图形渲染的重要条件)
	 */
	void addXx_arcTo_of_Path(Canvas canvas) {
		canvas.translate(mWidth / 2, 200);
		Path path = new Path();
		RectF rect = new RectF(-100, -100, 100, 100);
		path.addRect(rect, Path.Direction.CW);
		canvas.drawPath(path, mStrokeBluePaint);

		canvas.translate(0, 300);
		path.setLastPoint(-150, 150);
		canvas.drawPath(path, mStrokeRedPaint);

		path = new Path();
		canvas.translate(0, 300);
		/** CCW	 counter-clockwise	逆时针*/
		path.addRect(rect, Path.Direction.CCW);
		path.setLastPoint(-150, 150);
		canvas.drawPath(path, mStrokeYellowPaint);
	}

	/**
	 * moveTo、 setLastPoint、 lineTo 和 close
	 */
	private void basic_operation_of_Path(Canvas canvas) {
		canvas.save();
		canvas.translate(mWidth / 2, 0);
		Path path = new Path();
		path.lineTo(200, 200);
		path.lineTo(200, 0);
		canvas.drawPath(path, mStrokeBluePaint);

		canvas.translate(0, 300);
		path = new Path();
		path.lineTo(200, 200);
		path.moveTo(200, 100);
		path.lineTo(200, 0);
		canvas.drawPath(path, mStrokeBluePaint);

		canvas.translate(0, 300);
		path = new Path();
		path.lineTo(200, 200);
		path.setLastPoint(200, 100);
		path.lineTo(200, 0);
		canvas.drawPath(path, mStrokeBluePaint);


		canvas.translate(0, 300);
		path = new Path();
		path.lineTo(200, 200);
		path.lineTo(200, 0);
		// TODO:
		// 注意：close的作用是封闭路径，与连接当前最后一个点和第一个点并不等价。
		// 如果连接了最后一个点和第一个点仍然无法形成封闭图形，则close什么 也不做。
		path.close();
		canvas.drawPath(path, mStrokeBluePaint);
	}

}
/**
 * 作用	    相关方法	    备注
 * 移动起点	moveTo	        移动下一次操作的起点位置
 * 设置终点	setLastPoint    重置当前path中最后一个点位置，如果在绘制之前调用，效果和moveTo相同
 * 连接直线	lineTo	        添加上一个点到当前点之间的直线到Path
 * 闭合路径	close	        连接第一个点连接到最后一个点，形成一个闭合区域
 * 添加内容	addRect, addRoundRect, addOval, addCircle, addPath, addArc, arcTo	添加(矩形， 圆角矩形， 椭圆， 圆， 路径， 圆弧) 到当前Path (注意addArc和arcTo的区别)
 * 是否为空	isEmpty	        判断Path是否为空
 * 是否为矩形	isRect	        判断path是否是一个矩形
 * 替换路径	set	            用新的路径替换到当前路径所有内容
 * 偏移路径	offset     	    对当前路径之前的操作进行偏移(不会影响之后的操作)
 * 贝塞尔曲线	quadTo, cubicTo	  分别为二次和三次贝塞尔曲线的方法
 * rXxx方法	rMoveTo, rLineTo, rQuadTo, rCubicTo	不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
 * 填充模式	setFillType, getFillType, isInverseFillType, toggleInverseFillType	设置,获取,判断和切换填充模式
 * 提示方法	incReserve	    提示Path还有多少个点等待加入(这个方法貌似会让Path优化存储结构)
 * 布尔操作(API19)	op	    对两个Path进行布尔运算(即取交集、并集等操作)
 * 计算边界	computeBounds	计算Path的边界
 * 重置路径	reset, rewind	清除Path中的内容
 * reset不保留内部数据结构，但会保留FillType.
 * rewind会保留内部的数据结构，但不保留FillType
 * 矩阵操作	transform	    矩阵变换
 */
