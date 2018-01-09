package com.selfsell.investor.service;

import com.selfsell.investor.share.InviteInfoREQ;
import com.selfsell.investor.share.InviteInfoRES;

/**
 * 邀请服务
 * 
 * @author breeze
 *
 */
public interface InviteService {

	/**
	 * 设置用户邀请码
	 * 
	 * @param userId
	 */
	void setUserInviteCode(Long userId);

	/**
	 * 邀请处理
	 * 
	 * @param userId
	 * @param inviteCode
	 */
	void invite(Long userId, String inviteCode);

	/**
	 * 邀请信息
	 * 
	 * @param inviteInfoREQ
	 * @return
	 */
	InviteInfoRES inviteInfo(InviteInfoREQ inviteInfoREQ);

}
