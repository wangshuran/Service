package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 投资人开启google验证
 *
 * messageId[investorEnableGoogleAuth]
 * 
 * @author breeze
 * 
 */
public class InvestorEnableGoogleAuthREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private Integer step;
	private String googleAuthCode;

	/**
	 * @return 用户ID
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return 开启步骤，0/1
	 */
	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}
		
	/**
	 * @return google验证码，step为1时必填
	 */
	public String getGoogleAuthCode() {
		return googleAuthCode;
	}

	public void setGoogleAuthCode(String googleAuthCode) {
		this.googleAuthCode = googleAuthCode;
	}


}