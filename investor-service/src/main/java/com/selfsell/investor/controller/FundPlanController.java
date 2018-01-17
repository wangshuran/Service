package com.selfsell.investor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.FundPlan;
import com.selfsell.investor.service.FundPlanService;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.Urls;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.FundPlanREQ;

@RestController
public class FundPlanController {

	@Autowired
	FundPlanService fundPlanService;

	@RequestMapping(value = Urls.FUND_PLAN_LIST)
	ResultMap list(@RequestBody FundPlanBean fundPlanBean) {
		PageInfo<FundPlan> fundPlanPage = fundPlanService.list(fundPlanBean);

		List<FundPlan> resultList = fundPlanPage.getList();

		fundPlanService.fillLang(resultList);

		return ResultMap.successResult().set("totalAmount", fundPlanPage.getTotal()).set("resultList", resultList);
	}
	
	@RequestMapping(value = Urls.FUND_PLAN_LANG_LIST)
	ResultMap langList(@RequestBody FundPlanLangBean fundPlanLangBean) {

		return ResultMap.successResult().set("resultList", fundPlanService.langList(fundPlanLangBean));
	}

	@RequestMapping(value = Urls.FUND_PLAN_ADD)
	ResultMap add(@RequestBody FundPlanBean fundPlanBean) {

		fundPlanService.add(fundPlanBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.FUND_PLAN_UPDATE)
	ResultMap update(@RequestBody FundPlanBean fundPlanBean) {

		fundPlanService.update(fundPlanBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.FUND_PLAN_UPDATE_STATUS)
	ResultMap updateStatus(@RequestBody FundPlanBean fundPlanBean) {

		fundPlanService.updateStatus(fundPlanBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.FUND_PLAN_DEL)
	ResultMap del(@RequestBody Long id) {

		fundPlanService.del(id);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.FUND_PLAN)
	ResultMap fundPlan(@RequestBody FundPlanREQ fundPlanREQ) {

		return ResultMap.successResult(fundPlanService.fundPlan(fundPlanREQ));
		
	}

}
