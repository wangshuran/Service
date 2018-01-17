package com.selfsell.investor.share;

import java.io.Serializable;

import java.math.BigDecimal;



/**
 * 资金计划
 *
 * messageId[fundPlan]
 * 
 * @author breeze
 * 
 */
public class FundPlanRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String iconUrl;
	private BigDecimal annualRate;
	private Integer term;
	private String termUnit;
	private String title;
	private String remark;

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
	 * @return 图标
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return 年收益率
	 */
	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}
	/**
	 * @return 期限
	 */
	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}
		
	/**
	 * @return 期限单位
	 */
	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}

		
	/**
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

		
	/**
	 * @return 描述
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}