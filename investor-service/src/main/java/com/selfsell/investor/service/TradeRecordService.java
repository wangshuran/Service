package com.selfsell.investor.service;

import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.mybatis.domain.TradeRecord;

/**
 * 交易记录服务
 * 
 * @author breeze
 *
 */
public interface TradeRecordService {

	void insert(TradeRecord tradeRecord);
	
	void updateTranserStatus(String txId,TradeRecordStatus status);

}
