package com.selfsell.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
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

	@RequestMapping(value = "appBannerAdd")
	public ResultMap appBannerAdd(@RequestParam(value = "imgFile") MultipartFile file,
			@ModelAttribute AppBannerBean appBannerBean) {
		return investorService.appBannerAdd(file, appBannerBean);
	}
	
	@RequestMapping(value = "appBannerUpdate")
	public ResultMap appBannerUpdate(@RequestParam(value = "imgFile") MultipartFile file,
			@ModelAttribute AppBannerBean appBannerBean) {
		return investorService.appBannerUpdate(file, appBannerBean);
	}
	
	@RequestMapping(value = "appBannerDel")
	public ResultMap appBannerDel(@RequestParam(value = "id") Long id) {
		return investorService.appBannerDel(id);
	}
	
	@RequestMapping(value = "appBannerUpdateStatus")
	public ResultMap updateStatus(@ModelAttribute AppBannerBean appBannerBean) {
		return investorService.updateStatus(appBannerBean);
	}
	
	@RequestMapping(value = "fundPlanList")
	public ResultMap fundPlanList(@ModelAttribute FundPlanBean fundPlanBean) {
		return investorService.fundPlanList(fundPlanBean);
	}

}
