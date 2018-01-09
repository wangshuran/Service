package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投资人扩展表
 * 
 * @author breeze
 *
 */
@Table(name = "investor_ext")
public class InvestorExt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long userId;
	private String email;
	private Integer inviteNum;// 邀请人数
	private BigDecimal inviteReward;// 邀请奖励数

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getInviteNum() {
		return inviteNum;
	}

	public void setInviteNum(Integer inviteNum) {
		this.inviteNum = inviteNum;
	}

	public BigDecimal getInviteReward() {
		return inviteReward;
	}

	public void setInviteReward(BigDecimal inviteReward) {
		this.inviteReward = inviteReward;
	}

}
