package com.selfsell.investor.share;

/**
 * 字典标识lang
 * 
 * 字典名称多语言
 * 
 * @author Breeze
 *
 */
public enum WBlang {

	/**
	 * zh:简体中文
	 */
	zh("简体中文"), 
	/**
	 * en:英文
	 */
	en("英文"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBlang(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
