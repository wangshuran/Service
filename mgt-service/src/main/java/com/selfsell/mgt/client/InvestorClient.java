package com.selfsell.mgt.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.ParamSetBean;
import com.selfsell.investor.share.TransferBean;
import com.selfsell.investor.share.Urls;

@FeignClient(name = "investor-service")
public interface InvestorClient {
	@RequestMapping(value = Urls.APP_BANNER_LIST)
	public ResultMap appBannerList(AppBannerListREQ appBannerListREQ);
	@RequestMapping(value = Urls.APP_BANNER_ADD)
	public ResultMap appBannerAdd(AppBannerBean appBannerBean);
	@RequestMapping(value = Urls.APP_BANNER_DEL)
	public ResultMap appBannerDel(Long id);
	@RequestMapping(value = Urls.APP_BANNER_UPDATE_STATUS)
	public ResultMap appBannerUpdateStatus(AppBannerBean appBannerBean);
	@RequestMapping(value = Urls.APP_BANNER_UPDATE)
	public ResultMap appBannerUpdate(AppBannerBean appBannerBean);
	@RequestMapping(value = Urls.FUND_PLAN_LIST)
	public ResultMap fundPlanList(FundPlanBean fundPlanBean);
	@RequestMapping(value = Urls.FUND_PLAN_LANG_LIST)
	public ResultMap fundPlanLangList(FundPlanLangBean fundPlanLangBean);
	@RequestMapping(value = Urls.FUND_PLAN_ADD)
	public ResultMap fundPlanAdd(FundPlanBean fundPlanBean);
	@RequestMapping(value = Urls.FUND_PLAN_UPDATE)
	public ResultMap fundPlanUpdate(FundPlanBean fundPlanBean);
	@RequestMapping(value = Urls.FUND_PLAN_DEL)
	public ResultMap fundPlanDel(Long id);
	@RequestMapping(value = Urls.FUND_PLAN_UPDATE_STATUS)
	public ResultMap fundPlanUpdateStatus(FundPlanBean fundPlanBean);
	@RequestMapping(value = Urls.TRANSFER_LIST)
	public ResultMap transferList(TransferBean transferBean);
	@RequestMapping(value = Urls.TRANSFER_AUDIT)
	public ResultMap transferAudit(TransferBean transferBean);
	@RequestMapping(value = Urls.INVESTOR_LIST)
	public ResultMap list(InvestorListBean investorListBean);
	@RequestMapping(value = Urls.INVESTOR_UPDATE_STATUS)
	public ResultMap updateStatus(InvestorBean investorListBean);
	@RequestMapping(value = Urls.PARAMSET_LIST)
	public ResultMap paramSetList(ParamSetBean paramSetBean);
	@RequestMapping(value = Urls.PARAMSET_ADD)
	public ResultMap paramSetAdd(ParamSetBean paramSetBean);
	@RequestMapping(value = Urls.PARAMSET_UPDATE)
	public ResultMap paramSetUpdate(ParamSetBean paramSetBean);
	@RequestMapping(value = Urls.PARAMSET_DEL)
	public ResultMap paramSetDel(ParamSetBean paramSetBean);
}
