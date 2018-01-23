package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.service.FinancialService;
import com.selfsell.investor.share.JoinFundPlanREQ;
import com.selfsell.investor.share.QuitFundPlanREQ;
import com.selfsell.investor.share.Urls;

@RestController
public class FinancialController {

	@Autowired
	FinancialService financialService;

	@RequestMapping(value = Urls.JOIN_FUND_PLAN)
	public ResultMap joinFundPlan(@RequestBody JoinFundPlanREQ joinFundPlanREQ) {
		financialService.joinFundPlan(joinFundPlanREQ);
		
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.QUIT_FUND_PLAN)
	public ResultMap quitFundPlan(@RequestBody QuitFundPlanREQ quitFundPlanREQ) {
		financialService.quitFundPlan(quitFundPlanREQ);
		
		return ResultMap.successResult();
	}

}
