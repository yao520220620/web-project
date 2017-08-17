package com.dataeye.citest.base;

/**
 * 测试请求参数
 * @author yaolh
 * @version 创建时间：2017/4/25 19:35
 */
public enum ParameterData {
	UID("uid", 10105),
	UID_42566("uid", 42566),
	UID_43024("uid", 43024),
	APP_ID("appid", "CA44CCE567DF55A95AD51F741FFA5A325"),
	CHANNEL_ID("channelId", 1016),
	PLATFORM_IOS("platform", 1),
	PLATFORM_ANDROID("platform", 2),
	PLATFORM_ALL("platform", 3),
	TARGET_URL("targetUrl", "http://www.baidu.com"),
	TOKEN_GLOBAL_UID_10105("tokenglobal", "41a2e70b7a827b9bc2c4123b3adf3a752d40083fd8cc926bf0e26580b8ea471d"),
	TOKEN_GLOBAL_UID_42566("tokenglobal", "7b3da10d85e6a08526908e38d69a53fdd14974770c8294499a68ccd42623a78c"),
	TOKEN_GLOBAL_UID_43024("tokenglobal", "045e2ac05ba9f79dd8906bce02f6d71764ffa932c33500db8c75d5372c4b492e");
	
	private String key;
	private Object value;
	
	ParameterData(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
}
