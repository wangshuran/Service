package com.selfsell.investor.share;

import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;

import java.util.Date;


/**
 * 资金情况
 *
 * messageId[fundInfo]
 * 
 * @author breeze
 * 
 */
public class FundInfoRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public static class ElementFundDetail {

		private Long planId;
		
		private String planTitle;
		
		private String planIconUrl;
		
		private BigDecimal amount;
		
		private BigDecimal interest;
		
		private Long id;
		
		private Date createTime;
		
		private Date finishTime;
		
	
		/**
		 * @return 计划ID
		 */
		public Long getPlanId() {
			return planId;
		}

		public void setPlanId(Long planId) {
			this.planId = planId;
		}

	
		
		/**
		 * @return 计划标题
		 */
		public String getPlanTitle() {
			return planTitle;
		}

		public void setPlanTitle(String planTitle) {
			this.planTitle = planTitle;
		}


	
		
		/**
		 * @return 计划图标
		 */
		public String getPlanIconUrl() {
			return planIconUrl;
		}

		public void setPlanIconUrl(String planIconUrl) {
			this.planIconUrl = planIconUrl;
		}


	
		/**
		 * @return 购买数量
		 */
		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

	
		/**
		 * @return 当前收益
		 */
		public BigDecimal getInterest() {
			return interest;
		}

		public void setInterest(BigDecimal interest) {
			this.interest = interest;
		}

	
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
		 * @return 开始时间
		 */
		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	
		/**
		 * @return 结束时间
		 */
		public Date getFinishTime() {
			return finishTime;
		}

		public void setFinishTime(Date finishTime) {
			this.finishTime = finishTime;
		}

	}

	private BigDecimal totalSSC;
	private BigDecimal totalPrice;
	private List<ElementFundDetail> fundDetail;
	private BigDecimal availableSSC;

	/**
	 * @return 总SSC数量
	 */
	public BigDecimal getTotalSSC() {
		return totalSSC;
	}

	public void setTotalSSC(BigDecimal totalSSC) {
		this.totalSSC = totalSSC;
	}
	/**
	 * @return 总价值
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return 资产明细
	 */
	public List<ElementFundDetail> getFundDetail() {
		return fundDetail;
	}

	public void setFundDetail(List<ElementFundDetail> fundDetail) {
		this.fundDetail = fundDetail;
	}
	/**
	 * @return 可用SSC
	 */
	public BigDecimal getAvailableSSC() {
		return availableSSC;
	}

	public void setAvailableSSC(BigDecimal availableSSC) {
		this.availableSSC = availableSSC;
	}

}