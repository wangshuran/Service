package com.selfsell.investor.service.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.service.I18nService;

@Component
public class I18nServiceImpl implements I18nService {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MessageSource messageSource;

	@Override
	public String getMessage(String code, Object... args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(I18nMessageCode code, Object... args) {
		return messageSource.getMessage(code.name(), args,LocaleContextHolder.getLocale());
	}

	@Override
	public String getMessage(I18nMessageCode code, Object[] args, Locale locale) {
		return messageSource.getMessage(code.name(), args, locale);
	}
}
