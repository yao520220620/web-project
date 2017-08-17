package com.dataeye.citest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Stream;

/**
 * @author yaolh
 * @version 创建时间：2017/7/28 10:30
 */
@Controller
public class TestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${key}")
	private String key;
	
	@RequestMapping("test")
	@ResponseBody
	public String test() {
		logger.debug("hello");
		System.out.println("hello");
		System.out.println(key);
		
		Stream.of(1, 2, 3, 4).forEach(System.out::println);
		return "hello world!!!";
	}
}
