package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 更新资金密码
 *
 * messageId[capitalPasswordUpdate]
 * 
 * @author breeze
 * 
 */
public class CapitalPasswordUpdateREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String capitalPasswordOld;
	private String capitalPasswordNew;
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
	 * @return 老资金密码
	 */
	public String getCapitalPasswordOld() {
		return capitalPasswordOld;
	}

	public void setCapitalPasswordOld(String capitalPasswordOld) {
		this.capitalPasswordOld = capitalPasswordOld;
	}

		
	/**
	 * @return 新资金密码
	 */
	public String getCapitalPasswordNew() {
		return capitalPasswordNew;
	}

	public void setCapitalPasswordNew(String capitalPasswordNew) {
		this.capitalPasswordNew = capitalPasswordNew;
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