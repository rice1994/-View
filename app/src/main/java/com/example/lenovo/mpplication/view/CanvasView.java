package com.example.lenovo.mpplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fan on 2018/4/27.
 */
public class CanvasView extends View {
	public CanvasView(Context context) {
		super(context);
	}

	public CanvasView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		int r = 100;
		RectF rect = new RectF(100, 100, 700, 700);
		Paint paint1 = new Paint();
		paint1.setColor(Color.RED);
		int[] ints = {100, 200, 400, 200, 600, 120};
		int[] ints1 = new int[6];
		int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69};
		int total = 0;
		for (int i : ints) {
			total += i;
		}
		for (int i = 0; i < ints.length; i++) {
			paint1.setColor(mColors[i]);
			if (i == 0) {
				ints1[i] = ints[i] * 360 / total;
				canvas.drawArc(rect, -0, ints1[i], true, paint1);
			} else if (i == ints.length - 1) {
				canvas.drawArc(rect, ints1[i - 1], 360 - ints1[i - 1], true, paint1);
			} else {
				ints1[i] = ints[i] * 360 / total + ints1[i - 1];
				canvas.drawArc(rect, ints1[i-1], ints1[i]-ints1[i-1], true, paint1);
			}
		}

	}

}
