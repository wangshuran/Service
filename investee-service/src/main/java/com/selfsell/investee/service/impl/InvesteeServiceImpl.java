package com.selfsell.investee.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.selfsell.investee.mybatis.domain.Investee;
import com.selfsell.investee.mybatis.mapper.InvesteeMapper;
import com.selfsell.investee.service.InvesteeService;
import com.selfsell.investee.share.InvesteeListREQ;
import com.selfsell.investee.share.InvesteeListRES;
import com.selfsell.investee.share.InvesteeListRES.ElementResultList;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class InvesteeServiceImpl implements InvesteeService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	InvesteeMapper investeeMapper;

	@Override
	public void insert(Investee investee) {
		investeeMapper.insert(investee);
	}

	@Override
	public Investee findById(Long id) {
		return investeeMapper.selectByPrimaryKey(id);
	}

	@Override
	public InvesteeListRES list(InvesteeListREQ investeeListReq) {
		
		Example example = new Example(Investee.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(investeeListReq.getEmail())) {
			param.andEqualTo("email", investeeListReq.getEmail());
		}
		if (!StringUtils.isEmpty(investeeListReq.getMobile())) {
			param.andEqualTo("mobile", investeeListReq.getMobile());
		}
		if (!StringUtils.isEmpty(investeeListReq.getIdentityId())) {
			param.andEqualTo("identityId", investeeListReq.getIdentityId());
		}
		example.orderBy("createTime").desc();
		PageHelper.startPage(investeeListReq.getPage() - 1, investeeListReq.getLimit(), true);
		List<Investee> resultList = investeeMapper.selectByExample(example);
		PageInfo<Investee> pageInfo = new PageInfo<Investee>(resultList);

		InvesteeListRES res = new InvesteeListRES();
		List<Investee> pageResultList = pageInfo.getList();
		if (pageResultList != null && !pageResultList.isEmpty()) {
			List<ElementResultList> result = Lists.transform(pageResultList,
					new Function<Investee, ElementResultList>() {

						@Override
						public ElementResultList apply(Investee input) {
							ElementResultList output = new ElementResultList();
							BeanUtils.copyProperties(input, output);
							return output;
						}

					});

			res.setResultList(result);
		}

		return res;
	}

}
