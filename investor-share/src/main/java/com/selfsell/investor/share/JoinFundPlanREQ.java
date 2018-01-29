package com.selfsell.investor.share;

import java.io.Serializable;

import java.math.BigDecimal;



/**
 * 加入资金计划
 *
 * messageId[joinFundPlan]
 * 
 * @author breeze
 * 
 */
public class JoinFundPlanREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private Long fundPlanId;
	private BigDecimal amount;
	private String googleAuthCode;
	private String capitalPassword;

	/**
	 * @return 主键ID
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return 资金计划ID
	 */
	public Long getFundPlanId() {
		return fundPlanId;
	}

	public void setFundPlanId(Long fundPlanId) {
		this.fundPlanId = fundPlanId;
	}
	/**
	 * @return 存入数量
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
		
	/**
	 * @return google验证码
	 */
	public String getGoogleAuthCode() {
		return googleAuthCode;
	}

	public void setGoogleAuthCode(String googleAuthCode) {
		this.googleAuthCode = googleAuthCode;
	}

		
	/**
	 * @return 资金密码
	 */
	public String getCapitalPassword() {
		return capitalPassword;
	}

	public void setCapitalPassword(String capitalPassword) {
		this.capitalPassword = capitalPassword;
	}


}