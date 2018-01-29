package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 新增资金密码
 *
 * messageId[capitalPasswordAdd]
 * 
 * @author breeze
 * 
 */
public class CapitalPasswordAddREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String capitalPassword;
	private String googleAuthCode;
	private String loginPassword;

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

		
	/**
	 * @return 登录密码
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


}