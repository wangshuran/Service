package com.selfsell.investor.service;

import com.selfsell.investor.mybatis.domain.ScanTradeRecord;

/**
 * 扫描交易记录
 * 
 * @author breeze
 *
 */
public interface ScanTradeRecordService {

	/**
	 * 判断是否已经存在交易记录
	 * 
	 * @param tradeRecord
	 * @return
	 */
	boolean existsRecords(ScanTradeRecord tradeRecord);

	/**
	 * 插入扫描交易记录
	 * 
	 * @param scanTradeRecord
	 */
	void insert(ScanTradeRecord scanTradeRecord);

	/**
	 * 判断是否存在记录
	 * 
	 * @param txId
	 * @return
	 */
	boolean existsRecords(String txId);

}
