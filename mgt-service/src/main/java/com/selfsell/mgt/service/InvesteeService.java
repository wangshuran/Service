package com.selfsell.mgt.service;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investee.share.InvesteeListREQ;

public interface InvesteeService {

	/**
	 * 查询募资人员列表
	 * 
	 * @param data
	 * @return
	 */
	ResultMap list(InvesteeListREQ data);

}
