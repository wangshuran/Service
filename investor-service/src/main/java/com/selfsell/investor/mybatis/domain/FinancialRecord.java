package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.bean.FinancialStatus;

/**
 * 理财记录
 * 
 * @author breeze
 *
 */
@Table(name = "financial_record")
public class FinancialRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long investorId;// 投资人ID
	private Long fundPlanId;// 资金计划
	private BigDecimal annualRate;// 年收益
	private Date createTime;// 记录时间
	private Date startTime;//计息日期
	private Date finishTime;// 正常完成时间
	private Date endTime;// 结束时间
	private BigDecimal amount;// 交易数量
	private BigDecimal interest;// 收益
	private FinancialStatus status;// 理财状态

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvestorId() {
		return investorId;
	}

	public void setInvestorId(Long investorId) {
		this.investorId = investorId;
	}

	public Long getFundPlanId() {
		return fundPlanId;
	}

	public void setFundPlanId(Long fundPlanId) {
		this.fundPlanId = fundPlanId;
	}

	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public FinancialStatus getStatus() {
		return status;
	}

	public void setStatus(FinancialStatus status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
