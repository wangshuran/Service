package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投资人google验证
 * 
 * @author breeze
 *
 */
@Table(name = "investor_google_auth")
public class InvestorGoogleAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private Long investorId;// 投资人ID
	private String googleAuthKey;// google授权key
	private String googleAuthQrcode;// google授权二维码
	private Date updateTime;// 更新时间

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

	public String getGoogleAuthKey() {
		return googleAuthKey;
	}

	public void setGoogleAuthKey(String googleAuthKey) {
		this.googleAuthKey = googleAuthKey;
	}

	public String getGoogleAuthQrcode() {
		return googleAuthQrcode;
	}

	public void setGoogleAuthQrcode(String googleAuthQrcode) {
		this.googleAuthQrcode = googleAuthQrcode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
