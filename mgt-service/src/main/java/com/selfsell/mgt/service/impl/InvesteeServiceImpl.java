package com.selfsell.mgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investee.share.InvesteeListREQ;
import com.selfsell.mgt.client.InvesteeClient;
import com.selfsell.mgt.service.InvesteeService;

@Component
public class InvesteeServiceImpl implements InvesteeService {

	@Autowired
	@Qualifier("investeeClientHystrix")
	InvesteeClient investeeClient;

	@Override
	public ResultMap list(InvesteeListREQ data) {
		return investeeClient.investeeList(data);
	}

}
