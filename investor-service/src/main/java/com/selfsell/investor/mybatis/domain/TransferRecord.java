package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.bean.TradeRecordStatus;

@Table(name = "transfer_record")
public class TransferRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long investorId;// 投资人ID
	private Date createTime;// 记录时间
	private BigDecimal amount;// 交易数量
	private BigDecimal fee;// 手续费
	private String address;// 转出地址
	private TradeRecordStatus status;// 当前状态
	private String txId;// 转账交易流水号
	private String remark;//备注
	private Long tradeRecordId;//交易记录ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public TradeRecordStatus getStatus() {
		return status;
	}

	public void setStatus(TradeRecordStatus status) {
		this.status = status;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTradeRecordId() {
		return tradeRecordId;
	}

	public void setTradeRecordId(Long tradeRecordId) {
		this.tradeRecordId = tradeRecordId;
	}

}
