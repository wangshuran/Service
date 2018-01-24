package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.AnswerActivity;
import com.selfsell.investor.mybatis.mapper.AnswerActivityMapper;
import com.selfsell.investor.service.AnswerActivityService;
import com.selfsell.investor.share.AnswerActivityBean;
import com.selfsell.investor.share.WBrecordStatus;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class AnswerActivityServiceImpl implements AnswerActivityService {

	@Autowired
	AnswerActivityMapper answerActivityMapper;

	@Override
	public PageInfo<AnswerActivity> activityList(AnswerActivityBean answerActivityBean) {
		Example example = new Example(AnswerActivity.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(answerActivityBean.getTitle())) {
			param.andLike("title", "%" + answerActivityBean.getTitle() + "%");
		}

		example.orderBy("startTime").desc();
		PageHelper.startPage(answerActivityBean.getPage() - 1, answerActivityBean.getLimit(), true);
		List<AnswerActivity> resultList = answerActivityMapper.selectByExample(example);
		PageInfo<AnswerActivity> pageInfo = new PageInfo<AnswerActivity>(resultList);

		return pageInfo;
	}

	@Override
	public void add(AnswerActivityBean answerActivityBean) {
		AnswerActivity activity = new AnswerActivity();
		BeanUtils.copyProperties(answerActivityBean, activity,"id");
		activity.setStatus(WBrecordStatus.valueOf(answerActivityBean.getStatus()));

		answerActivityMapper.insert(activity);

	}

	@Override
	public void update(AnswerActivityBean answerActivityBean) {
		AnswerActivity activity = new AnswerActivity();
		BeanUtils.copyProperties(answerActivityBean, activity);
		activity.setStatus(WBrecordStatus.valueOf(answerActivityBean.getStatus()));
		
		answerActivityMapper.updateByPrimaryKey(activity);
		
	}

	@Override
	public void del(AnswerActivityBean answerActivityBean) {
		
		answerActivityMapper.deleteByPrimaryKey(answerActivityBean.getId());

	}

	@Override
	public void updateStatus(AnswerActivityBean answerActivityBean) {
		answerActivityMapper.updateStatus(answerActivityBean.getId(),answerActivityBean.getStatus());
	}

}
