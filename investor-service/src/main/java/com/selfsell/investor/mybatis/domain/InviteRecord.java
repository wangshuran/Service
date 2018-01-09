package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 邀请记录
 * 
 * @author breeze
 *
 */
@Table(name = "invite_record")
public class InviteRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;// 主键ID
	private Long userId;// 用户ID
	private Long inviterId;// 邀请人ID
	private BigDecimal inviteReward;// 邀请奖励
	private Date createTime;// 创建时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public BigDecimal getInviteReward() {
		return inviteReward;
	}

	public void setInviteReward(BigDecimal inviteReward) {
		this.inviteReward = inviteReward;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
