package com.example.lenovo.mpplication.robolectric;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.lenovo.mpplication.BuildConfig;
import com.example.lenovo.mpplication.CustomViewActivity;
import com.example.lenovo.mpplication.R;
import com.example.lenovo.mpplication.test.RobolectricActivity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;

/**
 * Created by fan on 2018/10/19.
 *
 * Robolectric定义了大量模拟Android系统类行为的Shadow类
 *
 */
//指定运行器
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class RobolectricTest {

	private RobolectricActivity mActivity;
	private Button mJumpBtn;
	private Button mToastBtn;
	private Button mDialogBtn;
	private Button mInverseBtn;
	private CheckBox checkBox;

	@Before
	public void setUp(){
		//输出日志
		ShadowLog.stream = System.out;
		// 默认会调用Activity的生命周期: onCreate->onStart->onResume

		mActivity = Robolectric.setupActivity(RobolectricActivity.class);
		mJumpBtn = (Button) mActivity.findViewById(R.id.button1);
		mToastBtn = (Button) mActivity.findViewById(R.id.button2);
		mDialogBtn = (Button) mActivity.findViewById(R.id.button3);
		mInverseBtn = (Button) mActivity.findViewById(R.id.button4);
		checkBox = (CheckBox) mActivity.findViewById(R.id.checkbox);
	}

	//Activity跳转验证
	@Test
	public void testJump(){
		// 触发按钮点击
		mActivity.findViewById(R.id.custom_view).performClick();
		// 获取对应的Shadow类
		ShadowActivity shadowActivity = Shadows.shadowOf(mActivity);
		// 借助Shadow类获取启动下一Activity的Intent
		Intent nextIntent = shadowActivity.getNextStartedActivity();
		// 校验Intent的正确性
		Assert.assertEquals(nextIntent.getComponent().getClassName(), CustomViewActivity.class.getName());
	}

	//Activity生命周期
	@Test
	public void testLifecycle(){
		//创建Activity控制器
		ActivityController<RobolectricActivity> controller = Robolectric.buildActivity(RobolectricActivity.class);
		RobolectricActivity activity = controller.get();
		Assert.assertNull(activity.getLifecycleState());
		controller.create();

		// 调用Activity的performCreate方法
		Assert.assertEquals("onCreate", activity.getLifecycleState());

		// 调用Activity的performStart方法
		controller.start();
		Assert.assertEquals("onStart", activity.getLifecycleState());

		// 调用Activity的performPause方法
		controller.resume();
		Assert.assertEquals("onResume", activity.getLifecycleState());

		// 调用Activity的performStop方法
		controller.pause();
		Assert.assertEquals("onPause", activity.getLifecycleState());

		// 调用Activity的performRestart方法
		controller.stop();
		Assert.assertEquals("onStop", activity.getLifecycleState());

		// 调用Activity的performDestroy方法
		controller.destroy();
		Assert.assertEquals("onDestroy", activity.getLifecycleState());
	}

	//验证Toast
	@Test
	public void testToast() throws Exception {
		Toast toast = ShadowToast.getLatestToast();
		Assert.assertNull(toast);
		mToastBtn.performClick();
		toast = ShadowToast.getLatestToast();
		//判断Toast已经弹出
		Assert.assertNotNull(toast);
		//获取Shadow类进行认证
		ShadowToast shadowToast = Shadows.shadowOf(toast);
		Assert.assertEquals(Toast.LENGTH_LONG,shadowToast.getDuration());
		Assert.assertEquals("Hello UT!",shadowToast.getTextOfLatestToast());

	}

	//验证Dialog
	@Test
	public void testDialog() throws Exception {
		AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
		// 判断Dialog尚未弹出
		Assert.assertNull(dialog);
		mDialogBtn.performClick();
		dialog = ShadowAlertDialog.getLatestAlertDialog();
		// 判断Dialog已经弹出
		Assert.assertNotNull(dialog);
		ShadowAlertDialog shadowAlertDialog = Shadows.shadowOf(dialog);
		Assert.assertEquals("Hello UT！",shadowAlertDialog.getMessage());
		Assert.assertEquals("提示",shadowAlertDialog.getTitle());
	}

	@Test
	public void testCheckBoxState() throws Exception {
		Assert.assertFalse(checkBox.isChecked());

		mInverseBtn.performClick();
		Assert.assertTrue(checkBox.isChecked());

		mInverseBtn.performClick();
		Assert.assertFalse(checkBox.isChecked());
	}

	//访问资源文件
	@Test
	public void testResources() {
		Application application = RuntimeEnvironment.application;
		String appName = application.getString(R.string.app_name);
		Assert.assertEquals("Mpplication", appName);
	}


}
