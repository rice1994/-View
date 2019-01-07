package com.example.lenovo.mpplication.mockito;

import com.example.lenovo.mpplication.DateFormatTest;
import com.example.lenovo.mpplication.DateUtilTest;
import com.example.lenovo.mpplication.test.Home;
import com.example.lenovo.mpplication.test.Person;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.Suite;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by fan on 2018/10/18.
 */
@Suite.SuiteClasses({DateUtilTest.class, DateFormatTest.class})
public class MockitoTest {

	@Mock
	Person person;

	@Spy
	Person person1;

	@InjectMocks
	Home mHome;

	@Test
	public void testHomeInjectMocks(){
		when(person1.getName()).thenReturn("小牛1");
		System.out.print(mHome.getMaster());
	}


	@Rule
	public MockitoRule mMockitoRule = MockitoJUnit.rule();


	@Test
	public void test1() {
//		Person person = mock(Person.class);
		when(person.getName()).thenReturn("小牛宝宝快快来");
		when(person.getAge()).thenReturn(15);

		System.out.println(person.getName());
		doReturn("哈哈哈").when(person).getName();

		System.out.println(person.getName());
		System.out.println(person.getAge());
		doReturn(222).when(person).getAge();

		System.out.println(person.getAge());
		assertNotNull(person.getName());
		assertNotNull(person.getAge());

		reset(person);
		person.getAge();
		person.getAge();
		verify(person,times(2)).getAge();
		verify(person,atLeast(2)).getAge();

		//匹配String对象
		when(person.eat(any(String.class))).thenReturn("米饭");
		System.out.println(person.eat("面条"));
		//参数包含给定的substring字符串
		when(person.eat(contains("面"))).thenReturn("面条");
		System.out.println(person.eat("面"));

		//argThat(ArgumentMatcher <T> matcher)	创建自定义的参数匹配模式
		when(person.eat(argThat(new ArgumentMatcher<String>() {
			@Override
			public boolean matches(String argument) {
				return argument.length()%2!=0;
			}
		}))).thenReturn("小牛宝宝");


		//比较mock和spy的区别

		System.out.println("person--"+person.getName());
		System.out.println("person1--"+person1.getName());
		//结果：person--null
		//		person1--默认值
		when(person.getName()).thenReturn("小牛宝宝快快来");
		when(person1.getName()).thenReturn("小牛宝宝快快来");

		System.out.println("person--"+person.getName());
		System.out.println("person1--"+person1.getName());
		//结果一样


	}
}
