package com.selfsell.investor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.mybatis.mapper.TradeRecordMapper;
import com.selfsell.investor.service.TradeRecordService;

@Component
public class TradeRecordServiceImpl implements TradeRecordService {
	@Autowired
	TradeRecordMapper tradeRecordMapper;

	@Override
	public void insert(TradeRecord tradeRecord) {

		tradeRecordMapper.insert(tradeRecord);

	}

	@Override
	public void updateTranserStatus(String txId, TradeRecordStatus status) {
		
		tradeRecordMapper.updateTranserStatus(txId,status.name());
		
	}
}
