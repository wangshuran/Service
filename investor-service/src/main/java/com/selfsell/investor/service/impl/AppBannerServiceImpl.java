package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.AppBanner;
import com.selfsell.investor.mybatis.mapper.AppBannerMapper;
import com.selfsell.investor.service.AppBannerService;
import com.selfsell.investor.share.AppBannerListREQ;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class AppBannerServiceImpl implements AppBannerService {
	@Autowired
	AppBannerMapper appBannerMapper;

	@Override
	public PageInfo<AppBanner> list(AppBannerListREQ appBannerListREQ) {
		Example example = new Example(AppBanner.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(appBannerListREQ.getTitle())) {
			param.andLike("title", "%" + appBannerListREQ.getTitle() + "%");
		}
		if (!StringUtils.isEmpty(appBannerListREQ.getStatus())) {
			param.andEqualTo("status", appBannerListREQ.getStatus());
		}

		example.orderBy("status").asc().orderBy("weight").asc();
		PageHelper.startPage(appBannerListREQ.getPage() - 1, appBannerListREQ.getLimit(), true);
		List<AppBanner> resultList = appBannerMapper.selectByExample(example);
		PageInfo<AppBanner> pageInfo = new PageInfo<AppBanner>(resultList);

		return pageInfo;
	}
}
