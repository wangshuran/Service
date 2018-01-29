package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 使用复活卡
 *
 * messageId[useResurrectionCard]
 * 
 * @author breeze
 * 
 */
public class UseResurrectionCardRES implements Serializable{

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