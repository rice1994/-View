package com.example.lenovo.mpplication.powermock;

import com.example.lenovo.mpplication.test.Banana;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;


/**
 * Created by fan on 2018/10/19.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Banana.class})
public class PowerMockTest {

	@Test
	public void textStaticMethod() {
		//mock静态方法
		PowerMockito.mockStatic(Banana.class);
		Mockito.when(Banana.getColor()).thenReturn("绿色");
		Assert.assertEquals("绿色", Banana.getColor());

	}

	@Test
	public void testChangeColor() {
		//修改类的私有static常量
		Whitebox.setInternalState(Banana.class,"COLOR","红色的");
		Assert.assertEquals("红色的", Banana.getColor());
	}

	@Test
	public void textPrivateMethod() throws Exception {
		Banana banana = PowerMockito.mock(Banana.class);

		//mock私有方法
		PowerMockito.when(banana.getBananaInfo()).thenCallRealMethod();
		PowerMockito.when(banana,"flavor").thenReturn("苦苦的");
		Assert.assertEquals("苦苦的黄色的",banana.getBananaInfo());
		PowerMockito.verifyPrivate(banana).invoke("flavor");

		//mock final 方法
		PowerMockito.when(banana.isLike()).thenReturn(false);
		Assert.assertFalse(banana.isLike());

		//mock构造方法
		banana = PowerMockito.mock(Banana.class);
		PowerMockito.when(banana.getBananaInfo()).thenReturn("大香蕉");
		PowerMockito.whenNew(Banana.class).withNoArguments().thenReturn(banana);//如果new新对象，则返回这个上面设置的这个对象
		Banana newBanana = new Banana();
		Assert.assertEquals("大香蕉",newBanana.getBananaInfo());
	}


	@Test
	public void skipPrivateMethod() {
		//跳过私有方法
		Banana banana = new Banana();
		PowerMockito.suppress(PowerMockito.method(Banana.class,"flavor"));
		Assert.assertEquals("null黄色的", banana.getBananaInfo());
	}

	@Test
	public void testChangeParentPrivate() throws IllegalAccessException {
		//更改父类私有变量
		Banana banana = new Banana();
		MemberModifier.field(Banana.class,"fruit").set(banana,"蔬菜");
		Assert.assertEquals("蔬菜", banana.getFruit());
	}

	@Test
	public void testFinalMethod() throws Exception {

	}
}
