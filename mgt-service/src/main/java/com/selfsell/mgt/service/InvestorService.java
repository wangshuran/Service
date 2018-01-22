package com.selfsell.mgt.service;

import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.TransferBean;

public interface InvestorService {

	ResultMap appBannerList(AppBannerListREQ appBannerListREQ);

	/**
	 * 新增banner
	 * 
	 * @param file
	 * @param appBannerBean
	 * @return
	 */
	ResultMap appBannerAdd(MultipartFile file, AppBannerBean appBannerBean);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	ResultMap appBannerDel(Long id);

	/**
	 * 更新欢迎页状态
	 * 
	 * @param appBannerBean
	 * @return
	 */
	ResultMap updateStatus(AppBannerBean appBannerBean);

	ResultMap appBannerUpdate(MultipartFile file, AppBannerBean appBannerBean);

	/**
	 * 资金计划
	 * @param fundPlanBean
	 * @return
	 */
	ResultMap fundPlanList(FundPlanBean fundPlanBean);

	/**
	 * 资金计划多语言
	 * @param fundPlanLangBean
	 * @return
	 */
	ResultMap fundPlanLangList(FundPlanLangBean fundPlanLangBean);

	ResultMap fundPlanAdd(FundPlanBean fundPlanBean,MultipartFile file);

	ResultMap fundPlanUpdate(FundPlanBean fundPlanBean,MultipartFile file);

	ResultMap fundPlanDel(Long id);

	ResultMap fundPlanUpdateStatus(FundPlanBean fundPlanBean);

	ResultMap transferList(TransferBean transferBean);

	/**
	 * 提现审批
	 * @param transferBean
	 * @return
	 */
	ResultMap transferAudit(TransferBean transferBean);

	/**
	 * 投资人列表 
	 * @param investorListBean
	 * @return
	 */
	ResultMap list(InvestorListBean investorListBean);

	/**
	 * 更新投资人状态
	 * @param investorListBean
	 * @return
	 */
	ResultMap updateStatus(InvestorBean investorBean);

}
