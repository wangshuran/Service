package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.FundPlan;

import tk.mybatis.mapper.common.Mapper;

public interface FundPlanMapper extends Mapper<FundPlan> {

	@Update("update fund_plan set status=#{status} where id=#{id}")
	void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);

}
