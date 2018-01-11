package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.selfsell.investor.share.WBdateUnit;
import com.selfsell.investor.share.WBrecordStatus;

@Table(name = "fund_plan")
public class FundPlan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String iconUrl;// 计划图标
	private BigDecimal annualRate;// 年收益
	private Integer term;// 期限
	private WBdateUnit termUnit;// 期限单位
	private WBrecordStatus status;// 计划状态
	
	@Transient
	private List<FundPlanLang> fundPlanLangs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public WBdateUnit getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(WBdateUnit termUnit) {
		this.termUnit = termUnit;
	}

	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}

	public WBrecordStatus getStatus() {
		return status;
	}

	public void setStatus(WBrecordStatus status) {
		this.status = status;
	}

	public List<FundPlanLang> getFundPlanLangs() {
		return fundPlanLangs;
	}

	public void setFundPlanLangs(List<FundPlanLang> fundPlanLangs) {
		this.fundPlanLangs = fundPlanLangs;
	}

}
