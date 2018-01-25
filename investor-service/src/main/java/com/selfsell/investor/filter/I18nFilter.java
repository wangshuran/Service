package com.selfsell.investor.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import com.selfsell.investor.service.I18nService;

/**
 * 验证授权
 * 
 * @author breeze
 *
 */
@WebFilter(filterName = "Filter0_I18nFilter", urlPatterns = "/*", initParams = {})
public class I18nFilter implements Filter {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	LocaleResolver localResolver;
	
	@Autowired
	I18nService i18nService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String lang = httpRequest.getHeader("Lang");
		if (!StringUtils.isEmpty(lang)) {
			final Locale locale = StringUtils.parseLocaleString(lang.toLowerCase());
			localResolver.setLocale(httpRequest, (HttpServletResponse) response, locale);
		}
		
		chain.doFilter(httpRequest, response);
		return;

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
