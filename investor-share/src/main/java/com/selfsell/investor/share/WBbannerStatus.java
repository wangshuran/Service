package com.selfsell.investor.share;

/**
 * 字典标识bannerStatus
 * 
 * 字典名称状态
 * 
 * @author Breeze
 *
 */
public enum WBbannerStatus {

	/**
	 * ENABLED:启用
	 */
	ENABLED("启用"), 
	/**
	 * DISABLED:禁用
	 */
	DISABLED("禁用"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBbannerStatus(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
