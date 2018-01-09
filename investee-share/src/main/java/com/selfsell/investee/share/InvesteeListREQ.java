package com.selfsell.investee.share;

import java.io.Serializable;




/**
 * 募资人员列表
 *
 * messageId[investeeList]
 * 
 * @author breeze
 * 
 */
public class InvesteeListREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String email;
	private String mobile;
	private String identityId;
	private Integer page;
	private Integer limit;

		
	/**
	 * @return 邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		
	/**
	 * @return 手机号
	 */
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

		
	/**
	 * @return 身份ID
	 */
	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	/**
	 * @return 获取页数
	 */
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	/**
	 * @return 分页大小
	 */
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}