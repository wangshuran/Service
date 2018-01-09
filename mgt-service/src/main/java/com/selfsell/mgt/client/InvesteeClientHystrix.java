package com.selfsell.mgt.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breeze.bms.mgt.bean.ResultMap;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.selfsell.investee.share.InvesteeListREQ;

@Service("investeeClientHystrix")
public class InvesteeClientHystrix implements InvesteeClient {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	InvesteeClient investeeClient;

	@Override
	@HystrixCommand(fallbackMethod = "investeeListFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap investeeList(InvesteeListREQ investeeListReq) {
		return investeeClient.investeeList(investeeListReq);
	}

	public ResultMap investeeListFallback(InvesteeListREQ investeeListReq,Throwable e) {
		logger.error("investee-service服务调用异常", e);
		return ResultMap.failResult("3000", "investee-service服务方法调用异常" + e.getMessage());
	}

}
