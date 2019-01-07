package com.example.lenovo.mpplication;

import com.example.lenovo.mpplication.test.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by fan on 2018/10/18.
 *
 * 参数化测试
 */
@RunWith(Parameterized.class)
public class DateFormatTest {
	private String time;

	public DateFormatTest(String time) {
		this.time = time;
	}

	@Parameterized.Parameters
	public static Collection primeNumbers(){
		return Arrays.asList(
				"2017-10-15",
//				"2017-10-15 16:00:02",// 抛出异常,java.lang.AssertionError: Expected exception: java.text.ParseException
				"2017年10月15日 16时00分02秒");
	}

	@Test(expected = ParseException.class)
	public void dataTostampTest1() throws ParseException {
		DateUtil.dateToStamp(time);
	}
}
