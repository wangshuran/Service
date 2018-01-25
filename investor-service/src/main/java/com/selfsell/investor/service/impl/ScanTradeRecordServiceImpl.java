package com.selfsell.investor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selfsell.investor.mybatis.domain.ScanTradeRecord;
import com.selfsell.investor.mybatis.mapper.ScanTradeRecordMapper;
import com.selfsell.investor.service.ScanTradeRecordService;

import tk.mybatis.mapper.entity.Example;

@Component
public class ScanTradeRecordServiceImpl implements ScanTradeRecordService {
	@Autowired
	ScanTradeRecordMapper scanTradeRecordMapper;

	@Override
	public boolean existsRecords(ScanTradeRecord tradeRecord) {

		return scanTradeRecordMapper.selectCount(tradeRecord) > 0;

	}

	@Override
	public void insert(ScanTradeRecord scanTradeRecord) {

		scanTradeRecordMapper.insert(scanTradeRecord);

	}

	@Override
	public boolean existsRecords(String txId) {
		Example example = new Example(ScanTradeRecord.class);
		example.createCriteria().andEqualTo("txIndex", txId);

		return scanTradeRecordMapper.selectCountByExample(example) > 0;
	}

}
