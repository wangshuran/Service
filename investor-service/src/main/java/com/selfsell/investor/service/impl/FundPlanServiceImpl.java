package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.mybatis.domain.FundPlan;
import com.selfsell.investor.mybatis.domain.FundPlanLang;
import com.selfsell.investor.mybatis.mapper.FundPlanLangMapper;
import com.selfsell.investor.mybatis.mapper.FundPlanMapper;
import com.selfsell.investor.service.FundPlanService;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.FundPlanREQ;
import com.selfsell.investor.share.FundPlanRES;
import com.selfsell.investor.share.WBdateUnit;
import com.selfsell.investor.share.WBlang;
import com.selfsell.investor.share.WBrecordStatus;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class FundPlanServiceImpl implements FundPlanService {
	@Autowired
	FundPlanMapper fundPlanMapper;
	@Autowired
	FundPlanLangMapper fundPlanLangMapper;

	@Override
	public PageInfo<FundPlan> list(FundPlanBean fundPlanBean) {

		Example example = new Example(FundPlan.class);

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

	public FundPlanLang queryLang(Long fundPlanId, String lang) {
		Example example = new Example(FundPlanLang.class);
		example.createCriteria().andEqualTo("fundPlanId", fundPlanId).andEqualTo("lang", lang);

		List<FundPlanLang> langs = fundPlanLangMapper.selectByExample(example);
		if (langs != null && !langs.isEmpty()) {
			return langs.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void add(FundPlanBean fundPlanBean) {
		FundPlan fundPlan = new FundPlan();
		BeanUtils.copyProperties(fundPlanBean, fundPlan, "id");
		fundPlan.setStatus(WBrecordStatus.valueOf(fundPlanBean.getStatus()));
		fundPlan.setTermUnit(WBdateUnit.valueOf(fundPlanBean.getTermUnit()));

		fundPlanMapper.insert(fundPlan);

		if (fundPlanBean.getFundPlanLangs() != null && !fundPlanBean.getFundPlanLangs().isEmpty()) {
			for (FundPlanLangBean lang : fundPlanBean.getFundPlanLangs()) {
				FundPlanLang fundPlanLang = new FundPlanLang();
				BeanUtils.copyProperties(lang, fundPlanLang);
				fundPlanLang.setLang(WBlang.valueOf(lang.getLang()));
				fundPlanLang.setFundPlanId(fundPlan.getId());
				fundPlanLangMapper.insert(fundPlanLang);
			}
		}

	}

	@Override
	public void update(FundPlanBean fundPlanBean) {

		FundPlan fundPlan = new FundPlan();
		BeanUtils.copyProperties(fundPlanBean, fundPlan);
		fundPlan.setStatus(WBrecordStatus.valueOf(fundPlanBean.getStatus()));
		fundPlan.setTermUnit(WBdateUnit.valueOf(fundPlanBean.getTermUnit()));

		fundPlanMapper.updateByPrimaryKey(fundPlan);

		fundPlanLangMapper.delByFundPlanId(fundPlan.getId());

		if (fundPlanBean.getFundPlanLangs() != null && !fundPlanBean.getFundPlanLangs().isEmpty()) {
			for (FundPlanLangBean lang : fundPlanBean.getFundPlanLangs()) {
				FundPlanLang fundPlanLang = new FundPlanLang();
				BeanUtils.copyProperties(lang, fundPlanLang);
				fundPlanLang.setLang(WBlang.valueOf(lang.getLang()));
				fundPlanLang.setFundPlanId(fundPlan.getId());
				fundPlanLangMapper.insert(fundPlanLang);
			}
		}

	}

	@Override
	public void updateStatus(FundPlanBean fundPlanBean) {
		CheckParamUtil.checkBoolean(fundPlanBean.getId() == null, "id为空");
		CheckParamUtil.checkEmpty(fundPlanBean.getStatus(), "状态为空");

		fundPlanMapper.updateStatus(fundPlanBean.getId(), fundPlanBean.getStatus());

	}

	@Override
	public void del(Long id) {
		fundPlanLangMapper.delByFundPlanId(id);
		fundPlanMapper.deleteByPrimaryKey(id);

	}

	@Override
	public List<FundPlanLang> langList(FundPlanLangBean fundPlanLangBean) {
		Example example = new Example(FundPlanLang.class);
		Criteria param = example.createCriteria();
		if (fundPlanLangBean.getFundPlanId() != null) {
			param.andEqualTo("fundPlanId", fundPlanLangBean.getFundPlanId());
		}

		return fundPlanLangMapper.selectByExample(example);
	}

	@Override
	public FundPlan queryByIdAndLang(Long fundPlanId, String language) {
		FundPlan fundPlan = fundPlanMapper.selectByPrimaryKey(fundPlanId);
		FundPlanLang fundPlanLang = queryLang(fundPlanId, language);
		if (fundPlanLang != null) {
			fundPlan.setTitle(fundPlanLang.getTitle());
			fundPlan.setRemark(fundPlanLang.getRemark());
		}
		return fundPlan;
	}

	@Override
	public List<FundPlanRES> fundPlan(FundPlanREQ fundPlanREQ) {
		List<FundPlanRES> resultList = Lists.newArrayList();
		Example example = new Example(FundPlan.class);
		example.createCriteria().andEqualTo("status", WBrecordStatus.ENABLED.name());

		List<FundPlan> fundPlanList = fundPlanMapper.selectByExample(example);
		if (fundPlanList != null && !fundPlanList.isEmpty()) {
			for (FundPlan fundPlan : fundPlanList) {
				FundPlanLang lang = queryLang(fundPlan.getId(), LocaleContextHolder.getLocale().getLanguage());
				if (lang != null) {
					fundPlan.setTitle(lang.getTitle());
					fundPlan.setRemark(lang.getRemark());
				}

				FundPlanRES fundPlanRes = new FundPlanRES();
				BeanUtils.copyProperties(fundPlan, fundPlanRes);
				fundPlanRes.setTermUnit(fundPlan.getTermUnit().name());
				resultList.add(fundPlanRes);
			}
		}

		return resultList;
	}

	@Override
	public FundPlan queryById(Long fundPlanId) {
		return fundPlanMapper.selectByPrimaryKey(fundPlanId);
	}
}
