package com.selfsell.investor.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfsell.common.bean.ResultMap;

@RestController
public class TestLanguageController {
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value = "/test/lang")
	public ResultMap test() {
		Locale locale = LocaleContextHolder.getLocale();
		return ResultMap.successResult().set("lang", messageSource.getMessage("test", null, locale));
	}
}
