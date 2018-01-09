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
public class InvestorEnableGoogleAuthRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String googleAuthKey;
	private String googleAuthQrcode;

		
	/**
	 * @return goolge验证密码
	 */
	public String getGoogleAuthKey() {
		return googleAuthKey;
	}

	public void setGoogleAuthKey(String googleAuthKey) {
		this.googleAuthKey = googleAuthKey;
	}

		
	/**
	 * @return google验证器二维码
	 */
	public String getGoogleAuthQrcode() {
		return googleAuthQrcode;
	}

	public void setGoogleAuthQrcode(String googleAuthQrcode) {
		this.googleAuthQrcode = googleAuthQrcode;
	}


}