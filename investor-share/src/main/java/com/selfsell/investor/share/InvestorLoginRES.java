package com.selfsell.investor.share;

import java.io.Serializable;


import java.util.Date;


/**
 * 投资人登录
 *
 * messageId[investorLogin]
 * 
 * @author breeze
 * 
 */
public class InvestorLoginRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public static class ElementInvestor {

		private Long id;
		
		private String email;
		
		private String status;
		
		private String googleAuthStatus;
		
		private String inviteCode;
		
		private Date createTime;
		
	
		/**
		 * @return 主键id
		 */
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	
		
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
		 * @return 状态
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}


	
		
		/**
		 * @return google验证状态
		 */
		public String getGoogleAuthStatus() {
			return googleAuthStatus;
		}

		public void setGoogleAuthStatus(String googleAuthStatus) {
			this.googleAuthStatus = googleAuthStatus;
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


	
		/**
		 * @return 注册时间
		 */
		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	}

	private String token;
	private ElementInvestor investor;

		
	/**
	 * @return 授权令牌
	 */
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return 投资人
	 */
	public ElementInvestor getInvestor() {
		return investor;
	}

	public void setInvestor(ElementInvestor investor) {
		this.investor = investor;
	}

}