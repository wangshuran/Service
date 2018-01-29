package com.selfsell.investor.service;

import java.util.List;

import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.share.TradeInfoPageREQ;
import com.selfsell.investor.share.TradeInfoPageRES;
import com.selfsell.investor.share.TradeInfoREQ;
import com.selfsell.investor.share.TradeInfoRES;

/**
 * 交易记录服务
 * 
 * @author breeze
 *
 */
public interface TradeRecordService {

	void insert(TradeRecord tradeRecord);
	
	void updateTranserStatus(String txId,TradeRecordStatus status);
	
	List<TradeInfoRES> tradeInfo(TradeInfoREQ tradeInfoREQ);

	/**
	 * 更新交易记录状态 
	 * @param tradeRecordId
	 * @param status
	 */
	void updateStatus(Long tradeRecordId, TradeRecordStatus status);

	TradeInfoPageRES tradeInfoPage(TradeInfoPageREQ tradeInfoPageREQ);

}
