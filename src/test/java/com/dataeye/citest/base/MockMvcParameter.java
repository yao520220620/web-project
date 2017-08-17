package com.dataeye.citest.base;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * The type Test http request parameter.
 * @author Stran <br>
 * @version 1.0 <br>
 * @since 2015.12.22 16:57:43
 */
public class MockMvcParameter {

    private Map<String, Object> parameterMap = new HashMap<>();

    public MockMvcParameter() {
    }

    public MockMvcParameter(ParameterData... data) {
		this();
		for (ParameterData datum : data) {
			parameterMap.put(datum.getKey(), datum.getValue());
		}
    }
    
    public MockMvcParameter put(String key, Object value) {
        parameterMap.put(key, value);
        return this;
    }
    
    public MockMvcParameter put(ParameterData... data) {
		for (ParameterData datum : data) {
			parameterMap.put(datum.getKey(), datum.getValue());
		}
        return this;
    }
	
	public String getString(String key) {
		return getString(key, null);
	}
	
	public String getString(String key, String defaultValue) {
		Object value = get(key);
		return null == value ? defaultValue : value.toString();
	}
	
	public Object get(String key) {
		return get(key, null);
	}
	
	public Object get(String key, Object defaultValue) {
		Object value = parameterMap.get(key);
		return null == value ? defaultValue : value;
	}
	
    public Map<String, Object> build() {
        return parameterMap;
    }

    public MockMvcParameter clear() {
        parameterMap.clear();
        return this;
    }
}
