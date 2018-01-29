package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 购买复活卡
 *
 * messageId[buyResurrectionCard]
 * 
 * @author breeze
 * 
 */
public class BuyResurrectionCardREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private Integer amount;
	private String capitalPassword;
	private String googleAuthCode;

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
	 * @return 购买数量
	 */
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

		
	/**
	 * @return google验证码
	 */
	public String getGoogleAuthCode() {
		return googleAuthCode;
	}

	public void setGoogleAuthCode(String googleAuthCode) {
		this.googleAuthCode = googleAuthCode;
	}


}