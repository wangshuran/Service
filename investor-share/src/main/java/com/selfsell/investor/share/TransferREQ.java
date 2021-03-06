package com.selfsell.investor.share;

import java.io.Serializable;

import java.math.BigDecimal;



/**
 * 转账
 *
 * messageId[transfer]
 * 
 * @author breeze
 * 
 */
public class TransferREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private BigDecimal amount;
	private String address;
	private BigDecimal fee;
	private String googleAuthCode;
	private String capitalPassword;

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
	 * @return 转账数量
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
		
	/**
	 * @return 转出地址
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return 手续费
	 */
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
		
	/**
	 * @return google验证码
	 */
	public String getGoogleAuthCode() {
		return googleAuthCode;
	}

	public void setGoogleAuthCode(String googleAuthCode) {
		this.googleAuthCode = googleAuthCode;
	}

		
	/**
	 * @return 资金密码
	 */
	public String getCapitalPassword() {
		return capitalPassword;
	}

	public void setCapitalPassword(String capitalPassword) {
		this.capitalPassword = capitalPassword;
	}


}