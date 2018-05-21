package com.example.lenovo.mpplication.view.canvas_image_text;

import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.view.base.BaseView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fan on 2018/5/4.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class DrawBitmap extends BaseView {
	private Bitmap mBitmap = null;
	public final static int DrawBitmap1 = 1;
	public final static int DrawBitmap2 = 2;
	public final static int DrawBitmap3 = 3;

	public DrawBitmap(Context context) {
		super(context);
	}

	public DrawBitmap(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mBitmap = getBitmapFromAssets();
	}

	Bitmap getBitmapFromResource() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pg);
		return bitmap;
	}

	Bitmap getBitmapFromAssets() {
		Bitmap bitmap = null;
		try {
			InputStream is = getResources().getAssets().open("pg.png");
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return bitmap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mType) {
			case DrawBitmap1:
				draw1(canvas);
				break;
			case DrawBitmap2:
				draw2(canvas);
				break;
			case DrawBitmap3:
				draw3(canvas);
				break;
		}
	}

	/**
	 * 第一种
	 * public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint)
	 * 关于Matrix和Paint暂时略过吧，一展开又是啰啰嗦嗦一大段
	 */
	private void draw1(Canvas canvas) {
		canvas.drawBitmap(mBitmap, new Matrix(), new Paint());
	}

	/**
	 * 第二种
	 * public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint)
	 * <p>
	 * 第二种方法就是在绘制时指定了图片左上角的坐标(距离坐标原点的距离)：
	 * 注意：此处指定的是与坐标原点的距离，并非是与屏幕顶部和左侧的距离, 虽然默认状态下两者是重合的，
	 * 但是也请注意分别两者的不同。
	 */
	private void draw2(Canvas canvas) {
		canvas.drawBitmap(mBitmap, 200, 500, new Paint());
	}

	/**
	 * 第三种
	 * public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint)
	 * public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint)
	 * <p>
	 * 第三种方法比较有意思，上面多了两个矩形区域(src,dst),这两个矩形选区是干什么用的？
	 * 名称	                  作用
	 * Rect src	              指定绘制图片的区域
	 * Rect dst 或RectF dst   指定图片在屏幕上显示(绘制)的区域
	 */
	private void draw3(Canvas canvas) {
		// 将画布坐标系移动到画布中央
		canvas.translate(mWidth / 2, mHeight / 2);
		// 指定图片绘制区域(左上角的四分之一)
		Rect src = new Rect(0, 0, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
		// 指定图片在屏幕上显示的区域
		Rect rect1 = new Rect(0, 0, 200, 400);
		// 绘制图片
		canvas.drawBitmap(mBitmap, src, rect1, new Paint());

	}
}
