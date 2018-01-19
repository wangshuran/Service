package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.TransferRecord;
import com.selfsell.investor.service.TransferService;
import com.selfsell.investor.share.TransferBean;
import com.selfsell.investor.share.Urls;

@RestController
public class TransferController {

	@Autowired
	TransferService transferService;

	@RequestMapping(value = Urls.TRANSFER_LIST, method = RequestMethod.POST)
	public ResultMap transferList(@RequestBody TransferBean transferBean) {
		PageInfo<TransferRecord> transferPage = transferService.pageList(transferBean);

		return ResultMap.successResult().set("totalCount", transferPage.getTotal()).set("resultList",
				transferPage.getList());
	}
	
	@RequestMapping(value = Urls.TRANSFER_AUDIT, method = RequestMethod.POST)
	public ResultMap transferAudit(@RequestBody TransferBean transferBean) {
		transferService.transferAudit(transferBean);

		return ResultMap.successResult();
	}
}
