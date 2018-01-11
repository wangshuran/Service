package com.selfsell.investor.share;

import java.io.Serializable;

public class AppBannerListREQ implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;

	private String title;
	private String status;
	private String lang;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
