package com.selfsell.investor.share;

/**
 * 字典标识investorStatus
 * 
 * 字典名称投资人状态
 * 
 * @author Breeze
 *
 */
public enum WBinvestorStatus {

	/**
	 * NORMAL:正常
	 */
	NORMAL("正常"), 
	/**
	 * OFF:注销
	 */
	OFF("注销"), 
	/**
	 * LOCK:锁定
	 */
	LOCK("锁定"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBinvestorStatus(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
