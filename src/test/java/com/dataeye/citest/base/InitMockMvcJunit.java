package com.dataeye.citest.base;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/springMvc.xml")
@ComponentScan(basePackages = "com.dataeye",
		       includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = {Controller.class})})
public class InitMockMvcJunit {
	@Resource
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	
	// 加载logback.xml
	static {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(lc);
		lc.reset();
		try {
			URL logbackUrl = new ClassPathResource("profile/logback.xml").getURL();
			configurator.doConfigure(logbackUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	/**
	 * 测试Controller对应的方法
	 * Created by yaolh on 2017/4/26 11:10.
	 */
	protected void testController(MockMvcParameter parameter) {
		getResponse(parameter);
	}
	
	/**
	 * 获取请求的放回参数
	 * Created by yaolh on 2017/4/26 11:10.
	 */
	protected String getResponse(MockMvcParameter parameter) {
		String response = doGet(mockMvc, getUrlTemplate(), parameter.build(), HttpStatus.OK);
		System.out.println("*************************************************************************************");
		System.out.println(response);
		System.out.println("*************************************************************************************");
		return response;
	}
	
	/**
	 * 获取请求的URL
	 * Created by yaolh on 2017/5/2 17:56.
	 */
	private String getUrlTemplate() {
		final String BASE_CONTROLLER_PACKAGE = "com.dataeye.citest.controller";
		final String STANDARD_TEST_CONTROLLER_SUFFIX = "Test";
		
		// 获取类的全限定名及方法名
		String testClass = "", method = "";
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			if (element.getClassName().startsWith(BASE_CONTROLLER_PACKAGE)) {
				
				testClass = element.getClassName();
				method = element.getMethodName();
				break;
			}
		}
		
		// 获取请求的URL
		String baseUrl, subUrl;
		// 获取测试方法上@RequestMapping的值
		subUrl = getMethodRequestMapping(getClass(testClass), method);
		if (testClass.endsWith(STANDARD_TEST_CONTROLLER_SUFFIX)) {
			String controllerClass = testClass.substring(0, testClass.length() - STANDARD_TEST_CONTROLLER_SUFFIX.length());
			Class clazz = getClass(controllerClass);
			baseUrl = getClassRequestMapping(clazz);
			// 如果测试方法没有@RequestMapping注解，则获取Controller对应方法的@RequestMapping注解的值
			if (StringUtils.isEmpty(subUrl)) {
				subUrl = getMethodRequestMapping(clazz, method);
			}
			if (StringUtils.isEmpty(subUrl)) {
				throw new RuntimeException("Controller中找不到与test方法同名的方法！");
			}
			
			return getFinalUrl(baseUrl, subUrl);
		} else {
			throw new RuntimeException("测试类必须以Test结尾，类名与要测试的Controller一样！");
		}
	}
	
	/**
	 * 获取类上的@RequestMapping注解的值
	 * Created by yaolh on 2017/5/2 19:14.
	 */
	private String getClassRequestMapping(Class clazz) {
		RequestMapping classMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
		if (null != classMapping) {
			// 获取Controller上注解的URL
			return classMapping.value()[0];
		}
		return "";
	}
	
	/**
	 * 获取方法上的@RequestMapping注解的值
	 * Created by yaolh on 2017/5/2 19:15.
	 */
	private String getMethodRequestMapping(Class clazz, String method) {
		for (Method m : clazz.getDeclaredMethods()) {
			// 获取方法上注解的URL
			if (method.equals(m.getName())) {
				RequestMapping methodMapping = m.getAnnotation(RequestMapping.class);
				return null == methodMapping ? "" : methodMapping.value()[0];
			}
		}
		return "";
	}
	
	private Class getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String doGet(MockMvc mockMvc, String url, Map<String, Object> parameter, HttpStatus httpStatus) {
		try {
			MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url);
			if (parameter != null) {
				for (Map.Entry<String, Object> entry : parameter.entrySet()) {
					builder.param(entry.getKey(), String.valueOf(entry.getValue()));
				}
			}
				
			MvcResult result = mockMvc.perform(builder)
					.andDo(print())
					.andExpect(status().is(httpStatus.value()))
					.andReturn();
			
			return result.getResponse().getContentAsString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getFinalUrl(String baseUrl, String subUrl) {
		String url = "/" + baseUrl + "/" + subUrl;
		return url.replaceAll("[/]{2,}", "/");
	}
}
