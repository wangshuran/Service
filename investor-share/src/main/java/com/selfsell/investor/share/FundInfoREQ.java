package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 资金情况
 *
 * messageId[fundInfo]
 * 
 * @author breeze
 * 
 */
public class FundInfoREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;

	/**
	 * @return 用户ID
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}