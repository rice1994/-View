package com.example.lenovo.mpplication.view.canvas_image_text;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * 绘制文字
 * <p>
 * // 第一类只能指定文本基线位置(基线x默认在字符串左侧，基线y默认在字符串下方)。
 * public void drawText (String text, float x, float y, Paint paint)
 * public void drawText (String text, int start, int end, float x, float y, Paint paint)
 * public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
 * public void drawText (char[] text, int index, int count, float x, float y, Paint paint)
 * <p>
 * // 第二类可以分别指定每个文字的位置。
 * public void drawPosText (String text, float[] pos, Paint paint)
 * public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
 * <p>
 * // 第三类是指定一个路径，根据路径绘制文字。
 * public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
 * public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
 * <p>
 * Paint文本相关常用方法表
 * <p>
 * 标题	相关方法	                 备注
 * 色彩	setColor setARGB setAlpha	 设置颜色，透明度
 * 大小	setTextSize	                 设置文本字体大小
 * 字体	setTypeface	                 设置或清除字体样式
 * 样式	setStyle	                 填充(FILL),描边(STROKE),填充加描边(FILL_AND_STROKE)
 * 对齐	setTextAlign	             左对齐(LEFT),居中对齐(CENTER),右对齐(RIGHT)
 * 测量	measureText	                 测量文本大小(注意，请在设置完文本各项参数后调用)
 */
public class DrawTextView extends BaseView {
	public final static int DrawText1 = 1;
	public final static int DrawText2 = 2;
	public final static int DrawText3 = 3;

	public DrawTextView(Context context) {
		super(context);
	}

	public DrawTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		switch (mType) {
			case DrawText1:
				onDrawText1(canvas);
				break;
			case DrawText2:
				onDrawText2(canvas);

				break;
			case DrawText3:
				onDrawText3(canvas);

				break;
		}
	}

	/**
	 * // 第一类只能指定文本基线位置(基线x默认在字符串左侧，基线y默认在字符串下方)。
	 * public void drawText (String text, float x, float y, Paint paint)
	 * public void drawText (String text, int start, int end, float x, float y, Paint paint)
	 * public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
	 * public void drawText (char[] text, int index, int count, float x, float y, Paint paint)
	 */

	void onDrawText1(Canvas canvas) {
		canvas.drawText("ABCDEFG", 200, 200, mBlackPaint);
		canvas.drawText("ABCDEFG", 1, 3, 200, 500, mBlackPaint);
		canvas.drawText("ABCDEFG".toCharArray(), 3, 2, 200, 800, mBlackPaint);
	}

	/**
	 * 第二类可以分别指定每个文字的位置。
	 * public void drawPosText (String text, float[] pos, Paint paint)
	 * public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
	 * <p>
	 * 不过嘛，虽然虽然这个方法也比较容易理解，但是关于这个方法我个人是不推荐使用的，
	 * 因为坑比较多，主要有一下几点：
	 * <p>
	 * 序号	反对理由
	 * 1	必须指定所有字符位置，否则直接crash掉，反人类设计
	 * 2	性能不佳，在大量使用的时候可能导致卡顿
	 * 3	不支持emoji等特殊字符，不支持字形组合与分解
	 */
	void onDrawText2(Canvas canvas) {
		String str = "SLOOP";

		canvas.drawPosText(str, new float[]{
				100, 100,    // 第一个字符位置
				200, 200,    // 第二个字符位置
				300, 300,    // ...
				400, 400,
				500, 500
		}, mBlackPaint);
	}

	/**
	 * // 第三类是指定一个路径，根据路径绘制文字。
	 * public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
	 * public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
	 * <p>
	 * 示例待定
	 */
	void onDrawText3(Canvas canvas) {

	}
}
