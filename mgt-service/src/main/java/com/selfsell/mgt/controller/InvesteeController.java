package com.selfsell.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investee.share.InvesteeListREQ;
import com.selfsell.mgt.service.InvesteeService;

/**
 * 
 * @author breeze
 *
 */
@RestController
@RequestMapping(value = "investee")
public class InvesteeController {

	@Autowired
	InvesteeService investeeService;

	@RequestMapping(value = "list")
	public ResultMap list(@ModelAttribute InvesteeListREQ investeeListReq) {
		return investeeService.list(investeeListReq);
	}

}
