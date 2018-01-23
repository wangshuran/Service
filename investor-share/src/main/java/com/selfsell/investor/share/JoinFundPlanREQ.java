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

}