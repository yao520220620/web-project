package com.dataeye.citest.base;

import org.apache.commons.lang.StringUtils;

/**
 * @author yaolh
 * @version 创建时间：2017/4/26 10:34
 */
public class JsonUtil {
	public static String formatJson(String jsonStr) {
		if (StringUtils.isEmpty(jsonStr)) return "";
		
		StringBuilder sb = new StringBuilder();
		char last;
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
				case '{':
				case '[':
					sb.append(current);
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
					break;
				case '}':
				case ']':
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
					sb.append(current);
					break;
				case ',':
					sb.append(current);
					if (last != '\\') {
						sb.append('\n');
						addIndentBlank(sb, indent);
					}
					break;
				default:
					sb.append(current);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 添加space
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append('\t');
		}
	}
}
