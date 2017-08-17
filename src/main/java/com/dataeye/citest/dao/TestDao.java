package com.dataeye.citest.dao;

import java.util.Map;

/**
 * @author yaolh
 * @version 创建时间：2017/8/13 12:47
 */
public interface TestDao {
	Map<String, String> list(String host, String user);
}
