package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.share.WBaaStage;
import com.selfsell.investor.share.WBrecordStatus;

/**
 * 答题活动
 * 
 * @author breeze
 *
 */
@Table(name = "answer_activity")
public class AnswerActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;// 活动主题
	private Date preHeatTime;//预热时间
	private Date startTime;// 开始时间
	private BigDecimal reward;// 活动奖励
	private WBrecordStatus status;//状态
	private WBaaStage stage;//阶段

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public BigDecimal getReward() {
		return reward;
	}

	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}

	public WBrecordStatus getStatus() {
		return status;
	}

	public void setStatus(WBrecordStatus status) {
		this.status = status;
	}

	public Date getPreHeatTime() {
		return preHeatTime;
	}

	public void setPreHeatTime(Date preHeatTime) {
		this.preHeatTime = preHeatTime;
	}

	public WBaaStage getStage() {
		return stage;
	}

	public void setStage(WBaaStage stage) {
		this.stage = stage;
	}

}
