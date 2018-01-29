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
public class UseResurrectionCardREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private Integer amount;

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
	 * @return 使用数量
	 */
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}