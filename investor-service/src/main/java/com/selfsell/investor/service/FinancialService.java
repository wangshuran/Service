package com.selfsell.investor.service;

import java.util.List;

import com.selfsell.investor.mybatis.domain.FinancialRecord;

public interface FinancialService {

	/**
	 * 查询投资人的理财计划
	 * 
	 * @param id
	 * @return
	 */
	List<FinancialRecord> queryInvestorFinancialRecords(Long id);

}
