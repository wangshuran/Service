package com.selfsell.common.bean;

public enum ErrorCode {
	/**
	 * 参数校验错误
	 */
	params_validate("1000", "参数校验错误"),
	/**
	 * token检验失败
	 */
	auth_token_fail("1001", "auth token检验失败"),
	/**
	 * controller exception handler 捕获异常
	 */
	controller_catch("2000", "处理器捕获异常"),
	internal_catch("2001", "程序内部错误");
	
	

	private String code;
	private String remark;

	ErrorCode(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
