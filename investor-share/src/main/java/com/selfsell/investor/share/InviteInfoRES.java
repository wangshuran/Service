package com.selfsell.investor.share;

import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;



/**
 * 邀请信息
 *
 * messageId[inviteInfo]
 * 
 * @author breeze
 * 
 */
public class InviteInfoRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public static class ElementRewardRankList {

		private String email;
		
		private BigDecimal reward;
		
	
		
		/**
		 * @return 账号邮箱
		 */
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}


	
		/**
		 * @return 奖励金额
		 */
		public BigDecimal getReward() {
			return reward;
		}

		public void setReward(BigDecimal reward) {
			this.reward = reward;
		}

	}

	private String inviteCode;
	private Integer inviteNum;
	private BigDecimal reward;
	private List<ElementRewardRankList> rewardRankList;

		
	/**
	 * @return 邀请码
	 */
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	/**
	 * @return 邀请好友数量
	 */
	public Integer getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}
	/**
	 * @return 奖励金额
	 */
	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	/**
	 * @return 奖励排名列表
	 */
	public List<ElementRewardRankList> getRewardRankList() {
		return rewardRankList;
	}

	public void setRewardRankList(List<ElementRewardRankList> rewardRankList) {
		this.rewardRankList = rewardRankList;
	}

}