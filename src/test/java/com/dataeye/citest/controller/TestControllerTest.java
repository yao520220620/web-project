package com.dataeye.citest.controller;

import com.dataeye.citest.base.InitMockMvcJunit;
import com.dataeye.citest.base.MockMvcParameter;
import com.dataeye.citest.dao.TestDao;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author yaolh
 * @version 创建时间：2017/8/7 14:35
 */
public class TestControllerTest extends InitMockMvcJunit {
	@Resource
	private TestDao testDao;
	
	@Test
	public void test() {
		testController(new MockMvcParameter());
	}
	
	@Test
	public void test2() {
		System.out.println(testDao.list("localhost", "root"));
	}
	
	@Test
	public void test3() {
		for (Method m : TestDao.class.getMethods()) {
			System.out.println("----------------------------------------");
			System.out.println("   method: " + m.getName());
			System.out.println("   return: " + m.getReturnType().getName());
			for (Parameter p : m.getParameters()) {
				System.out.println("parameter: " + p.getType().getName() + ", " + p.getName());
				System.out.println("isNamePresent: " + p.isNamePresent());
				System.out.println( "Parameter: " + p.getName() );
			}
		}
	}
}
