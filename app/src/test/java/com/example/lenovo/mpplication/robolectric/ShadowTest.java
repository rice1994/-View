package com.example.lenovo.mpplication.robolectric;

import android.util.Log;
import com.example.lenovo.mpplication.BuildConfig;
import com.example.lenovo.mpplication.test.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.shadow.api.Shadow.extract;

/**
 * Created by fan on 2018/10/19.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
		sdk = 23,
		shadows = {ShadowPerson.class})
public class ShadowTest {

	@Before
	public void setUp() {
		ShadowLog.stream = System.out;
	}

	@Test
	public void testShadowShadow() {
		Person person = new Person();
		//实际上调用的是ShadowPerson的方法
		Log.d("test", person.getName());
		Log.d("test", String.valueOf(person.getAge()));
		Log.d("test", String.valueOf(person.getSex()));

		//获取Person对象对应的Shadow对象
		ShadowPerson shadowPerson = extract(person);
		assertEquals("AndroidUT", shadowPerson.getName());
		assertEquals(18, shadowPerson.getAge());
		assertEquals(0, person.getSex());
	}
}