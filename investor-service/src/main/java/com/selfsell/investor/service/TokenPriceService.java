package com.selfsell.investor.service;

import java.math.BigDecimal;

import com.selfsell.investor.bean.CurrencyType;

/**
 * 代币价格服务
 * 
 * @author breeze
 *
 */
public interface TokenPriceService {
	/**
	 * 查询相关代币价格
	 * 
	 * @param token
	 * @return
	 */
	BigDecimal queryPrice(String token, CurrencyType currencyType);
}
