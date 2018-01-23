package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 退出资金计划
 *
 * messageId[quitFundPlan]
 * 
 * @author breeze
 * 
 */
public class QuitFundPlanREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private Long recordId;

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
	 * @return 记录ID
	 */
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

}