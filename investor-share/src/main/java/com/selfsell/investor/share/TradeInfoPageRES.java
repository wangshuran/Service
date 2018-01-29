package com.selfsell.investor.share;

import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;

import java.util.Date;


/**
 * 交易信息（分页）
 *
 * messageId[tradeInfoPage]
 * 
 * @author breeze
 * 
 */
public class TradeInfoPageRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public static class ElementTradeList {

		private Date time;
		
		private String tradeType;
		
		private String remark;
		
		private BigDecimal amount;
		
		private String ioFlag;
		
		private String status;
		
	
		/**
		 * @return 交易时间
		 */
		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

	
		
		/**
		 * @return 交易类型
		 */
		public String getTradeType() {
			return tradeType;
		}

		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}


	
		
		/**
		 * @return 交易描述
		 */
		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}


	
		/**
		 * @return 交易金额
		 */
		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

	
		
		/**
		 * @return 收支标识
		 */
		public String getIoFlag() {
			return ioFlag;
		}

		public void setIoFlag(String ioFlag) {
			this.ioFlag = ioFlag;
		}


	
		
		/**
		 * @return 交易状态
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}


	}

	private Long totalAmount;
	private List<ElementTradeList> tradeList;

	/**
	 * @return 总记录数
	 */
	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return 交易记录列表
	 */
	public List<ElementTradeList> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<ElementTradeList> tradeList) {
		this.tradeList = tradeList;
	}

}