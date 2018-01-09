package com.selfsell.investee.service;

import com.selfsell.investee.mybatis.domain.Investee;
import com.selfsell.investee.share.InvesteeListREQ;
import com.selfsell.investee.share.InvesteeListRES;

/**
 * 募资人服务
 * 
 * @author breeze
 *
 */
public interface InvesteeService {
	/**
	 * 插入数据
	 * 
	 * @param investee
	 */
	public void insert(Investee investee);

	/**
	 * 通过ID获取数据
	 * 
	 * @param id
	 * @return
	 */
	public Investee findById(Long id);

	/**
	 * 获取募次人列表
	 * 
	 * @param investeeListReq
	 * @return
	 */
	public InvesteeListRES list(InvesteeListREQ investeeListReq);
}
