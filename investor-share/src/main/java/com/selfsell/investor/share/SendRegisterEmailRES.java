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
public class SendRegisterEmailRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Integer timeAlive;

	/**
	 * @return 存活时间
	 */
	public Integer getTimeAlive() {
		return timeAlive;
	}

	public void setTimeAlive(Integer timeAlive) {
		this.timeAlive = timeAlive;
	}

}