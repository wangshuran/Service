package com.selfsell.investor.share;

/**
 * 字典标识dateUnit
 * 
 * 字典名称日期单元
 * 
 * @author Breeze
 *
 */
public enum WBdateUnit {

	/**
	 * Y:年
	 */
	Y("年"), 
	/**
	 * M:月
	 */
	M("月"), 
	/**
	 * D:日
	 */
	D("日"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBdateUnit(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
