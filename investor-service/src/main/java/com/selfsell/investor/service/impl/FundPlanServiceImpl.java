package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.FundPlan;
import com.selfsell.investor.mybatis.domain.FundPlanLang;
import com.selfsell.investor.mybatis.mapper.FundPlanLangMapper;
import com.selfsell.investor.mybatis.mapper.FundPlanMapper;
import com.selfsell.investor.service.FundPlanService;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.WBdateUnit;
import com.selfsell.investor.share.WBlang;
import com.selfsell.investor.share.WBrecordStatus;

import tk.mybatis.mapper.entity.Example;

@Component
public class FundPlanServiceImpl implements FundPlanService {
	@Autowired
	FundPlanMapper fundPlanMapper;
	@Autowired
	FundPlanLangMapper fundPlanLangMapper;

	@Override
	public PageInfo<FundPlan> list(FundPlanBean fundPlanBean) {

		Example example = new Example(FundPlan.class);
		example.orderBy("weight").asc();

		PageHelper.startPage(fundPlanBean.getPage() - 1, fundPlanBean.getLimit(), true);
		List<FundPlan> resultList = fundPlanMapper.selectByExample(example);
		PageInfo<FundPlan> pageInfo = new PageInfo<FundPlan>(resultList);

		return pageInfo;

	}

	@Override
	public void fillLang(List<FundPlan> resultList) {
		if (resultList != null && !resultList.isEmpty()) {
			for (FundPlan fundPlan : resultList) {
				fundPlan.setFundPlanLangs(queryLangs(fundPlan.getId()));
			}
		}

	}

	public List<FundPlanLang> queryLangs(Long fundPlanId) {
		Example example = new Example(FundPlanLang.class);
		example.createCriteria().andEqualTo("fundPlanId", fundPlanId);

		return fundPlanLangMapper.selectByExample(example);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void add(FundPlanBean fundPlanBean) {
		FundPlan fundPlan = new FundPlan();
		BeanUtils.copyProperties(fundPlanBean, fundPlan);
		fundPlan.setStatus(WBrecordStatus.valueOf(fundPlanBean.getStatus()));
		fundPlan.setTermUnit(WBdateUnit.valueOf(fundPlanBean.getTermUnit()));

		fundPlanMapper.insert(fundPlan);

		if (fundPlanBean.getFundPlanLangs() != null && !fundPlanBean.getFundPlanLangs().isEmpty()) {
			for (FundPlanLangBean lang : fundPlanBean.getFundPlanLangs()) {
				FundPlanLang fundPlanLang = new FundPlanLang();
				BeanUtils.copyProperties(lang, fundPlanLang);
				fundPlanLang.setLang(WBlang.valueOf(lang.getLang()));
				fundPlanLang.setId(fundPlan.getId());
				fundPlanLangMapper.insert(fundPlanLang);
			}
		}

	}

	@Override
	public void update(FundPlanBean fundPlanBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateStatus(FundPlanBean fundPlanBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void del(Long id) {
		// TODO Auto-generated method stub

	}
}
