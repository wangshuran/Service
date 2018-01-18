package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 交易记录
 * 
 * @author breeze
 *
 */
@Table(name = "scan_trade_record")
public class ScanTradeRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 转出地址
	 */
	private String fromAddress;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 交易数量
	 */
	private BigDecimal amount;
	/**
	 * 交易时间
	 */
	private Date time;
	/**
	 * 交易索引
	 */
	private String txIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTxIndex() {
		return txIndex;
	}

	public void setTxIndex(String txIndex) {
		this.txIndex = txIndex;
	}

}
