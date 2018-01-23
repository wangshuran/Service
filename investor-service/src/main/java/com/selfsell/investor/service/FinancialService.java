package com.selfsell.investor.service;

import java.util.List;

import com.selfsell.investor.mybatis.domain.FinancialRecord;
import com.selfsell.investor.share.JoinFundPlanREQ;
import com.selfsell.investor.share.QuitFundPlanREQ;

public interface FinancialService {

	/**
	 * 查询投资人的理财计划
	 * 
	 * @param id
	 * @return
	 */
	List<FinancialRecord> queryInvestorFinancialRecords(Long id);

	/**
	 * 加入资金计划
	 * 
	 * @param joinFundPlanREQ
	 */
	void joinFundPlan(JoinFundPlanREQ joinFundPlanREQ);

	/**
	 * 退出资金计划
	 * 
	 * @param quitFundPlanREQ
	 */
	void quitFundPlan(QuitFundPlanREQ quitFundPlanREQ);

}
