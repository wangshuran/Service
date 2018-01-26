package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.share.WBinout;
import com.selfsell.investor.share.WBrcChannel;

/**
 * 复活卡记录
 * 
 * @author breeze
 *
 */
@Table(name = "resurrection_card_record")
public class ResurrectionCardRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;// 主键ID
	private Long userId;// 用户ID
	private Integer amount;// 复活卡数
	private Date createTime;// 创建时间
	private WBinout inoutFlag;// 收支标识
	private WBrcChannel channel;// 获取使用渠道

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public WBinout getInoutFlag() {
		return inoutFlag;
	}

	public void setInoutFlag(WBinout inoutFlag) {
		this.inoutFlag = inoutFlag;
	}

	public WBrcChannel getChannel() {
		return channel;
	}

	public void setChannel(WBrcChannel channel) {
		this.channel = channel;
	}

}
