package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 检查google验证
 *
 * messageId[checkGoogleAuth]
 * 
 * @author breeze
 * 
 */
public class CheckGoogleAuthREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
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
	 * @return google验证码
	 */
	public String getGoogleAuthCode() {
		return googleAuthCode;
	}

	public void setGoogleAuthCode(String googleAuthCode) {
		this.googleAuthCode = googleAuthCode;
	}


}