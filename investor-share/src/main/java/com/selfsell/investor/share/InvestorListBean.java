package com.selfsell.investor.share;

import java.io.Serializable;

/**
 * 投资人查询列表属性bean
 * 
 * @author breeze
 *
 */
public class InvestorListBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;

	private String email;
	private String inviteCode;
	private String status;
	private String sscAddress;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSscAddress() {
		return sscAddress;
	}

	public void setSscAddress(String sscAddress) {
		this.sscAddress = sscAddress;
	}
}
