package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 邀请信息
 *
 * messageId[inviteInfo]
 * 
 * @author breeze
 * 
 */
public class InviteInfoREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;

	/**
	 * @return 用户ID
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}