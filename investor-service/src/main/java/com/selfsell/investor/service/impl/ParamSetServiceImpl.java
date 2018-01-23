package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.ParamSet;
import com.selfsell.investor.mybatis.mapper.ParamSetMapper;
import com.selfsell.investor.service.ParamSetService;
import com.selfsell.investor.share.ParamSetBean;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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

	@Override
	public PageInfo<ParamSet> list(ParamSetBean paramSetBean) {
		Example example = new Example(ParamSet.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(paramSetBean.getParamKey())) {
			param.andEqualTo("paramKey", paramSetBean.getParamKey());
		}

		example.orderBy("paramKey").asc();
		PageHelper.startPage(paramSetBean.getPage() - 1, paramSetBean.getLimit(), true);
		List<ParamSet> resultList = paramSetMapper.selectByExample(example);
		PageInfo<ParamSet> pageInfo = new PageInfo<ParamSet>(resultList);

		return pageInfo;
	}

	@Override
	@CacheEvict(value = "paramSetCache", key = "'PARAM::'+#paramSetBean.paramKey")
	public void add(ParamSetBean paramSetBean) {

		ParamSet paramSet = new ParamSet();
		BeanUtils.copyProperties(paramSetBean, paramSet);

		paramSetMapper.insert(paramSet);

	}

	@Override
	@CacheEvict(value = "paramSetCache", key = "'PARAM::'+#paramSetBean.paramKey")
	public void update(ParamSetBean paramSetBean) {
		ParamSet paramSet = new ParamSet();
		BeanUtils.copyProperties(paramSetBean, paramSet);

		paramSetMapper.updateByPrimaryKey(paramSet);

	}

	@Override
	@CacheEvict(value = "paramSetCache", key = "'PARAM::'+#paramSetBean.paramKey")
	public void del(ParamSetBean paramSetBean) {
		paramSetMapper.deleteByPrimaryKey(paramSetBean.getParamKey());

	}
}
