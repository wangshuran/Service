package com.selfsell.mgt.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breeze.bms.mgt.bean.ResultMap;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.selfsell.investor.share.AnswerActivityBean;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.ParamSetBean;
import com.selfsell.investor.share.TransferBean;

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

	@Override
	@HystrixCommand(fallbackMethod = "fundPlanFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap fundPlanAdd(FundPlanBean fundPlanBean) {
		return investorClient.fundPlanAdd(fundPlanBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "fundPlanFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap fundPlanUpdate(FundPlanBean fundPlanBean) {
		return investorClient.fundPlanUpdate(fundPlanBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "fundPlanDelFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap fundPlanDel(Long id) {
		return investorClient.fundPlanDel(id);
	}
	public ResultMap fundPlanDelFallback(Long id, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "fundPlanFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap fundPlanUpdateStatus(FundPlanBean fundPlanBean) {
		return investorClient.fundPlanUpdateStatus(fundPlanBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "transferFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap transferList(TransferBean transferBean) {
		return investorClient.transferList(transferBean);
	}
	public ResultMap transferFallback(TransferBean transferBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "transferFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap transferAudit(TransferBean transferBean) {
		return investorClient.transferAudit(transferBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "listFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap list(InvestorListBean investorListBean) {
		return investorClient.list(investorListBean);
	}
	public ResultMap listFallback(InvestorListBean investorListBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "updateStatusFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap updateStatus(InvestorBean investorBean) {
		return investorClient.updateStatus(investorBean);
	}
	public ResultMap updateStatusFallback(InvestorBean investorBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "paramSetFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap paramSetList(ParamSetBean paramSetBean) {
		return investorClient.paramSetList(paramSetBean);
	}
	public ResultMap paramSetFallback(ParamSetBean paramSetBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "paramSetFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap paramSetAdd(ParamSetBean paramSetBean) {
		return investorClient.paramSetAdd(paramSetBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "paramSetFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap paramSetUpdate(ParamSetBean paramSetBean) {
		return investorClient.paramSetUpdate(paramSetBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "paramSetFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap paramSetDel(ParamSetBean paramSetBean) {
		return investorClient.paramSetDel(paramSetBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "answerActivityFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap answerActivityList(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityList(answerActivityBean);
	}
	
	public ResultMap answerActivityFallback(AnswerActivityBean answerActivityBean, Throwable e) {
		logger.error("investor-service服务调用异常", e);
		return ResultMap.failResult("3000", "investor-service服务方法调用异常" + e.getMessage());
	}

	@Override
	@HystrixCommand(fallbackMethod = "answerActivityFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap answerActivityAdd(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityAdd(answerActivityBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "answerActivityFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap answerActivityUpdate(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityUpdate(answerActivityBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "answerActivityFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap answerActivityUpdateStatus(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityUpdateStatus(answerActivityBean);
	}

	@Override
	@HystrixCommand(fallbackMethod = "answerActivityFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000") })
	public ResultMap answerActivityDel(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityDel(answerActivityBean);
	}
}
