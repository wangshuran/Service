package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.bean.GoogleAuthStatus;

/**
 * 
 * @author breeze
 *
 */
@Table(name = "investor")
public class Investor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;// 主键ID
	private String email;// 邮箱
	private String password;// 密码
	private String status;// 状态
	private GoogleAuthStatus googleAuthStatus;// google验证
	private String inviteCode;// 邀请码
	private Date createTime;// 注册时间
	private String sscAddress;//分配ssc地址

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public GoogleAuthStatus getGoogleAuthStatus() {
		return googleAuthStatus;
	}

	public void setGoogleAuthStatus(GoogleAuthStatus googleAuthStatus) {
		this.googleAuthStatus = googleAuthStatus;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSscAddress() {
		return sscAddress;
	}

	public void setSscAddress(String sscAddress) {
		this.sscAddress = sscAddress;
	}

}
