package com.example.lenovo.mpplication.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.*;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.lenovo.mpplication.R;

public class TransitionFrameworkActivity extends AppCompatActivity {

	@BindView(R.id.scene_root)
	FrameLayout mSceneRoot;
	private Scene mAScene;
	private Scene mAnotherScene;
	private boolean isScene2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transition_framework);
		ButterKnife.bind(this);
		// Create the scenes
		mAScene = Scene.getSceneForLayout(mSceneRoot, R.layout.a_scene, this);
		mAnotherScene = Scene.getSceneForLayout(mSceneRoot, R.layout.another_scene, this);
		TransitionManager.go(mAScene);
		//从资源文件创建转换实例
		Transition fadeTransition = TransitionInflater.from(this).inflateTransition(R.transition.fade_transition);
		//在代码中创建转换实例
		Fade fade = new Fade();

	}

	@OnClick(R.id.switch_btn)
	public void onClick() {
		TransitionManager.go(isScene2?mAScene:mAnotherScene,new ChangeBounds());
		isScene2=!isScene2;
	}
}
