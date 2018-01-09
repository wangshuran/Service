package com.selfsell.common.exception;

import java.text.MessageFormat;

/**
 * 业务异常
 * 
 * @author Breeze
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	BusinessException() {
	}

	public BusinessException(String message, Object... param) {

		super(MessageFormat.format(message, param));

	}

}
