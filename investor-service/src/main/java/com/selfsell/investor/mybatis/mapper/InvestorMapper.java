package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.Investor;

import tk.mybatis.mapper.common.Mapper;

public interface InvestorMapper extends Mapper<Investor> {

	@Update("update investor set invite_code=#{inviteCode} where id=#{userId}")
	public void updateInviteCode(@Param(value = "userId") Long userId, @Param(value = "inviteCode") String inviteCode);

	@Update("update investor set google_auth_status=#{googleAuthStatus} where id=#{id}")
	public void updateGoogleAuthStatus(@Param(value = "id") Long id,
			@Param(value = "googleAuthStatus") String googleAuthStatus);

	@Update("update investor set password=#{password} where id=#{id}")
	public void resetPwd(@Param(value = "id") Long id, @Param(value = "password") String password);

	@Update("update investor set status=#{status} where id=#{id}")
	public void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);

	@Update("update investor set capital_password=#{password} where id=#{id}")
	public void updateCapitalPassword(@Param(value = "id") Long id, @Param(value = "password") String password);
}
