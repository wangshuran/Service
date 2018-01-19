package com.selfsell.investor.share;

import java.io.Serializable;

import java.math.BigDecimal;



/**
 * 获取个人转账信息
 *
 * messageId[queryTransferInfo]
 * 
 * @author breeze
 * 
 */
public class QueryTransferInfoRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private BigDecimal availableSSC;
	private BigDecimal fee;

	/**
	 * @return 可用SSC数量
	 */
	public BigDecimal getAvailableSSC() {
		return availableSSC;
	}

	public void setAvailableSSC(BigDecimal availableSSC) {
		this.availableSSC = availableSSC;
	}
	/**
	 * @return 手续费
	 */
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

}