package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.selfsell.investor.bean.FinancialStatus;
import com.selfsell.investor.mybatis.domain.FinancialRecord;
import com.selfsell.investor.mybatis.mapper.FinancialRecordMapper;
import com.selfsell.investor.service.FinancialService;

import tk.mybatis.mapper.entity.Example;

@Component
public class FinancialServiceImpl implements FinancialService {
	@Autowired
	FinancialRecordMapper financialRecordMapper;

	@Override
	public List<FinancialRecord> queryInvestorFinancialRecords(Long id) {
		Example example = new Example(FinancialRecord.class);
		example.createCriteria().andEqualTo("status", FinancialStatus.ING.name()).andEqualTo("investorId", id);

		return financialRecordMapper.selectByExample(example);
	}

}
