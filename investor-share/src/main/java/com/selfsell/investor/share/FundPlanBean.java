package com.selfsell.investor.share;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class FundPlanBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer limit;

	private Long id;
	private String iconUrl;
	private BigDecimal annualRate;
	private Integer term;
	private String termUnit;
	private String status;

	private List<FundPlanLangBean> fundPlanLangs;

	public static class FundPlanLangBean implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Long id;
		private Long fundPlanId;
		private String lang;
		private String title;
		private String remark;

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

		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
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

	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FundPlanLangBean> getFundPlanLangs() {
		return fundPlanLangs;
	}

	public void setFundPlanLangs(List<FundPlanLangBean> fundPlanLangs) {
		this.fundPlanLangs = fundPlanLangs;
	}
}
