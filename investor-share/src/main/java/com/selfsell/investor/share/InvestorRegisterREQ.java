package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 投资人注册
 *
 * messageId[investorRegister]
 * 
 * @author breeze
 * 
 */
public class InvestorRegisterREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String email;
	private String emailCheckCode;
	private String password;
	private String inviteCode;

		
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
	 * @return 邮箱验证码
	 */
	public String getEmailCheckCode() {
		return emailCheckCode;
	}

	public void setEmailCheckCode(String emailCheckCode) {
		this.emailCheckCode = emailCheckCode;
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
	 * @return 邀请码
	 */
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}


}