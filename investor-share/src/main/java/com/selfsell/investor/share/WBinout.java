package com.selfsell.investor.share;

/**
 * 字典标识inout
 * 
 * 字典名称收支标识
 * 
 * @author Breeze
 *
 */
public enum WBinout {

	/**
	 * IN:收入
	 */
	IN("收入"), 
	/**
	 * OUT:支出
	 */
	OUT("支出"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBinout(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
