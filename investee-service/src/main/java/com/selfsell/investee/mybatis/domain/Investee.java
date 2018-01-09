package com.selfsell.investee.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author breeze
 *
 */
@Table(name = "investee")
public class Investee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	private String firstName;
	private String lastName;
	private String mobile;
	private String email;
	private String identityType;
	private String identityId;
	private String introImgUrl;
	private String introVedioUrl;
	private String educationBg;
	private String careerBg;
	private String salary;
	private String usePlan;
	private String status;
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public String getIntroImgUrl() {
		return introImgUrl;
	}
	public void setIntroImgUrl(String introImgUrl) {
		this.introImgUrl = introImgUrl;
	}
	public String getIntroVedioUrl() {
		return introVedioUrl;
	}
	public void setIntroVedioUrl(String introVedioUrl) {
		this.introVedioUrl = introVedioUrl;
	}
	public String getEducationBg() {
		return educationBg;
	}
	public void setEducationBg(String educationBg) {
		this.educationBg = educationBg;
	}
	public String getCareerBg() {
		return careerBg;
	}
	public void setCareerBg(String careerBg) {
		this.careerBg = careerBg;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getUsePlan() {
		return usePlan;
	}
	public void setUsePlan(String usePlan) {
		this.usePlan = usePlan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
