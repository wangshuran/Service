package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.TradeRecord;

import tk.mybatis.mapper.common.Mapper;

public interface TradeRecordMapper extends Mapper<TradeRecord> {

	@Update("update trade_record set status=#{status} where tx_id=#{txId}")
	void updateTranserStatus(@Param(value = "txId") String txId, @Param(value = "status") String status);

}
