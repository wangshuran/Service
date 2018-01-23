package com.selfsell.investor.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.bean.FinancialStatus;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.bean.TradeType;
import com.selfsell.investor.mybatis.domain.FinancialRecord;
import com.selfsell.investor.mybatis.domain.FundPlan;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.mybatis.mapper.FinancialRecordMapper;
import com.selfsell.investor.mybatis.mapper.InvestorMapper;
import com.selfsell.investor.service.FinancialService;
import com.selfsell.investor.service.FundPlanService;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.share.JoinFundPlanREQ;
import com.selfsell.investor.share.QuitFundPlanREQ;
import com.selfsell.investor.share.WBdateUnit;
import com.selfsell.investor.share.WBinout;

import tk.mybatis.mapper.entity.Example;

@Component
public class FinancialServiceImpl implements FinancialService {
	@Autowired
	FinancialRecordMapper financialRecordMapper;

	@Autowired
	I18nService i18nService;

	@Autowired
	InvestorMapper investorMapper;

	@Autowired
	FundPlanService fundPlanService;

	@Autowired
	InvestorService investorService;

	@Autowired
	TradeRecordService tradeRecordService;

	@Override
	public List<FinancialRecord> queryInvestorFinancialRecords(Long id) {
		Example example = new Example(FinancialRecord.class);
		example.createCriteria().andEqualTo("status", FinancialStatus.ING.name()).andEqualTo("investorId", id);

		return financialRecordMapper.selectByExample(example);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void joinFundPlan(JoinFundPlanREQ joinFundPlanREQ) {
		CheckParamUtil.checkBoolean(joinFundPlanREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkBoolean(joinFundPlanREQ.getFundPlanId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_12));
		CheckParamUtil.checkBoolean(joinFundPlanREQ.getAmount() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_10));
		Investor investor = investorMapper.selectByPrimaryKey(joinFundPlanREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, joinFundPlanREQ.getId()));
		}

		FundPlan fundPlan = fundPlanService.queryById(joinFundPlanREQ.getFundPlanId());
		if (fundPlan == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.fund_plan_not_exists, joinFundPlanREQ.getFundPlanId()));
		}

		investorService.updateAssets(investor.getId(), WBinout.OUT, joinFundPlanREQ.getAmount(), false);

		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setAmount(joinFundPlanREQ.getAmount());
		tradeRecord.setCreateTime(new Date());
		tradeRecord.setInoutFlag(WBinout.OUT);
		tradeRecord.setInvestorId(investor.getId());
		tradeRecord.setStatus(TradeRecordStatus.success);
		tradeRecord.setType(TradeType.buy_fund_plan);
		tradeRecordService.insert(tradeRecord);

		FinancialRecord financialRecord = new FinancialRecord();
		financialRecord.setAmount(joinFundPlanREQ.getAmount());
		financialRecord.setAnnualRate(fundPlan.getAnnualRate());
		Calendar now = Calendar.getInstance();
		financialRecord.setCreateTime(now.getTime());
		Calendar finish = (Calendar) now.clone();
		if (WBdateUnit.Y.equals(fundPlan.getTermUnit())) {
			finish.add(Calendar.YEAR, fundPlan.getTerm());
		} else if (WBdateUnit.M.equals(fundPlan.getTermUnit())) {
			finish.add(Calendar.MONTH, fundPlan.getTerm());
		} else if (WBdateUnit.D.equals(fundPlan.getTermUnit())) {
			finish.add(Calendar.DAY_OF_YEAR, fundPlan.getTerm());
		}
		financialRecord.setFinishTime(finish.getTime());
		financialRecord.setFundPlanId(fundPlan.getId());
		financialRecord.setInterest(BigDecimal.ZERO);
		financialRecord.setInvestorId(investor.getId());
		financialRecord.setStatus(FinancialStatus.ING);
		financialRecordMapper.insert(financialRecord);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void quitFundPlan(QuitFundPlanREQ quitFundPlanREQ) {
		CheckParamUtil.checkBoolean(quitFundPlanREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkBoolean(quitFundPlanREQ.getRecordId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_13));
		Investor investor = investorMapper.selectByPrimaryKey(quitFundPlanREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, quitFundPlanREQ.getId()));
		}

		FinancialRecord financialRecord = financialRecordMapper.selectByPrimaryKey(quitFundPlanREQ.getRecordId());
		if (financialRecord == null) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.financial_record_id_not_exists,
					quitFundPlanREQ.getRecordId()));
		}

		if (!FinancialStatus.ING.equals(financialRecord.getStatus())) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.financial_record_finished));
		}

		if (financialRecord.getInvestorId().compareTo(investor.getId()) != 0) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.financial_record_not_match));
		}

		investorService.updateAssets(investor.getId(), WBinout.IN, financialRecord.getAmount(), false);

		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setAmount(financialRecord.getAmount());
		tradeRecord.setCreateTime(new Date());
		tradeRecord.setInoutFlag(WBinout.IN);
		tradeRecord.setInvestorId(investor.getId());
		tradeRecord.setStatus(TradeRecordStatus.success);
		tradeRecord.setType(TradeType.capital);
		tradeRecordService.insert(tradeRecord);

		financialRecord.setEndTime(new Date());
		financialRecord.setStatus(FinancialStatus.POVER);
		financialRecordMapper.updateByPrimaryKey(financialRecord);
	}

}
