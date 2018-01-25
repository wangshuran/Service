package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.AnswerActivity;
import com.selfsell.investor.service.AnswerActivityService;
import com.selfsell.investor.share.AAQuestionBean;
import com.selfsell.investor.share.AAQuestionBean.AAOptionBean;
import com.selfsell.investor.share.AnswerActivityBean;
import com.selfsell.investor.share.Urls;

@RestController
public class AnswerActivityController {

	@Autowired
	AnswerActivityService answerActivityService;

	@RequestMapping(value = Urls.ANSWER_ACTIVITY_LIST, method = RequestMethod.POST)
	public ResultMap activityList(@RequestBody AnswerActivityBean answerActivityBean) {
		PageInfo<AnswerActivity> answerActivityPage = answerActivityService.activityList(answerActivityBean);
		return ResultMap.successResult().set("totalAmount", answerActivityPage.getTotal()).set("resultList",
				answerActivityPage.getList());
	}

	@RequestMapping(value = Urls.ANSWER_ACTIVITY_ADD, method = RequestMethod.POST)
	ResultMap add(@RequestBody AnswerActivityBean answerActivityBean) {
		answerActivityService.add(answerActivityBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.ANSWER_ACTIVITY_UPDATE, method = RequestMethod.POST)
	ResultMap update(@RequestBody AnswerActivityBean answerActivityBean) {
		answerActivityService.update(answerActivityBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.ANSWER_ACTIVITY_DEL, method = RequestMethod.POST)
	ResultMap del(@RequestBody AnswerActivityBean answerActivityBean) {
		answerActivityService.del(answerActivityBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.ANSWER_ACTIVITY_UPDATE_STATUS, method = RequestMethod.POST)
	ResultMap updateStatus(@RequestBody AnswerActivityBean answerActivityBean) {
		answerActivityService.updateStatus(answerActivityBean);

		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.AA_QUESTION_LIST, method = RequestMethod.POST)
	ResultMap aaQuestionList(@RequestBody AAQuestionBean aaQuestionBean) {

		return ResultMap.successResult().set("resultList", answerActivityService.aaQuestionList(aaQuestionBean));
	}

	@RequestMapping(value = Urls.AA_QUESTION_ADD, method = RequestMethod.POST)
	ResultMap aaQuestionAdd(@RequestBody AAQuestionBean aaQuestionBean) {

		answerActivityService.aaQuestionAdd(aaQuestionBean);
		
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.AA_QUESTION_UPDATE, method = RequestMethod.POST)
	ResultMap aaQuestionUpdate(@RequestBody AAQuestionBean aaQuestionBean) {

		answerActivityService.aaQuestionUpdate(aaQuestionBean);
		
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.AA_QUESTION_DEL, method = RequestMethod.POST)
	ResultMap aaQuestionDel(@RequestBody AAQuestionBean aaQuestionBean) {

		answerActivityService.aaQuestionDel(aaQuestionBean);
		
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.AA_OPTION_LIST, method = RequestMethod.POST)
	ResultMap aaOptionList(@RequestBody AAOptionBean aaOptionBean) {

		return ResultMap.successResult().set("resultList", answerActivityService.aaOptionList(aaOptionBean));
	}
}
