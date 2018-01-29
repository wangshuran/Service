package com.selfsell.investor.mybatis.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.InvestorExt;

import tk.mybatis.mapper.common.Mapper;

public interface InvestorExtMapper extends Mapper<InvestorExt> {

	@Update("update investor_ext set invite_num = invite_num + 1,invite_reward = invite_reward +#{inviteReward} where user_id=#{userId}")
	void updateInvite(@Param(value = "userId") Long userId, @Param(value = "inviteReward") BigDecimal inviteReward);

	@Update("update investor_ext set resurrection_card = #{resurrectionCard} where user_id=#{userId}")
	void updateResurrectionCardNum(@Param(value = "userId") Long userId,
			@Param(value = "resurrectionCard") Integer resurrectionCard);

	@Update("update investor_ext set total_ssc = #{totalSSC},available_ssc = #{availableSSC} where user_id=#{userId}")
	void updateAssets(@Param(value = "userId")Long userId, @Param(value = "totalSSC")BigDecimal totalSSC, @Param(value = "availableSSC")BigDecimal availableSSC);

}
