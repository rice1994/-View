package com.example.lenovo.mpplication;

import com.example.lenovo.mpplication.test.DateUtil;
import org.junit.*;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fan on 2018/10/18.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 *
 * 基础用法
 */
public class DateUtilTest {
	private static final String TAG = DateUtilTest.class.getSimpleName();
	private String time = "2017-10-15 16:00:02";
	private long timeStamp = 1508054402000L;
	private Date mDate;

	/**
	 * 此注解修饰的方法必须是static void
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("TEST--beforeClass--在类中所有方法前运行");
	}

	@Before
	public void Before() {
		mDate = new Date();
		mDate.setTime(timeStamp);
		System.out.println("TEST--Before--在每个测试方法前执行，可做初始化操作");
	}

	@Test
	public void stampToDateTest() throws Exception {
		assertEquals(time, DateUtil.stampToDate(timeStamp));
	}

	@Test
	public void dateToStampTest() throws Exception {
		assertNotEquals(4, DateUtil.dateToStamp(time));
	}

	@Test(expected = ParseException.class)
	public void dateToStampTest1() throws Exception {
		DateUtil.dateToStamp("2017-10-15");
	}

	@Test
	public void test1() {
		System.out.println("TEST--Test--表示此方法为测试方法");
	}

	@Ignore
	public void Ignore() {
		System.out.println("TEST--Ignore--忽略的测试方法");
	}

	@After
	public void After() {
		System.out.println("TEST--After--在每个测试方法后执行，可做释放资源操作");
	}

	/**
	 * 此注解修饰的方法必须是static void
	 */
	@AfterClass
	public static void AfterClass() {
		System.out.println("TEST--AfterClass--在类中最后运行");
	}
}
