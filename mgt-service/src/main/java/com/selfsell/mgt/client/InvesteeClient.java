package com.selfsell.mgt.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investee.share.InvesteeListREQ;
import com.selfsell.investee.share.Urls;

@FeignClient(name = "investee-service")
public interface InvesteeClient {
	@RequestMapping(value = Urls.INVESTEE_LIST)
	public ResultMap investeeList(InvesteeListREQ investeeListReq);
}
