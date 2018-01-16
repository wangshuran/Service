package com.selfsell.mgt.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breeze.bms.mgt.bean.ResultMap;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;

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

	@Override
	@HystrixCommand(fallbackMethod = "appBannerAddFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap appBannerAdd(AppBannerBean appBannerBean) {
		return investorClient.appBannerAdd(appBannerBean);
	}

	public ResultMap appBannerAddFallback(AppBannerBean appBannerBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "appBannerDelFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap appBannerDel(Long id) {
		return investorClient.appBannerDel(id);
	}

	public ResultMap appBannerDelFallback(Long id, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "appBannerAddFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap appBannerUpdateStatus(AppBannerBean appBannerBean) {
		return investorClient.appBannerUpdateStatus(appBannerBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "appBannerAddFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap appBannerUpdate(AppBannerBean appBannerBean) {
		return investorClient.appBannerUpdate(appBannerBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "fundPlanFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap fundPlanList(FundPlanBean fundPlanBean) {
		return investorClient.fundPlanList(fundPlanBean);
	}
	public ResultMap fundPlanFallback(FundPlanBean fundPlanBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "fundPlanLangFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap fundPlanLangList(FundPlanLangBean fundPlanLangBean) {
		return investorClient.fundPlanLangList(fundPlanLangBean);
	}
	public ResultMap fundPlanLangFallback(FundPlanLangBean fundPlanLangBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}
}
