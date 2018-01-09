package com.selfsell.common.bean;

import java.text.MessageFormat;
import java.util.HashMap;

import org.springframework.util.StringUtils;

/**
 * 请求返回
 * 
 * @author breeze
 *
 */
public class ResultMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 2007243626124925692L;
	private static final String FLAG_FIELD = "success";
	private static final String CODE_FIELD = "code";
	private static final String MESSAGE_FIELD = "message";
	private static final String DATA_FIELD = "data";

	/**
	 * 返回只有标识的成功结果
	 * 
	 * @return
	 */
	public static ResultMap successResult() {
		ResultMap rm = new ResultMap();
		rm.put(FLAG_FIELD, true);
		return rm;
	}

	/**
	 * 返回带data属性的成功结果
	 * 
	 * @param data
	 * @return
	 */
	public static ResultMap successResult(Object data) {
		ResultMap rm = new ResultMap();
		rm.put(FLAG_FIELD, true);
		rm.put(DATA_FIELD, data);
		return rm;
	}

	/**
	 * 自定义code失败的结果
	 * 
	 * @param code
	 * @param message
	 * @param param
	 * @return
	 */
	public static ResultMap failResult(String code, String message, Object... param) {
		ResultMap rm = new ResultMap();
		rm.put(FLAG_FIELD, false);
		rm.put(CODE_FIELD, code);
		if (!StringUtils.isEmpty(message))
			rm.put(MESSAGE_FIELD, MessageFormat.format(message, param));
		return rm;
	}

	/**
	 * 以固定code失败的结果
	 * 
	 * @param errorCode
	 * @param message
	 * @param param
	 * @return
	 */
	public static ResultMap failResult(ErrorCode errorCode, String message, Object... param) {
		ResultMap rm = new ResultMap();
		rm.put(FLAG_FIELD, false);
		rm.put(CODE_FIELD, errorCode.getCode());
		if (StringUtils.isEmpty(message))
			message = errorCode.getRemark();
		rm.put(MESSAGE_FIELD, MessageFormat.format(message, param));
		return rm;
	}

	/**
	 * 设置返回结果集
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap set(String key, Object value) {
		this.put(key, value);
		return this;
	}
}
