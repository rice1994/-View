package com.example.lenovo.mpplication.view.canvas_image_text;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

/**
 * Canvas之图片文字
 */
public class CanvasImageTextActivity extends Activity {
	@BindView(R.id.db)
	DrawBitmap mDb;
	@BindView(R.id.dt)
	DrawTextView mDt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canvas_image_text);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.DrawBitmap1, R.id.DrawBitmap2, R.id.DrawBitmap3, R.id.DrawText1,
			R.id.DrawText2, R.id.DrawText3, R.id.DrawBitmapAndText})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.DrawBitmap1:
				mDb.resetView(DrawBitmap.DrawBitmap1);
				mDb.setVisibility(View.VISIBLE);
				mDt.setVisibility(View.GONE);
				break;
			case R.id.DrawBitmap2:
				mDb.resetView(DrawBitmap.DrawBitmap2);
				mDb.setVisibility(View.VISIBLE);
				mDt.setVisibility(View.GONE);
				break;
			case R.id.DrawBitmap3:
				mDb.resetView(DrawBitmap.DrawBitmap3);
				mDb.setVisibility(View.VISIBLE);
				mDt.setVisibility(View.GONE);
				break;
			case R.id.DrawText1:
				mDt.resetView(DrawTextView.DrawText1);
				mDt.setVisibility(View.VISIBLE);
				mDb.setVisibility(View.GONE);
				break;
			case R.id.DrawText2:
				mDt.resetView(DrawTextView.DrawText2);
				mDt.setVisibility(View.VISIBLE);
				mDb.setVisibility(View.GONE);
				break;
			case R.id.DrawText3:
				mDt.resetView(DrawTextView.DrawText3);
				mDt.setVisibility(View.VISIBLE);
				mDb.setVisibility(View.GONE);
				break;
			case R.id.DrawBitmapAndText:
				mDb.resetView(DrawBitmap.DrawBitmapAndText);
				mDb.setVisibility(View.VISIBLE);
				mDt.setVisibility(View.GONE);
				break;
		}
	}
}
