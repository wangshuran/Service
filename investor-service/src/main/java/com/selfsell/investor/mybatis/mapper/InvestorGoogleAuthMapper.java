package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.InvestorGoogleAuth;

import tk.mybatis.mapper.common.Mapper;

public interface InvestorGoogleAuthMapper extends Mapper<InvestorGoogleAuth> {

	@Update("update investor_google_auth set google_auth_qrcode=#{qrcodeBase64} where investor_id=#{investorId}")
	void updateQrcode(@Param(value = "investorId") Long id, @Param(value = "qrcodeBase64") String qrcodeBase64);

}
