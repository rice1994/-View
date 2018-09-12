package com.example.lenovo.mpplication.drag;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.lenovo.mpplication.R;

public class DrayAndDropActivity extends AppCompatActivity {
	private static final String IMAGEVIEW_TAG = "icon bitmap";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dray_and_drop);
		final ImageView imageView = findViewById(R.id.drag_image);
		View btn = findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				imageView.animate().setDuration(2000).alpha(0f).setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						imageView.setVisibility(View.GONE);
					}
				});
			}
		});
		imageView.setImageResource(R.drawable.ic_launcher);
//		imageView.setTag(IMAGEVIEW_TAG);
//		imageView.setOnLongClickListener(new View.OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
//				ClipData dragData = new ClipData((CharSequence) v.getTag(),new String[] {
//						ClipDescription.MIMETYPE_TEXT_PLAIN },item);
//				MyDragShadowBuilder myDragShadowBuilder = new MyDragShadowBuilder(imageView);
//				return v.startDragAndDrop(dragData,myDragShadowBuilder,null,0);
//			}
//		});
//		imageView.setOnDragListener(new myDragEventListener());
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(DrayAndDropActivity.this, "onClick", Toast.LENGTH_SHORT).show();
			}
		});
	}


	@SuppressLint("NewApi")
	protected class myDragEventListener implements View.OnDragListener {

		// 该方法由系统调用，当有拖拽事件发生时
		@SuppressLint("ShowToast")
		public boolean onDrag(View v, DragEvent event) {
			// 定义一个变量来存储通过事件传递的action类型
			final int action = event.getAction();
			// 每个事件的处理
			switch (action) {

				case DragEvent.ACTION_DRAG_STARTED:
					System.out.println("ACTION_DRAG_STARTED----------------");
					// 确定是否这个视图（View）可以接收拖拽的数据类型
					if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
						// 这里是一个例子，通过更改TextView的背景颜色和文字
						// 来证明它可以接收数据
						v.setBackgroundColor(Color.BLUE);
						// 强制重绘视图以显示新的特性
						v.invalidate();
						// 通过返回true来表明View可以接收拖拽数据
						return (true);

					} else {
						// 返回false. 在当前是拖拽和落下操作时，视图（View）将不再接收
						// 事件直到发送ACTION_DRAG_ENDED
						return (false);
					}

				case DragEvent.ACTION_DRAG_ENTERED:
					System.out.println("ACTION_DRAG_ENTERED----------------");
					// 拖拽的阴影已经到达指定的区域，TextView背景变为绿色，返回true，该返回值没有意义
					v.setBackgroundColor(Color.GREEN);
					// 强制重绘视图以显示新的特性
					v.invalidate();
					return (true);

				case DragEvent.ACTION_DRAG_LOCATION:
					// 忽略该事件
					return (true);

				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println("ACTION_DRAG_EXITED----------------");
					// 拖拽阴影超出制定区域，重置TextView背景色为蓝色
					v.setBackgroundColor(Color.BLUE);
					// 强制重绘视图以显示新的特性
					v.invalidate();
					return (true);

				case DragEvent.ACTION_DROP:
					System.out.println("ACTION_DROP----------------");
					// 获得item包括拖拽数据
					ClipData.Item item = event.getClipData().getItemAt(0);
					// 从item获得文本数据
					CharSequence dragData = item.getText();
					// 显示拖拽数据中包含的信息.
					Toast.makeText(DrayAndDropActivity.this, "Dragged data is: " + dragData, Toast.LENGTH_SHORT).show();
					// 重新设置颜色和文字
					v.setBackgroundColor(Color.WHITE);
					// 强制重绘视图以显示新的特性
					v.invalidate();

					// 返回true. DragEvent.getResult()将会返回true.
					return (true);

				case DragEvent.ACTION_DRAG_ENDED:
					System.out.println("ACTION_DRAG_ENDED----------------");
					// 重新设置颜色和文字
					v.setBackgroundColor(Color.WHITE);
					// 强制重绘视图以显示新的特性
					v.invalidate();
					//通过getResult()方法的返回值判断发生了什么
					if (event.getResult()) {
						Toast.makeText(DrayAndDropActivity.this, "The drop was handled.", Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(DrayAndDropActivity.this, "The drop didn't work.", Toast.LENGTH_LONG).show();

					};
					return (true);

				// 其他未知的action.
				default:
					Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");

					break;
			}
			;
			return true;
		};
	}

}
