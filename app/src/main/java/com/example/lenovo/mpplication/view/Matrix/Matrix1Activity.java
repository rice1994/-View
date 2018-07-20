package com.example.lenovo.mpplication.view.Matrix;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.ZoomImageView1;

public class Matrix1Activity extends AppCompatActivity {

	private ViewPager viewPager;

	private int[] mImgs = new int[]{R.mipmap.beauty00, R.mipmap.beauty01, R.mipmap.beauty02, R.mipmap.beauty03};
	private ZoomImageView1[] mImageViews = new ZoomImageView1[mImgs.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_matrix);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				final ZoomImageView1 imageView = new ZoomImageView1(getApplicationContext());
				imageView.setImageResource(mImgs[position]);
				container.addView(imageView);
				mImageViews[position] = imageView;
				imageView.post(new Runnable() {

					@Override
					public void run() {
						Log.i("", "run: getWidth="+imageView.getWidth()+"，getHeight="+imageView.getHeight());
						imageView.getWidth(); // 获取宽度
						imageView.getHeight(); // 获取高度
					}
				});

				return imageView;
			}

			@Override
			public int getCount() {
				return mImgs.length;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(mImageViews[position]);
			}
		});
	}
}
