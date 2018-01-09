package com.selfsell.investee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfsell.common.bean.ResultMap;
import com.selfsell.investee.service.InvesteeService;
import com.selfsell.investee.share.InvesteeListREQ;
import com.selfsell.investee.share.InvesteeListRES;
import com.selfsell.investee.share.Urls;

@RestController
public class InvesteeController {

	@Autowired
	InvesteeService investeeService;

	@RequestMapping(value = Urls.INVESTEE_LIST)
	ResultMap list(@RequestBody InvesteeListREQ investeeListReq) {
		InvesteeListRES result = investeeService.list(investeeListReq);
		return ResultMap.successResult().set("totalAmount", result.getTotalCount()).set("resultList",
				result.getResultList());
	}
}
