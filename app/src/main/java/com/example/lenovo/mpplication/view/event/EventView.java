package com.example.lenovo.mpplication.view.event;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.example.lenovo.mpplication.view.base.BaseView;

/**
 * getActionMasked()	与 getAction() 类似，多点触控必须使用这个方法获取事件类型。
 * getActionIndex()	获取该事件是哪个指针(手指)产生的。
 * getPointerCount()	获取在屏幕上手指的个数。
 * getPointerId(int pointerIndex)	获取一个指针(手指)的唯一标识符ID，在手指按下和抬起之间ID始终不变。
 * findPointerIndex(int pointerId)	通过PointerId获取到当前状态下PointIndex，之后通过PointIndex获取其他内容。
 * getX(int pointerIndex)	获取某一个指针(手指)的X坐标
 * getY(int pointerIndex)	获取某一个指针(手指)的Y坐标
 */
public class EventView extends BaseView {
	private static final String TAG = EventView.class.getSimpleName();
	private PointF point;
	private boolean haveSecondPoint;


	public EventView(Context context) {
		super(context);
		init();
	}

	public EventView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		point = new PointF();
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			/** 手指 初次接触到屏幕 时触发。 */
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, "Action--MotionEvent.ACTION_DOWN:");
				break;
			/** 手指 在屏幕上滑动 时触发，会多次触发。 */
			case MotionEvent.ACTION_MOVE:
				Log.i(TAG, "Action--MotionEvent.ACTION_MOVE:");
				break;
			/**  手指 离开屏幕 时触发。 */
			case MotionEvent.ACTION_UP:
				Log.i(TAG, "Action--MotionEvent.ACTION_UP:");
				break;
			/**  事件 被上层拦截 时触发。
			 * 例如：上层 View 是一个 RecyclerView，它收到了一个 ACTION_DOWN 事件，由于这个可能是点击事件，
			 // 所以它先传递给对应 ItemView，询问 ItemView 是否需要这个事件，
			 // 然而接下来又传递过来了一个 ACTION_MOVE 事件，且移动的方向和 RecyclerView 的可滑动方向一致，
			 // 所以 RecyclerView 判断这个事件是滚动事件，于是要收回事件处理权，
			 // 这时候对应的 ItemView 会收到一个 ACTION_CANCEL ，并且不会再收到后续事件。 */
			case MotionEvent.ACTION_CANCEL:
				Log.i(TAG, "Action--MotionEvent.ACTION_CANCEL:");
				break;
			/**  手指 不在控件区域 时触发。
			 * 例如Dialog 就是一个特殊的视图(没有占满屏幕大小的窗口)，
			 能够接收到视图区域外的事件(虽然在通常情况下你根本用不到这个事件)*/
			case MotionEvent.ACTION_OUTSIDE:
				Log.i(TAG, "Action--MotionEvent.ACTION_OUTSIDE:");
				break;
			/**  有非主要的手指按下(即按下之前已经有手指在屏幕上)。 */
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.i(TAG, "Action--MotionEvent.ACTION_POINTER_DOWN:");
				break;
			/**  有非主要的手指抬起(即抬起之后仍然有手指在屏幕上)。  */
			case MotionEvent.ACTION_POINTER_UP:
				Log.i(TAG, "Action--MotionEvent.ACTION_POINTER_UP:");
				break;

		}
		/**
		 *
		 * getActionMasked()	与 getAction() 类似，多点触控必须使用这个方法获取事件类型。
		 getActionIndex()	获取该事件是哪个指针(手指)产生的。只在 down 和 up 时有效，move 时是无效的
		 getPointerCount()	获取在屏幕上手指的个数。
		 getPointerId(int pointerIndex)	获取一个指针(手指)的唯一标识符ID，在手指按下和抬起之间ID始终不变。
		 findPointerIndex(int pointerId)	通过PointerId获取到当前状态下PointIndex，之后通过PointIndex获取其他内容。
		 getX(int pointerIndex)	获取某一个指针(手指)的X坐标
		 getY(int pointerIndex)	获取某一个指针(手指)的Y坐标
		 */
		final int action = event.getActionMasked();
