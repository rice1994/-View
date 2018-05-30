package com.example.lenovo.mpplication.view.Matrix;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.example.lenovo.mpplication.R;

public class MatrixActivity extends AppCompatActivity {

	private ViewPager viewPager;

	private int[] mImgs = new int[]{R.mipmap.beauty00, R.mipmap.beauty01, R.mipmap.beauty02, R.mipmap.beauty03};
	private ZoomImageView[] mImageViews = new ZoomImageView[mImgs.length];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_matrix);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ZoomImageView imageView = new ZoomImageView(getApplicationContext());
				imageView.setImageResource(mImgs[position]);
				container.addView(imageView);
				mImageViews[position] = imageView;
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
