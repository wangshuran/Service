package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.mybatis.mapper.InvestorMapper;
import com.selfsell.investor.mybatis.mapper.TradeRecordMapper;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.service.TradeService;
import com.selfsell.investor.share.TradeInfoREQ;
import com.selfsell.investor.share.TradeInfoRES;

import tk.mybatis.mapper.entity.Example;

@Component
public class TradeServiceImpl implements TradeService {
	@Autowired
	TradeRecordMapper tradeRecordMapper;

	@Autowired
	I18nService i18nService;

	@Autowired
	InvestorMapper investorMapper;

	@Override
	public List<TradeInfoRES> tradeInfo(TradeInfoREQ tradeInfoREQ) {
		CheckParamUtil.checkBoolean(tradeInfoREQ.getId() == null, i18nService.getMessage(I18nMessageCode.PC_1000_05));

		Investor investor = investorMapper.selectByPrimaryKey(tradeInfoREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, tradeInfoREQ.getId()));
		}

		List<TradeInfoRES> resultList = Lists.newArrayList();

		List<TradeRecord> tradeRecords = queryInvestorTrade(tradeInfoREQ.getId());
		if (tradeRecords != null && !tradeRecords.isEmpty()) {
			for (TradeRecord tradeRecord : tradeRecords) {
				TradeInfoRES tradeInfo = new TradeInfoRES();
				tradeInfo.setTime(tradeRecord.getCreateTime());
				tradeInfo.setRemark(tradeRecord.getRemark());
				tradeInfo.setAmount(tradeRecord.getAmount());
				tradeInfo.setType(tradeRecord.getInout().name());
				resultList.add(tradeInfo);
			}
		}
		return resultList;
	}

	public List<TradeRecord> queryInvestorTrade(Long investorId) {
		Example example = new Example(TradeRecord.class);
		example.createCriteria().andEqualTo("investorId", investorId);

		return tradeRecordMapper.selectByExample(example);
	}
}
