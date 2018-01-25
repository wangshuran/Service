package com.selfsell.mgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AAQuestionBean;
import com.selfsell.investor.share.AAQuestionBean.AAOptionBean;
import com.selfsell.investor.share.AnswerActivityBean;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.ParamSetBean;
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
	
	@RequestMapping(value = "list")
	public ResultMap list(@ModelAttribute InvestorListBean investorListBean) {
		return investorService.list(investorListBean);
	}
	
	@RequestMapping(value = "updateStatus")
	public ResultMap updateStatus(@ModelAttribute InvestorBean investorBean) {
		return investorService.updateStatus(investorBean);
	}

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
	
	@RequestMapping(value = "paramSetList")
	public ResultMap paramSetList(@ModelAttribute ParamSetBean paramSetBean) {
		return investorService.paramSetList(paramSetBean);
	}
	@RequestMapping(value = "paramSetAdd")
	public ResultMap paramSetAdd(@ModelAttribute ParamSetBean paramSetBean) {
		return investorService.paramSetAdd(paramSetBean);
	}
	@RequestMapping(value = "paramSetUpdate")
	public ResultMap paramSetUpdate(@ModelAttribute ParamSetBean paramSetBean) {
		return investorService.paramSetUpdate(paramSetBean);
	}
	@RequestMapping(value = "paramSetDel")
	public ResultMap paramSetDel(@ModelAttribute ParamSetBean paramSetBean) {
		return investorService.paramSetDel(paramSetBean);
	}
	
	@RequestMapping(value = "answerActivityList")
	public ResultMap answerActivityList(@ModelAttribute AnswerActivityBean answerActivityBean) {
		return investorService.answerActivityList(answerActivityBean);
	}
	@RequestMapping(value = "answerActivityAdd")
	public ResultMap answerActivityAdd(@ModelAttribute AnswerActivityBean answerActivityBean) {
		return investorService.answerActivityAdd(answerActivityBean);
	}
	@RequestMapping(value = "answerActivityUpdate")
	public ResultMap answerActivityUpdate(@ModelAttribute AnswerActivityBean answerActivityBean) {
		return investorService.answerActivityUpdate(answerActivityBean);
	}
	@RequestMapping(value = "answerActivityUpdateStatus")
	public ResultMap answerActivityUpdateStatus(@ModelAttribute AnswerActivityBean answerActivityBean) {
		return investorService.answerActivityUpdateStatus(answerActivityBean);
	}
	@RequestMapping(value = "answerActivityDel")
	public ResultMap answerActivityDel(@ModelAttribute AnswerActivityBean answerActivityBean) {
		return investorService.answerActivityDel(answerActivityBean);
	}
	
	@RequestMapping(value = "aaQuestionList")
	public ResultMap aaQuestionList(@ModelAttribute AAQuestionBean aaQuestionBean) {
		return investorService.aaQuestionList(aaQuestionBean);
	}
	@RequestMapping(value = "aaQuestionAdd")
	public ResultMap aaQuestionAdd(@ModelAttribute AAQuestionBean aaQuestionBean) {
		return investorService.aaQuestionAdd(aaQuestionBean);
	}
	@RequestMapping(value = "aaQuestionUpdate")
	public ResultMap aaQuestionUpdate(@ModelAttribute AAQuestionBean aaQuestionBean) {
		return investorService.aaQuestionUpdate(aaQuestionBean);
	}
	@RequestMapping(value = "aaQuestionDel")
	public ResultMap aaQuestionDel(@ModelAttribute AAQuestionBean aaQuestionBean) {
		return investorService.aaQuestionDel(aaQuestionBean);
	}
	@RequestMapping(value = "aaOptionList")
	public ResultMap aaOptionList(@ModelAttribute AAOptionBean aaOptionBean) {
		return investorService.aaOptionList(aaOptionBean);
	}
	

}
