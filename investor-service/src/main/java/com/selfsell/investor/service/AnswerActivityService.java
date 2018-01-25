package com.selfsell.investor.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.AAOption;
import com.selfsell.investor.mybatis.domain.AAQuestion;
import com.selfsell.investor.mybatis.domain.AnswerActivity;
import com.selfsell.investor.share.AAQuestionBean;
import com.selfsell.investor.share.AAQuestionBean.AAOptionBean;
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

	List<AAQuestion> aaQuestionList(AAQuestionBean aaQuestionBean);

	void aaQuestionAdd(AAQuestionBean aaQuestionBean);

	void aaQuestionUpdate(AAQuestionBean aaQuestionBean);

	void aaQuestionDel(AAQuestionBean aaQuestionBean);

	List<AAOption> aaOptionList(AAOptionBean aaOptionBean);

}