//		switch (action) {
//			//遍历出多个手指的 move 事件：
//			case MotionEvent.ACTION_MOVE:
//				int pointerCount = event.getPointerCount();
//				for(int i=0;i<pointerCount;i++)
//					Log.i(TAG, "pointerIndex="+i+", pointerId="+event.getPointerId(i));
//					break;
//		}

		int index = event.getActionIndex();
		switch (action) {
			case MotionEvent.ACTION_POINTER_DOWN:
				if (event.getPointerId(index) == 1) {
					haveSecondPoint = true;
					point.set(event.getX(), event.getY());
				}
				break;
			case MotionEvent.ACTION_POINTER_UP:
				if (event.getPointerId(index) == 1) {
					point.set(0, 0);
					haveSecondPoint = false;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (haveSecondPoint) {
					// 通过 pointerId 来获取 pointerIndex
					int pointerIndex = event.findPointerIndex(1);
					// 通过 pointerIndex 来取出对应的坐标
					point.set(event.getX(pointerIndex), event.getY(pointerIndex));
				}
				break;
		}
		invalidate();   // 刷新
		/** 历史数据(批处理)
		 *
		 * getHistorySize()	获取历史事件集合大小
		 getHistoricalX(int pos)	获取第pos个历史事件x坐标
		 (pos < getHistorySize())
		 getHistoricalY(int pos)	获取第pos个历史事件y坐标
		 (pos < getHistorySize())
		 getHistoricalX (int pin, int pos)	获取第pin个手指的第pos个历史事件x坐标
		 (pin < getPointerCount(), pos < getHistorySize() )
		 getHistoricalY (int pin, int pos)	获取第pin个手指的第pos个历史事件y坐标
		 (pin < getPointerCount(), pos < getHistorySize() )
		 */

		/**获取事件发生的时间
		 *
		 * getDownTime()	获取手指按下时的时间。
		 getEventTime()	获取当前事件发生的时间。
		 getHistoricalEventTime(int pos)	获取历史事件发生的时间。
		 */

		/**获取压力(接触面积大小)
		 *
		 * getSize ()	获取第1个手指与屏幕接触面积的大小
		 getSize (int pin)	获取第pin个手指与屏幕接触面积的大小
		 getHistoricalSize (int pos)	获取历史数据中第1个手指在第pos次事件中的接触面积
		 getHistoricalSize (int pin, int pos)	获取历史数据中第pin个手指在第pos次事件中的接触面积
		 getPressure ()	获取第一个手指的压力大小
		 getPressure (int pin)	获取第pin个手指的压力大小
		 getHistoricalPressure (int pos)	获取历史数据中第1个手指在第pos次事件中的压力大小
		 getHistoricalPressure (int pin, int pos)	获取历史数据中第pin个手指在第pos次事件中的压力大小
		 */

		//备注：
		// 1、MotionEvent.ACTION_CANCEL
		//
		//2、MotionEvent.ACTION_OUTSIDE
		//
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(haveSecondPoint)
			canvas.save();
		canvas.translate(50, mHeight/2);
		mBlackPaint.setTextSize(40);
		mBlackPaint.setStrokeWidth(2);
		canvas.drawText("追踪第2个按下手指的位置 x="+point.x+",y="+point.y, 0, 0, mBlackPaint);
		canvas.restore();
			canvas.drawCircle(point.x, point.y, 50, mBluePaint);
	}

	/**
	 * 专门响应一些外接设备的操作的，比如游戏手柄，鼠标，滚轮，触控板等
	 * <p>
	 * 设备类型	简介
	 * TOOL_TYPE_ERASER	橡皮擦
	 * TOOL_TYPE_FINGER	手指
	 * TOOL_TYPE_MOUSE	鼠标
	 * TOOL_TYPE_STYLUS	手写笔
	 * TOOL_TYPE_UNKNOWN	未知类型
	 * <p>
	 * 使用 getToolType(int pointerIndex) 来获取对应的输入设备类型，pointIndex可以为0，但必须小于 getPointerCount()。
	 * <p>
	 * 事件	简介
	 * ACTION_HOVER_ENTER	指针移入到窗口或者View区域，但没有按下。
	 * ACTION_HOVER_MOVE	指针在窗口或者View区域移动，但没有按下。
	 * ACTION_HOVER_EXIT	指针移出到窗口或者View区域，但没有按下。
	 * ACTION_SCROLL	滚轮滚动，可以触发水平滚动(AXIS_HSCROLL)或者垂直滚动(AXIS_VSCROLL)
	 */
	@Override
	public boolean onGenericMotionEvent(MotionEvent event) {
		return super.onGenericMotionEvent(event);
	}
}
