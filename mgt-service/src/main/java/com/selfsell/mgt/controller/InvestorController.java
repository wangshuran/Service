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
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.TransferBean;
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
	
	@RequestMapping(value = "fundPlanAdd")
	public ResultMap fundPlanAdd(@ModelAttribute FundPlanBean fundPlanBean,@RequestParam(value = "iconFile") MultipartFile file) {
		return investorService.fundPlanAdd(fundPlanBean,file);
	}
	
	@RequestMapping(value = "fundPlanUpdate")
	public ResultMap fundPlanUpdate(@ModelAttribute FundPlanBean fundPlanBean,@RequestParam(value = "iconFile") MultipartFile file) {
		return investorService.fundPlanUpdate(fundPlanBean,file);
	}
	@RequestMapping(value = "fundPlanUpdateStatus")
	public ResultMap fundPlanUpdateStatus(@ModelAttribute FundPlanBean fundPlanBean) {
		return investorService.fundPlanUpdateStatus(fundPlanBean);
	}
	@RequestMapping(value = "fundPlanDel")
	public ResultMap fundPlanDel(@RequestParam(value = "id") Long id) {
		return investorService.fundPlanDel(id);
	}
	
	@RequestMapping(value = "fundPlanLangList")
	public ResultMap fundPlanLangList(@ModelAttribute FundPlanLangBean fundPlanLangBean) {
		return investorService.fundPlanLangList(fundPlanLangBean);
	}
	
	@RequestMapping(value = "transferList")
	public ResultMap transferList(@ModelAttribute TransferBean transferBean) {
		return investorService.transferList(transferBean);
	}
	
	@RequestMapping(value = "transferAudit")
	public ResultMap transferAudit(@ModelAttribute TransferBean transferBean) {
		return investorService.transferAudit(transferBean);
	}

}
