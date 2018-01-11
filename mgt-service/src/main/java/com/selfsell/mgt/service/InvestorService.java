package com.selfsell.mgt.service;

import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;

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

}
