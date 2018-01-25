package com.selfsell.investor.filter;

import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfsell.common.bean.ErrorCode;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.share.Constants;

/**
 * 验证授权
 * 
 * @author breeze
 *
 */
@WebFilter(filterName = "Filter1_JWTAuthFilter", urlPatterns = "/*", initParams = {
		@WebInitParam(name = "EXCLUDED_URL", value = "/investor/register$|" + "/investor/login$|" + "/send/email$|"
				+ "/app/banner/*|"+ "/transfer/audit$|"+ "/investor/list$|"+ "/investor/updateStatus$|"
				+ "/paramset/*|"+ "/answer/activity/*|"+ "/aa/*|"
				+ "/fund/plan/*|" + "/transfer/list$") })
public class JWTAuthFilter implements Filter {

	Logger log = LoggerFactory.getLogger(getClass());

	Pattern excludedUrlPattern;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	I18nService i18nService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedUrl = filterConfig.getInitParameter("EXCLUDED_URL");
		if (!StringUtils.isEmpty(excludedUrl)) {
			excludedUrlPattern = Pattern.compile(excludedUrl);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if ("OPTIONS".equals(httpRequest.getMethod())) {
			chain.doFilter(httpRequest, response);
			return;
		}

		Matcher m = excludedUrlPattern.matcher(httpRequest.getRequestURI());

		if (!m.find()) {
			ResultMap result;
			String authorization = httpRequest.getHeader("Authorization");
			String lang = httpRequest.getHeader("Lang");
			Locale locale = Locale.SIMPLIFIED_CHINESE;
			if (!StringUtils.isEmpty(lang)) {
				locale = StringUtils.parseLocaleString(lang.toLowerCase());
			}

			if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")) {
				String token = authorization.replaceAll("Bearer ", "");
				try {
					JWT.require(Algorithm.HMAC256(Constants.JWT_SECRET_AUTH)).build().verify(token);
					chain.doFilter(httpRequest, response);
					return;
				} catch (SignatureVerificationException e1) {
					result = ResultMap.failResult(ErrorCode.auth_token_fail,
							i18nService.getMessage(I18nMessageCode.ATF_1001_01, null, locale));
				} catch (TokenExpiredException e2) {
					result = ResultMap.failResult(ErrorCode.auth_token_fail,
							i18nService.getMessage(I18nMessageCode.ATF_1001_02, null, locale));
				} catch (InvalidClaimException e3) {
					result = ResultMap.failResult(ErrorCode.auth_token_fail,
							i18nService.getMessage(I18nMessageCode.ATF_1001_03, null, locale));
				}
			} else {
				result = ResultMap.failResult(ErrorCode.auth_token_fail,
						i18nService.getMessage(I18nMessageCode.ATF_1001_04, null, locale));
			}

			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json; charset=utf-8");
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			httpResponse.getWriter().write(objectMapper.writeValueAsString(result));

			return;
		}

		chain.doFilter(httpRequest, response);
		return;

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
