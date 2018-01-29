package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 交易信息（分页）
 *
 * messageId[tradeInfoPage]
 * 
 * @author breeze
 * 
 */
public class TradeInfoPageREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private Integer page;
	private Integer limit;

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
	 * @return 请求页数
	 */
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	/**
	 * @return 每页条数
	 */
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}