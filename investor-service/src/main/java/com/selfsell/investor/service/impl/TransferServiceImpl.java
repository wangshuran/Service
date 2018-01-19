package com.selfsell.investor.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.bean.TradeType;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.mybatis.domain.TransferRecord;
import com.selfsell.investor.mybatis.mapper.TransferRecordMapper;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.service.TransferService;
import com.selfsell.investor.share.TransferBean;
import com.selfsell.investor.share.WBinout;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class TransferServiceImpl implements TransferService {
	@Autowired
	TransferRecordMapper transferRecordMapper;
	
	@Autowired
	InvestorService investorService;
	
	@Autowired
	TradeRecordService tradeRecordService;

	@Override
	public PageInfo<TransferRecord> pageList(TransferBean transferBean) {
		Example example = new Example(TransferRecord.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(transferBean.getStatus())) {
			param.andEqualTo("status", transferBean.getStatus());
		}

		example.orderBy("createTime").desc();

		PageHelper.startPage(transferBean.getPage() - 1, transferBean.getLimit(), true);
		List<TransferRecord> resultList = transferRecordMapper.selectByExample(example);
		PageInfo<TransferRecord> pageInfo = new PageInfo<TransferRecord>(resultList);

		return pageInfo;
	}

	@Override
	public void transferAudit(TransferBean transferBean) {
		CheckParamUtil.checkBoolean(transferBean.getId() == null, "主键ID为空");
		CheckParamUtil.checkEmpty(transferBean.getStatus(), "审批结果不能为空");

		TransferRecord record = transferRecordMapper.selectByPrimaryKey(transferBean.getId());
		if (record == null) {
			throw new BusinessException("转账记录【{0}】找不到", transferBean.getId());
		}

		record.setStatus(TradeRecordStatus.valueOf(transferBean.getStatus()));
		record.setTxId(transferBean.getTxId());
		record.setRemark(transferBean.getRemark());

		transferRecordMapper.updateByPrimaryKey(record);
		
		tradeRecordService.updateStatus(record.getTradeRecordId(),record.getStatus());
		
		if(TradeRecordStatus.fail.equals(record.getStatus())) {//回滚扣除金额
			investorService.updateAssets(record.getInvestorId(), WBinout.IN, record.getAmount().add(record.getFee()), true);
		
			TradeRecord tradeRecord = new TradeRecord();
			tradeRecord.setAmount(record.getAmount().add(record.getFee()));
			tradeRecord.setCreateTime(new Date());
			tradeRecord.setInoutFlag(WBinout.IN);
			tradeRecord.setInvestorId(record.getInvestorId());
			tradeRecord.setStatus(TradeRecordStatus.success);
			tradeRecord.setsAddress(record.getAddress());
//			tradeRecord.settAddress(record.getAddress());
			tradeRecord.setType(TradeType.back);
			tradeRecordService.insert(tradeRecord);
		}
	}

	@Override
	public void insert(TransferRecord transferRecord) {
		transferRecordMapper.insert(transferRecord);
	}

}
