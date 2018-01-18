package com.selfsell.investor.share;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 常量类
 * 
 * @author breeze
 *
 */
public class Constants {

	/**
	 * 投资人服务登录jwt验证密钥
	 */
	public static final String JWT_SECRET_AUTH = "be0b41aec949416aab36acc0c414df8d";
	
	/**
	 * 1act的换算
	 */
	public static final BigDecimal ACT_PRECISION = BigDecimal.ONE;

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
