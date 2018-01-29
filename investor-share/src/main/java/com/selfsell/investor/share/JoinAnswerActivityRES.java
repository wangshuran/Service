package com.selfsell.investor.share;

import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;

import java.util.Date;


/**
 * 加入答题活动
 *
 * messageId[joinAnswerActivity]
 * 
 * @author breeze
 * 
 */
public class JoinAnswerActivityRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public static class ElementRewardList {

		private String email;
		
		private BigDecimal reward;
		
	
		
		/**
		 * @return 账户邮箱
		 */
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}


	
		/**
		 * @return 资金数量
		 */
		public BigDecimal getReward() {
			return reward;
		}

		public void setReward(BigDecimal reward) {
			this.reward = reward;
		}

	}

	public static class ElementPersonInfo {

		private BigDecimal reward;
		
		private Long rank;
		
		private Integer rcNum;
		
	
		/**
		 * @return 奖金
		 */
		public BigDecimal getReward() {
			return reward;
		}

		public void setReward(BigDecimal reward) {
			this.reward = reward;
		}

	
		/**
		 * @return 排名
		 */
		public Long getRank() {
			return rank;
		}

		public void setRank(Long rank) {
			this.rank = rank;
		}

	
		/**
		 * @return 复活卡数量
		 */
		public Integer getRcNum() {
			return rcNum;
		}

		public void setRcNum(Integer rcNum) {
			this.rcNum = rcNum;
		}

	}

	private String stage;
	private Date startTime;
	private BigDecimal reward;
	private Date now;
	private List<ElementRewardList> rewardList;
	private ElementPersonInfo personInfo;
	private Long joinNum;

		
	/**
	 * @return 当前状态
	 */
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	/**
	 * @return 开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return 资金
	 */
	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	/**
	 * @return 当前时间
	 */
	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
	/**
	 * @return 资金排名
	 */
	public List<ElementRewardList> getRewardList() {
		return rewardList;
	}

	public void setRewardList(List<ElementRewardList> rewardList) {
		this.rewardList = rewardList;
	}
	/**
	 * @return 个人活动信息
	 */
	public ElementPersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(ElementPersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	/**
	 * @return 加入人数
	 */
	public Long getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(Long joinNum) {
		this.joinNum = joinNum;
	}

}