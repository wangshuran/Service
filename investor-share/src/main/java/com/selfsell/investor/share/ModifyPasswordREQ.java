package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 更改密码
 *
 * messageId[modifyPassword]
 * 
 * @author breeze
 * 
 */
public class ModifyPasswordREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String password;
	private String newPassword;

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
	 * @return 当前密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

		
	/**
	 * @return 新密码
	 */
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}