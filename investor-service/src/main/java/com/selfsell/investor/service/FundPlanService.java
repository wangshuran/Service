package com.selfsell.investor.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.FundPlan;
import com.selfsell.investor.share.FundPlanBean;

/**
 * 资金计划服务
 * 
 * @author breeze
 *
 */
public interface FundPlanService {

	/**
	 * 分页查询资金计划
	 * 
	 * @param fundPlanBean
	 * @return
	 */
	PageInfo<FundPlan> list(FundPlanBean fundPlanBean);

	/**
	 * 填充多语言信息
	 * 
	 * @param resultList
	 */
	void fillLang(List<FundPlan> resultList);

	/**
	 * 新增
	 * 
	 * @param fundPlanBean
	 */
	void add(FundPlanBean fundPlanBean);

	/**
	 * 更新
	 * 
	 * @param fundPlanBean
	 */
	void update(FundPlanBean fundPlanBean);

	/**
	 * 更新状态
	 * 
	 * @param fundPlanBean
	 */
	void updateStatus(FundPlanBean fundPlanBean);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void del(Long id);

}
