package com.selfsell.investor.share;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * 交易记录
 *
 * messageId[tradeInfo]
 * 
 * @author breeze
 * 
 */
public class TradeInfoRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Date time;
	private String type;
	private String remark;
	private BigDecimal amount;

	/**
	 * @return 交易时间
	 */
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
		
	/**
	 * @return 交易类型
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

		
	/**
	 * @return 交易描述
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return 交易数量
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}