package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 获取个人转账信息
 *
 * messageId[queryTransferInfo]
 * 
 * @author breeze
 * 
 */
public class QueryTransferInfoREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;

	/**
	 * @return 主键ID
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}