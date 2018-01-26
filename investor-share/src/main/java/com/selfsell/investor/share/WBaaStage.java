package com.selfsell.investor.share;

/**
 * 字典标识aaStage
 * 
 * 字典名称答题活动阶段
 * 
 * @author Breeze
 *
 */
public enum WBaaStage {

	/**
	 * SCHEDULE:排期中
	 */
	SCHEDULE("排期中"), 
	/**
	 * TOSTART:待开始
	 */
	TOSTART("待开始"), 
	/**
	 * PREHEATING:预热中
	 */
	PREHEATING("预热中"), 
	/**
	 * ANSWERING:答题中
	 */
	ANSWERING("答题中"), 
	/**
	 * FINISHED:完成
	 */
	FINISHED("完成"); 

	/**
	 * 字典项显示
	 */
	private String display;
	
	WBaaStage(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
