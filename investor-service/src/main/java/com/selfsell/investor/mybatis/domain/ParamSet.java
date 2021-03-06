package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;

import javax.persistence.Id;

public class ParamSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String paramKey;
	private String paramValue;
	private String remark;

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
