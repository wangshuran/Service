package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.share.WBlang;

@Table(name = "fund_plan_lang")
public class FundPlanLang implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long fundPlanId;
	private WBlang lang;//多语言
	private String title;//计划标题
	private String remark;//计划描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFundPlanId() {
		return fundPlanId;
	}

	public void setFundPlanId(Long fundPlanId) {
		this.fundPlanId = fundPlanId;
	}

	public WBlang getLang() {
		return lang;
	}

	public void setLang(WBlang lang) {
		this.lang = lang;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
