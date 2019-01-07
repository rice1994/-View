package com.example.lenovo.mpplication.view.setPathEffect;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * Created by fan on 2018/5/22.
 */
public class SetPathEffectView extends BaseView {
	public final static int basic_operation_of_Path = 1;
	public final static int CornerPathEffect1 = 2;
	public final static int DiscretePathEffect1 = 3;
	public final static int DashPathEffect1 = 4;
	public final static int PathDashPathEffect1 = 5;
	public final static int ComposePathEffect1 = 6;
	public final static int SumPathEffect1 = 7;
	private final Path mPath;
	private int phase;
	private int phase1;

	public SetPathEffectView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mPath = new Path();
		mPath.moveTo(10,50);
		for(int i=1;i<30;i++){
			mPath.lineTo(i*30, (float) (Math.random()*100));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(0,mHeight/2);
		switch (mType) {
			case basic_operation_of_Path:

				canvas.drawPath(mPath,mStrokeRedPaint);
				break;
			case CornerPathEffect1:
				CornerPathEffect1(canvas);
				break;
			case DiscretePathEffect1:
				DiscretePathEffect1(canvas);

				break;
			case DashPathEffect1:				DashPathEffect1(canvas);

				break;
			case PathDashPathEffect1:				PathDashPathEffect1(canvas);

				break;
			case ComposePathEffect1:				ComposePathEffect1(canvas);

				break;
			case SumPathEffect1:				SumPathEffect1(canvas);

				break;
		}
	}

	/**
	 * SumPathEffect(PathEffect first, PathEffect second)则会把两种路径效果加起来再作用于路径。
	 */
	private void SumPathEffect1(Canvas canvas) {
		CornerPathEffect cornerPathEffect = new CornerPathEffect(30);
		DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10}, phase);
		SumPathEffect pathEffect = new SumPathEffect(dashPathEffect, cornerPathEffect);
		mStrokeBluePaint.setPathEffect(pathEffect);
		canvas.drawPath(mPath,mStrokeBluePaint);

		canvas.translate(0,100);
		pathEffect = new SumPathEffect(cornerPathEffect, dashPathEffect);
		mStrokeBluePaint.setPathEffect(pathEffect);
		canvas.drawPath(mPath, mStrokeBluePaint);
		phase++;
		invalidate();
	}

	/**
	 * ComposePathEffect(PathEffect outerpe, PathEffect innerpe)会先将路径变成innerpe的效果，
	 * 再去复合outerpe的路径效果，即：outerpe(innerpe(Path))；
	 */
	private void ComposePathEffect1(Canvas canvas) {
		CornerPathEffect cornerPathEffect = new CornerPathEffect(30);
		DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10}, phase);
		ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect, cornerPathEffect);
		mStrokeBluePaint.setPathEffect(composePathEffect);
		canvas.drawPath(mPath,mStrokeBluePaint);

		canvas.translate(0,100);
		composePathEffect = new ComposePathEffect(cornerPathEffect, dashPathEffect);
		mStrokeBluePaint.setPathEffect(composePathEffect);
		canvas.drawPath(mPath, mStrokeBluePaint);
		phase++;
		invalidate();
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}

	/**
	 * PathDashPathEffect和DashPathEffect是类似的，不同的是PathDashPathEffect可以让我们自己定义路径虚线的样式，
	 * 比如我们将其换成一个个小圆组成的虚线：
	 *
	 *     public PathDashPathEffect(Path shape, float advance, float phase,Style style)
	 *     advance为样式间距
	 */
	private void PathDashPathEffect1(Canvas canvas) {
		Path path = new Path();
		path.addCircle(0,0,3, Path.Direction.CW);
		mStrokeBluePaint.setPathEffect(new PathDashPathEffect(path,20,phase1,
				PathDashPathEffect.Style.ROTATE));
		canvas.drawPath(mPath,mStrokeBluePaint);
		phase1++;
		invalidate();
	}

	/**
	 * 它的效果相对与上面两种路径效果来说要略显复杂，其虽说也是包含了两个参数：
	 * public DashPathEffect(float intervals[], float phase)
	 * 而DashPathEffect的第二个参数（phase）我称之为偏移值，动态改变其值会让路径产生动画的效果。
	 */
	private void DashPathEffect1(Canvas canvas) {
		//loat[] {20, 10}的偶数参数20（注意数组下标是从0开始哦）定义了我们第一条实线的长度，
		// 而奇数参数10则表示第一条虚线的长度，如果此时数组后面不再有数据则重复第一个数以此往复循环，
		// 整条线就成了[20,10,20,10,20,10…………………………]这么一个状态
		mStrokeBluePaint.setPathEffect(new DashPathEffect(new float[]{20,10}, phase));
		canvas.drawPath(mPath,mStrokeBluePaint);

		//当然如果你想要对每个实线和虚线进行设置，你可以这样：
		canvas.translate(0,200);
		DashPathEffect mEffect =  new DashPathEffect(new float[] {20, 10, 50, 5, 100, 30, 10, 5}, phase);
		mStrokeBluePaint.setPathEffect(mEffect);
		canvas.drawPath(mPath,mStrokeBluePaint);

		phase++;
		invalidate();
	}

	/**
	 * （离散路径效果）相对来说则稍微复杂点，其会在路径上绘制很多“杂点”的突出来模拟一种类似生锈铁丝的效果。
	 * 其构造方法有两个参数：
	 *  public DiscretePathEffect(float segmentLength, float deviation)
	 * 第一个呢指定这些突出的“杂点”的密度，值越小杂点越密集；
	 * 第二个参数呢则是“杂点”突出的大小，值越大突出的距离越大反之反之。
	 */
	private void DiscretePathEffect1(Canvas canvas) {
		mStrokeBluePaint.setPathEffect(new DiscretePathEffect(10.0F, 2.0F));
		canvas.drawPath(mPath,mStrokeBluePaint);
	}

	/**
	 * CornerPathEffect则可以将路径的转角变得圆滑，构造方法只接受一个参数radius，意思就是转角处的圆滑程度。
	 */
	private void CornerPathEffect1(Canvas canvas) {
		mStrokeBluePaint.setPathEffect(new CornerPathEffect(50));
		canvas.drawPath(mPath,mStrokeBluePaint);

	}

}
