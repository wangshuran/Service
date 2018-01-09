package com.selfsell.investee.share;

import java.io.Serializable;
import java.util.List;


import java.util.Date;


/**
 * 募资人员列表
 *
 * messageId[investeeList]
 * 
 * @author breeze
 * 
 */
public class InvesteeListRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public static class ElementResultList {

		private Long id;
		
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
		
	
		/**
		 * @return 主键ID
		 */
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	
		
		/**
		 * @return 名
		 */
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}


	
		
		/**
		 * @return 姓
		 */
		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}


	
		
		/**
		 * @return 电话
		 */
		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
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
		 * @return 身份类型
		 */
		public String getIdentityType() {
			return identityType;
		}

		public void setIdentityType(String identityType) {
			this.identityType = identityType;
		}


	
		
		/**
		 * @return 身份ID
		 */
		public String getIdentityId() {
			return identityId;
		}

		public void setIdentityId(String identityId) {
			this.identityId = identityId;
		}


	
		
		/**
		 * @return 介绍图像地址
		 */
		public String getIntroImgUrl() {
			return introImgUrl;
		}

		public void setIntroImgUrl(String introImgUrl) {
			this.introImgUrl = introImgUrl;
		}


	
		
		/**
		 * @return 介绍视频地址
		 */
		public String getIntroVedioUrl() {
			return introVedioUrl;
		}

		public void setIntroVedioUrl(String introVedioUrl) {
			this.introVedioUrl = introVedioUrl;
		}


	
		
		/**
		 * @return 教育背景
		 */
		public String getEducationBg() {
			return educationBg;
		}

		public void setEducationBg(String educationBg) {
			this.educationBg = educationBg;
		}


	
		
		/**
		 * @return 职业背景
		 */
		public String getCareerBg() {
			return careerBg;
		}

		public void setCareerBg(String careerBg) {
			this.careerBg = careerBg;
		}


	
		
		/**
		 * @return 财产薪质情况
		 */
		public String getSalary() {
			return salary;
		}

		public void setSalary(String salary) {
			this.salary = salary;
		}


	
		
		/**
		 * @return 使用计划
		 */
		public String getUsePlan() {
			return usePlan;
		}

		public void setUsePlan(String usePlan) {
			this.usePlan = usePlan;
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
		 * @return 创建时间
		 */
		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	}

	private Long totalCount;
	private List<ElementResultList> resultList;

	/**
	 * @return 总条数
	 */
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return 
	 */
	public List<ElementResultList> getResultList() {
		return resultList;
	}

	public void setResultList(List<ElementResultList> resultList) {
		this.resultList = resultList;
	}

}