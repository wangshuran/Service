package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 购买复活卡
 *
 * messageId[buyResurrectionCard]
 * 
 * @author breeze
 * 
 */
public class BuyResurrectionCardRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Integer amount;

	/**
	 * @return 复活卡数量
	 */
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}