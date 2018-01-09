package com.selfsell.mgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.mgt.client.InvestorClient;
import com.selfsell.mgt.service.InvestorService;

@Component
public class InvestorServiceImpl implements InvestorService {
	@Autowired
	@Qualifier("investorClientHystrix")
	InvestorClient investorClient;

	@Override
	public ResultMap appBannerList(AppBannerListREQ appBannerListREQ) {
		return investorClient.appBannerList(appBannerListREQ);
	}
}
