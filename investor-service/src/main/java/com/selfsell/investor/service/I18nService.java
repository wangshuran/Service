package com.selfsell.investor.service;

import java.util.Locale;

import com.selfsell.investor.bean.I18nMessageCode;

/**
 * 多语言服务
 * 
 * @author breeze
 *
 */
public interface I18nService {
	
	String getMessage(I18nMessageCode code, Object... args);

	String getMessage(String code, Object... args);
	
	String getMessage(String code, Object[] args,Locale locale);
	
	String getMessage(I18nMessageCode code, Object[] args,Locale locale);

}
