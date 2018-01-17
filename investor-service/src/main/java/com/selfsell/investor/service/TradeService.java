package com.selfsell.investor.service;

import java.util.List;

import com.selfsell.investor.share.TradeInfoREQ;
import com.selfsell.investor.share.TradeInfoRES;

/**
 * 交易服务
 * 
 * @author breeze
 *
 */
public interface TradeService {

	List<TradeInfoRES> tradeInfo(TradeInfoREQ tradeInfoREQ);

}
