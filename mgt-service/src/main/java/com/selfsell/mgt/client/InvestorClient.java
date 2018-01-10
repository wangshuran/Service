package com.selfsell.mgt.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
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
}
