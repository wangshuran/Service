package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 投资人登录
 *
 * messageId[investorLogin]
 * 
 * @author breeze
 * 
 */
public class InvestorLoginREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String email;
	private String password;
	private String googleAuthCode;

		
	/**
	 * @return 邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		
	/**
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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