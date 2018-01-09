package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 发送注册邮箱验证码
 *
 * messageId[sendRegisterEmail]
 * 
 * @author breeze
 * 
 */
public class SendRegisterEmailREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String email;

		
	/**
	 * @return 发送邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}