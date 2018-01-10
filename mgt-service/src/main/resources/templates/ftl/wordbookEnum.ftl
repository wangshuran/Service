package com.breeze.wordbook;

/**
 * 字典标识${wb.wbKey}
 * 
 * 字典名称${wb.wbName}
 * 
 * @author Breeze
 *
 */
public enum WB${wb.wbKey} {

<#list wbItems as wbItem>
	/**
	 * ${wbItem.wbiKey}:${wbItem.wbiValue}
	 */
	${wbItem.wbiKey}("${wbItem.wbiValue}")<#if wbItem_has_next>,<#else>;</#if> 
</#list>

	/**
	 * 字典项显示
	 */
	private String display;
	
	WB${wb.wbKey}(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
