package com.selfsell.investor.share;

/**
 * 字典标识rcChannel
 * 
 * 字典名称复活卡渠道
 * 
 * @author Breeze
 *
 */
public enum WBrcChannel {

	/**
	 * BUY:购买
	 */
	BUY("购买"), 
	/**
	 * INVITE:邀请赠送
	 */
	INVITE("邀请赠送"), 
	/**
	 * USE:使用
	 */
	USE("使用"), 
	/**
	 * GIVE:赠送
	 */
	GIVE("赠送"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBrcChannel(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
