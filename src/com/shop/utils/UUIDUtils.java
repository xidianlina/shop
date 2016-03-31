package com.shop.utils;

import java.util.UUID;

/**
 * 随机生成字符串的工具类
 * 
 * @author dell
 * 
 */
public class UUIDUtils {
	/**
	 * 获得随机字符串的方法
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
