package com.selfsell.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.mgt.service.InvestorService;

/**
 * 
 * @author breeze
 *
 */
@RestController
@RequestMapping(value = "investor")
public class InvestorController {

	@Autowired
	InvestorService investorService;

	@RequestMapping(value = "appBannerList")
	public ResultMap appBannerList(@ModelAttribute AppBannerListREQ appBannerListREQ) {
		return investorService.appBannerList(appBannerListREQ);
	}

}
