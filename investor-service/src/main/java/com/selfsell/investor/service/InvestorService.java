package com.selfsell.investor.service;

import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.share.FundInfoREQ;
import com.selfsell.investor.share.FundInfoRES;
import com.selfsell.investor.share.InvestorDisableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorDisableGoogleAuthRES;
import com.selfsell.investor.share.InvestorEnableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorEnableGoogleAuthRES;
import com.selfsell.investor.share.InvestorLoginREQ;
import com.selfsell.investor.share.InvestorLoginRES;
import com.selfsell.investor.share.InvestorRegisterREQ;
import com.selfsell.investor.share.InvestorRegisterRES;
import com.selfsell.investor.share.InvestorResetPasswordREQ;
import com.selfsell.investor.share.InvestorResetPasswordRES;
import com.selfsell.investor.share.ModifyPasswordREQ;
import com.selfsell.investor.share.ModifyPasswordRES;

/**
 * 投资人服务
 * 
 * @author breeze
 *
 */
public interface InvestorService {

	/**
	 * 投资人注册
	 * 
	 * @param investorRegisterREQ
	 * @return
	 */
	InvestorRegisterRES register(InvestorRegisterREQ investorRegisterREQ);

	/**
	 * 投资人登录
	 * 
	 * @param investorLoginREQ
	 * @return
	 */
	InvestorLoginRES login(InvestorLoginREQ investorLoginREQ);

	Investor queryByEmail(String email);

	/**
	 * 更新google验证信息
	 * 
	 * @param userName
	 * @param secretKey
	 */
	void insertGoogleAuthKey(String userName, String secretKey);

	/**
	 * 投资人开启google验证
	 * 
	 * @param enableGoogleAuthREQ
	 * @return
	 */
	InvestorEnableGoogleAuthRES enableGoogleAuth(InvestorEnableGoogleAuthREQ enableGoogleAuthREQ);

	/**
	 * 投资人关闭google验证
	 * 
	 * @param disableGoogleAuthREQ
	 * @return
	 */
	InvestorDisableGoogleAuthRES disableGoogleAuth(InvestorDisableGoogleAuthREQ disableGoogleAuthREQ);

	/**
	 * 重置密码
	 * 
	 * @param resetPasswordREQ
	 * @return
	 */
	InvestorResetPasswordRES resetPassword(InvestorResetPasswordREQ resetPasswordREQ);

	/**
	 * 修改密码
	 * 
	 * @param modifyPasswordREQ
	 * @return
	 */
	ModifyPasswordRES modifyPassword(ModifyPasswordREQ modifyPasswordREQ);

	/**
	 * 资金情况
	 * 
	 * @param fundInfoREQ
	 * @return
	 */
	FundInfoRES fundInfo(FundInfoREQ fundInfoREQ);

}
