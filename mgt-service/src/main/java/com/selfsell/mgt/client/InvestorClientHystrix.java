package com.selfsell.mgt.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breeze.bms.mgt.bean.ResultMap;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.selfsell.investor.share.AppBannerListREQ;

@Service("investorClientHystrix")
public class InvestorClientHystrix implements InvestorClient {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	InvestorClient investorClient;

	@Override
	@HystrixCommand(fallbackMethod = "appBannerListFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap appBannerList(AppBannerListREQ appBannerListREQ) {
		return investorClient.appBannerList(appBannerListREQ);
	}

	public ResultMap appBannerListFallback(AppBannerListREQ appBannerListREQ, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

}
