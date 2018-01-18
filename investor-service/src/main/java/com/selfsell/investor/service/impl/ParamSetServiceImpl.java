package com.selfsell.investor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.selfsell.investor.mybatis.domain.ParamSet;
import com.selfsell.investor.mybatis.mapper.ParamSetMapper;
import com.selfsell.investor.service.ParamSetService;

@Component
public class ParamSetServiceImpl implements ParamSetService {
	@Autowired
	ParamSetMapper paramSetMapper;

	@Cacheable(value = "paramSetCache", key = "'PARAM::'+#paramKey")
	@Override
	public String findParamValue(String paramKey) {
		ParamSet param = paramSetMapper.selectByPrimaryKey(paramKey);
		if (param != null) {
			return param.getParamValue();
		}
		return null;
	}
}
