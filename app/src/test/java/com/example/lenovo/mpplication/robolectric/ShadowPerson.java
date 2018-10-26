package com.example.lenovo.mpplication.robolectric;

import com.example.lenovo.mpplication.test.Person;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by fan on 2018/10/19.
 * Copyright  2018 www.yylending.com. All Rights Reserved.
 */
@Implements(Person.class)
public class ShadowPerson {

	@Implementation
	public String getName() {
		return "AndroidUT";
	}

	@Implementation
	public int getSex() {
		return 0;
	}

	@Implementation
	public int getAge(){
		return 18;
	}
}
