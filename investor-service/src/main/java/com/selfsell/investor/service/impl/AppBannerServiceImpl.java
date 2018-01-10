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
import com.google.common.collect.Lists;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.mybatis.domain.AppBanner;
import com.selfsell.investor.mybatis.mapper.AppBannerMapper;
import com.selfsell.investor.service.AppBannerService;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.AppBannerREQ;
import com.selfsell.investor.share.AppBannerRES;
import com.selfsell.investor.share.WBbannerStatus;
import com.selfsell.investor.share.WBlang;

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

		example.orderBy("status").desc().orderBy("weight").asc();
		PageHelper.startPage(appBannerListREQ.getPage() - 1, appBannerListREQ.getLimit(), true);
		List<AppBanner> resultList = appBannerMapper.selectByExample(example);
		PageInfo<AppBanner> pageInfo = new PageInfo<AppBanner>(resultList);

		return pageInfo;
	}

	@Override
	@CacheEvict(value="appBanner",key="'APP::BANNER'")
	public void add(AppBannerBean appBannerBean) {
		AppBanner appBanner = new AppBanner();
		BeanUtils.copyProperties(appBannerBean, appBanner, "id");
		appBanner.setStatus(WBbannerStatus.valueOf(appBannerBean.getStatus()));
		appBanner.setLang(WBlang.valueOf(appBannerBean.getLang()));
		appBannerMapper.insert(appBanner);
	}

	@Override
	@CacheEvict(value="appBanner",key="'APP::BANNER'")
	public void del(Long id) {

		appBannerMapper.deleteByPrimaryKey(id);

	}

	@Override
	@CacheEvict(value="appBanner",key="'APP::BANNER'")
	public void update(AppBannerBean appBannerBean) {
		AppBanner appBanner = new AppBanner();
		BeanUtils.copyProperties(appBannerBean, appBanner);

		appBanner.setStatus(WBbannerStatus.valueOf(appBannerBean.getStatus()));
		appBanner.setLang(WBlang.valueOf(appBannerBean.getLang()));
		appBannerMapper.updateByPrimaryKey(appBanner);
	}

	@Override
	@CacheEvict(value="appBanner",key="'APP::BANNER'")
	public void updateStatus(AppBannerBean appBannerBean) {
		CheckParamUtil.checkBoolean(appBannerBean.getId() == null, "id为空");
		CheckParamUtil.checkEmpty(appBannerBean.getStatus(), "状态为空");

		appBannerMapper.updateStatus(appBannerBean.getId(), appBannerBean.getStatus());
	}

	@Cacheable(value="appBanner",key="'APP::BANNER'")
	@Override
	public List<AppBannerRES> appBanner(AppBannerREQ appBannerREQ) {
		Example param = new Example(AppBanner.class);
		param.createCriteria().andEqualTo("status", WBbannerStatus.ENABLED.name());
		param.orderBy("weight").asc();

		List<AppBanner> resultList = appBannerMapper.selectByExample(param);

		if (resultList != null && !resultList.isEmpty()) {
			
			List<AppBannerRES> result = Lists.newArrayList();
			for(AppBanner appBanner:resultList) {
				AppBannerRES output = new AppBannerRES();
				output.setImgUrl(appBanner.getImgUrl());
				output.setTitle(appBanner.getTitle());
				output.setSubTitle(appBanner.getSubTitle());
				result.add(output);
			}
			
			return result;
		}
		return null;
	}
}
