package com.selfsell.investor.service;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.AnswerActivity;
import com.selfsell.investor.share.AnswerActivityBean;

/**
 * 答题活动服务
 * 
 * @author breeze
 *
 */
public interface AnswerActivityService {

	PageInfo<AnswerActivity> activityList(AnswerActivityBean answerActivityBean);

	void add(AnswerActivityBean answerActivityBean);

	void update(AnswerActivityBean answerActivityBean);

	void del(AnswerActivityBean answerActivityBean);

	void updateStatus(AnswerActivityBean answerActivityBean);

}
