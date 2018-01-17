package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.selfsell.investor.mybatis.domain.FundPlanLang;

import tk.mybatis.mapper.common.Mapper;

public interface FundPlanLangMapper extends Mapper<FundPlanLang> {

	@Delete("delete from fund_plan_lang where fund_plan_id=#{fundPlanId}")
	void delByFundPlanId(@Param(value = "fundPlanId") Long fundPlanId);

}
