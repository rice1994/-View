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
 */
public class DrawBitmap extends BaseView {
	private Bitmap mBitmap = null;
	public final static int DrawBitmap1 = 1;
	public final static int DrawBitmap2 = 2;
	public final static int DrawBitmap3 = 3;
	public final static int DrawBitmapAndText = 4;

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
			case DrawBitmapAndText:
				DrawBitmapAndText(canvas);

				break;
		}
	}

	/**
	 * 水印
	 */
	private void DrawBitmapAndText(Canvas canvas) {
		//画布
		int w = mBitmap.getWidth();
		int h = mBitmap.getHeight();
		Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas1 = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setTextSize(30);
		paint.setAntiAlias(true);
		canvas1.drawBitmap(mBitmap, 0, 0, paint);

		String text = "张三 2018/05/22 19:52 16:22";
//		Rect bounds = new Rect();
//		paint.getTextBounds(text, 0, text.length(), bounds);

		canvas1.drawText(text, 24, h-24, paint);
//		canvas1.drawText(text, 24, h-(bounds.bottom-bounds.top), paint);
		canvas1.save();
		canvas1.restore();

//		canvas.translate(mWidth,mHeight);
//		canvas.scale(-1,-1);
		canvas.drawBitmap(bmp, 10, 10, new Paint());
	}

	//图片上绘制文字
	private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
			Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
		android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

		paint.setDither(true); // 获取跟清晰的图像采样
		paint.setFilterBitmap(true);// 过滤一些
		if (bitmapConfig == null) {
			bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);
		Canvas canvas = new Canvas(bitmap);

		canvas.drawText(text, paddingLeft, paddingTop, paint);
		return bitmap;
	}
	/**
	 * 绘制文字到左下方
	 * @param context
	 * @param bitmap
	 * @param text
	 * @param size
	 * @param color
	 * @param paddingLeft
	 * @param paddingBottom
	 * @return
	 */
//	public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text,
//			int size, int color, int paddingLeft, int paddingBottom) {
//		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		paint.setColor(color);
//		paint.setTextSize(dp2px(context, size));
//		Rect bounds = new Rect();
//		paint.getTextBounds(text, 0, text.length(), bounds);
//		return drawTextToBitmap(context, bitmap, text, paint, bounds,
//				dp2px(context, paddingLeft),
//				bitmap.getHeight() - dp2px(context, paddingBottom));
//	}
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
