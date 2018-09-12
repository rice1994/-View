package com.example.lenovo.mpplication.animation;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class AnimateLayoutChangesActivity extends AppCompatActivity {

	private LinearLayout layoutTransitionGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animate_layout_changes);
		ButterKnife.bind(this);
		layoutTransitionGroup = (LinearLayout) findViewById(R.id.layoutTransitionGroup);
		LayoutTransition transition = new LayoutTransition();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translationX", 0f,-dm.widthPixels);
		transition.setAnimator(LayoutTransition.DISAPPEARING,animOut);
		layoutTransitionGroup.setLayoutTransition(transition);
	}

	@OnClick({R.id.add_btn, R.id.remove_btn})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.add_btn:
				addView();
				break;
			case R.id.remove_btn:
				layoutTransitionGroup.removeViewAt(0);
				break;
		}
	}

	private void addView(){
		final View view = LayoutInflater.from(this).inflate(R.layout.list_item_example, layoutTransitionGroup, false);
		// 随机设置子View的内容
		((TextView) view.findViewById(android.R.id.text1)).setText(
				COUNTRIES[(int) (Math.random() * COUNTRIES.length)]);
		view.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				layoutTransitionGroup.removeView(view);
			}
		});

		layoutTransitionGroup.addView(view,0);
	}

	/**
	 * A static list of country names.
	 */
	private static final String[] COUNTRIES = new String[]{
			"Belgium", "France", "Italy", "Germany", "Spain",
			"Austria", "Russia", "Poland", "Croatia", "Greece",
			"Ukraine",
	};

}
