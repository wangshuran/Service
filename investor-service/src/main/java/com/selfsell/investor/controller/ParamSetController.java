package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.ParamSet;
import com.selfsell.investor.service.ParamSetService;
import com.selfsell.investor.share.ParamSetBean;
import com.selfsell.investor.share.Urls;

@RestController
public class ParamSetController {

	@Autowired
	ParamSetService paramSetService;

	@RequestMapping(value = Urls.PARAMSET_LIST)
	ResultMap list(@RequestBody ParamSetBean paramSetBean) {
		PageInfo<ParamSet> paramSetPage = paramSetService.list(paramSetBean);

		return ResultMap.successResult().set("totalAmount", paramSetPage.getTotal()).set("resultList",
				paramSetPage.getList());
	}

	@RequestMapping(value = Urls.PARAMSET_ADD)
	ResultMap add(@RequestBody ParamSetBean paramSetBean) {
		paramSetService.add(paramSetBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.PARAMSET_UPDATE)
	ResultMap update(@RequestBody ParamSetBean paramSetBean) {
		paramSetService.update(paramSetBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.PARAMSET_DEL)
	ResultMap del(@RequestBody ParamSetBean paramSetBean) {
		paramSetService.del(paramSetBean);

		return ResultMap.successResult();
	}
	
}
