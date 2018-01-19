package com.selfsell.investor.service;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.TransferRecord;
import com.selfsell.investor.share.TransferBean;

/**
 * 转账提现服务
 * 
 * @author breeze
 *
 */
public interface TransferService {

	PageInfo<TransferRecord> pageList(TransferBean transferBean);

	void transferAudit(TransferBean transferBean);

	void insert(TransferRecord transferRecord);

}
