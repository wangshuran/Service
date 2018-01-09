package com.selfsell.investee.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.selfsell.common.bean.ErrorCode;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.common.exception.BusinessException;

/**
 * controller 异常统一捕获
 * 
 * @author breeze
 *
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

	Logger log = LoggerFactory.getLogger(getClass());

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(BusinessException.class)
	ResultMap bussinessException(BusinessException e) {
		log.error("业务异常", e);

		return ResultMap.failResult(ErrorCode.controller_catch, e.getMessage());
	}

}
