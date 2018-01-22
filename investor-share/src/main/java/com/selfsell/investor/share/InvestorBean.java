package com.selfsell.investor.share;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author breeze
 *
 */
public class InvestorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;// 主键ID
	private String email;// 邮箱
	private String password;// 密码
	private String status;// 状态
	private String googleAuthStatus;// google验证
	private String inviteCode;// 邀请码
	private Date createTime;// 注册时间
	private String sscAddress;// 分配ssc地址
	private Integer inviteNum;// 邀请人数
	private BigDecimal inviteReward;// 邀请奖励数
	private BigDecimal totalSSC;// 总资产
	private BigDecimal availableSSC;// 可用SSC

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGoogleAuthStatus() {
		return googleAuthStatus;
	}

	public void setGoogleAuthStatus(String googleAuthStatus) {
		this.googleAuthStatus = googleAuthStatus;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSscAddress() {
		return sscAddress;
	}

	public void setSscAddress(String sscAddress) {
		this.sscAddress = sscAddress;
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

	public BigDecimal getTotalSSC() {
		return totalSSC;
	}

	public void setTotalSSC(BigDecimal totalSSC) {
		this.totalSSC = totalSSC;
	}

	public BigDecimal getAvailableSSC() {
		return availableSSC;
	}

	public void setAvailableSSC(BigDecimal availableSSC) {
		this.availableSSC = availableSSC;
	}

}
