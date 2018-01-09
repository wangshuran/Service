package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 发送邮件
 *
 * messageId[sendEmail]
 * 
 * @author breeze
 * 
 */
public class SendEmailREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String email;
	private String type;

		
	/**
	 * @return 发送邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

		
	/**
	 * @return 发送邮件类型REGISTER、RESETPWD
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}