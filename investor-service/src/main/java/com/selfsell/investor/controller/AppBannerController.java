package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.AppBanner;
import com.selfsell.investor.service.AppBannerService;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.Urls;

@RestController
public class AppBannerController {

	@Autowired
	AppBannerService appBannerService;

	@RequestMapping(value = Urls.APP_BANNER_LIST)
	ResultMap list(@RequestBody AppBannerListREQ appBannerListREQ) {
		PageInfo<AppBanner> appBannerPage = appBannerService.list(appBannerListREQ);

		return ResultMap.successResult().set("totalAmount", appBannerPage.getTotal()).set("appBannerList",
				appBannerPage.getList());
	}
}
