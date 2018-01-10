package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.AppBanner;
import com.selfsell.investor.service.AppBannerService;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.AppBannerREQ;
import com.selfsell.investor.share.Urls;

@RestController
public class AppBannerController {

	@Autowired
	AppBannerService appBannerService;

	@RequestMapping(value = Urls.APP_BANNER_LIST)
	ResultMap list(@RequestBody AppBannerListREQ appBannerListREQ) {
		PageInfo<AppBanner> appBannerPage = appBannerService.list(appBannerListREQ);

		return ResultMap.successResult().set("totalAmount", appBannerPage.getTotal()).set("resultList",
				appBannerPage.getList());
	}

	@RequestMapping(value = Urls.APP_BANNER_ADD)
	ResultMap add(@RequestBody AppBannerBean appBannerBean) {
		appBannerService.add(appBannerBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.APP_BANNER_UPDATE)
	ResultMap update(@RequestBody AppBannerBean appBannerBean) {
		appBannerService.update(appBannerBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.APP_BANNER_DEL)
	ResultMap del(@RequestBody Long id) {
		appBannerService.del(id);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.APP_BANNER_UPDATE_STATUS)
	ResultMap del(@RequestBody AppBannerBean appBannerBean) {
		appBannerService.updateStatus(appBannerBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.APP_BANNER)
	ResultMap appBanner(@RequestBody AppBannerREQ appBannerREQ) {

		return ResultMap.successResult(appBannerService.appBanner(appBannerREQ));
	}
}
