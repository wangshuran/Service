package com.selfsell.common.util;

import com.selfsell.common.exception.BusinessException;

public class CheckParamUtil {
	/**
	 * 检查参数是否为空
	 * 
	 * @param reference
	 * @param message
	 */
	public static void checkEmpty(String reference, String message) {
		if (reference == null || "".equals(reference.trim())) {
			throw new BusinessException(message);
		}
	}

	/**
	 * 检查true/false
	 * 
	 * @param result
	 * @param message
	 */
	public static void checkBoolean(Boolean result, String message) {
		if (result) {
			throw new BusinessException(message);
		}

	}
}
