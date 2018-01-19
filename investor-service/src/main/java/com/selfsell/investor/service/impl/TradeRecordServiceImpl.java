package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.mybatis.mapper.InvestorMapper;
import com.selfsell.investor.mybatis.mapper.TradeRecordMapper;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.share.TradeInfoREQ;
import com.selfsell.investor.share.TradeInfoRES;

import tk.mybatis.mapper.entity.Example;

@Component
public class TradeRecordServiceImpl implements TradeRecordService {
	@Autowired
	TradeRecordMapper tradeRecordMapper;
	
	@Autowired
	I18nService i18nService;

	@Autowired
	InvestorMapper investorMapper;

	@Override
	public void insert(TradeRecord tradeRecord) {

		tradeRecordMapper.insert(tradeRecord);

	}

	@Override
	public void updateTranserStatus(String txId, TradeRecordStatus status) {
		
		tradeRecordMapper.updateTranserStatus(txId,status.name());
		
	}

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
				tradeInfo.setIoFlag(tradeRecord.getInoutFlag().name());
				tradeInfo.setStatus(tradeRecord.getStatus().name());
				tradeInfo.setTradeType(tradeRecord.getType().name());
				resultList.add(tradeInfo);
			}
		}
		return resultList;
	}

	public List<TradeRecord> queryInvestorTrade(Long investorId) {
		Example example = new Example(TradeRecord.class);
		example.createCriteria().andEqualTo("investorId", investorId);
		
		example.orderBy("createTime").desc();

		return tradeRecordMapper.selectByExample(example);
	}

	@Override
	public void updateStatus(Long tradeRecordId, TradeRecordStatus status) {
		tradeRecordMapper.updateStatus(tradeRecordId, status.name());
		
	}
}
