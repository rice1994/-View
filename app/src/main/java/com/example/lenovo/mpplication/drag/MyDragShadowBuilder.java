package com.example.lenovo.mpplication.drag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by fan on 2018/8/20.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
public class MyDragShadowBuilder extends View.DragShadowBuilder {
	private Drawable shadow;

	public MyDragShadowBuilder(View view) {
		super(view);
		this.shadow = new ColorDrawable(Color.CYAN);
	}

	/**
	 * The system calls this method immediately after you call startDrag().
	 * Use it to send to the system the dimensions and touch point of the drag shadow.
	 */
	// 定义一个回调方法，将阴影的维度和触摸点返回给系统
	@Override
	public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
		super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint);
		int width = getView().getWidth() / 2;
		int height = getView().getHeight() / 2;
		// 拖拽阴影是一个ColorDrawable. 这个集合的维度和系统所提供的Canvas是一样的
		// 因此，拖拽阴影将会被Canvas覆盖
		shadow.setBounds(0,0,width,height);
		outShadowSize.set(width,height);
		outShadowTouchPoint.set(width/2,height/2);
	}

	/**
	 *	// 在画布Canvas中定义一个回调函数来绘制拖拽的阴影，该画布是通过方法onProvideShadowMetrics()提供的维度
	 // 由系统构造
	 * Immediately after the call to onProvideShadowMetrics() the system calls onDrawShadow()
	 * to get the drag shadow itself. The method has a single argument, a Canvas object
	 * that the system constructs from the parameters you provide in onProvideShadowMetrics()
	 * Use it to draw the drag shadow in the provided Canvas object.
	 * @param canvas
	 */
	@Override
	public void onDrawShadow(Canvas canvas) {
		super.onDrawShadow(canvas);
		shadow.draw(canvas);
	}
}
