package com.selfsell.investor.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.common.util.G;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.mybatis.domain.AAOption;
import com.selfsell.investor.mybatis.domain.AAQuestion;
import com.selfsell.investor.mybatis.domain.AnswerActivity;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.InvestorExt;
import com.selfsell.investor.mybatis.mapper.AAOptionMapper;
import com.selfsell.investor.mybatis.mapper.AAQuestionMapper;
import com.selfsell.investor.mybatis.mapper.AnswerActivityMapper;
import com.selfsell.investor.mybatis.mapper.InvestorExtMapper;
import com.selfsell.investor.mybatis.mapper.InvestorMapper;
import com.selfsell.investor.service.AnswerActivityService;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.service.ParamSetService;
import com.selfsell.investor.share.AAQuestionBean;
import com.selfsell.investor.share.AAQuestionBean.AAOptionBean;
import com.selfsell.investor.share.AnswerActivityBean;
import com.selfsell.investor.share.JoinAnswerActivityREQ;
import com.selfsell.investor.share.JoinAnswerActivityRES;
import com.selfsell.investor.share.JoinAnswerActivityRES.ElementPersonInfo;
import com.selfsell.investor.share.JoinAnswerActivityRES.ElementRewardList;
import com.selfsell.investor.share.ParamKeys;
import com.selfsell.investor.share.WBaaStage;
import com.selfsell.investor.share.WBrecordStatus;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class AnswerActivityServiceImpl implements AnswerActivityService {

	@Autowired
	AnswerActivityMapper answerActivityMapper;

	@Autowired
	AAQuestionMapper aaQuestionMapper;

	@Autowired
	AAOptionMapper aaOptionMapper;

	@Autowired
	ParamSetService paramSetService;

	@Autowired
	RedissonClient redissonClient;

	@Autowired
	I18nService i18nService;

	@Autowired
	InvestorMapper investorMapper;

	@Autowired
	InvestorExtMapper investorExtMapper;

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
	@Transactional(rollbackFor = Throwable.class)
	public void add(AnswerActivityBean answerActivityBean) {
		AnswerActivity activity = new AnswerActivity();
		BeanUtils.copyProperties(answerActivityBean, activity, "id");
		activity.setStatus(WBrecordStatus.valueOf(answerActivityBean.getStatus()));
		activity.setStage(WBaaStage.SCHEDULE);

		if (activity.getStartTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(activity.getStartTime());
			calendar.add(Calendar.MINUTE, -G.i(paramSetService.findParamValue(ParamKeys.AA_PREHEATE_TIME), 5));
			activity.setPreHeatTime(calendar.getTime());
		}

		answerActivityMapper.insert(activity);

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void update(AnswerActivityBean answerActivityBean) {
		AnswerActivity activity = new AnswerActivity();
		BeanUtils.copyProperties(answerActivityBean, activity);
		activity.setStatus(WBrecordStatus.valueOf(answerActivityBean.getStatus()));
		activity.setStage(WBaaStage.SCHEDULE);

		if (activity.getStartTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(activity.getStartTime());
			calendar.add(Calendar.MINUTE, -G.i(paramSetService.findParamValue(ParamKeys.AA_PREHEATE_TIME), 5));
			activity.setPreHeatTime(calendar.getTime());
		}
		answerActivityMapper.updateByPrimaryKey(activity);

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void del(AnswerActivityBean answerActivityBean) {

		answerActivityMapper.deleteByPrimaryKey(answerActivityBean.getId());

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void updateStatus(AnswerActivityBean answerActivityBean) {
		answerActivityMapper.updateStatus(answerActivityBean.getId(), answerActivityBean.getStatus());
	}

	@Override
	public List<AAQuestion> aaQuestionList(AAQuestionBean aaQuestionBean) {
		Example example = new Example(AAQuestion.class);
		example.createCriteria().andEqualTo("aaId", aaQuestionBean.getAaId());

		example.orderBy("number").asc();
		return aaQuestionMapper.selectByExample(example);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void aaQuestionAdd(AAQuestionBean aaQuestionBean) {
		AAQuestion question = new AAQuestion();
		BeanUtils.copyProperties(aaQuestionBean, question);
		aaQuestionMapper.insert(question);

		if (aaQuestionBean.getOptions() != null && !aaQuestionBean.getOptions().isEmpty()) {
			for (AAOptionBean aaOptionBean : aaQuestionBean.getOptions()) {
				AAOption option = new AAOption();
				BeanUtils.copyProperties(aaOptionBean, option, "id");
				option.setQuestionId(question.getId());
				aaOptionMapper.insert(option);
			}
		}

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void aaQuestionUpdate(AAQuestionBean aaQuestionBean) {
		AAQuestion question = new AAQuestion();
		BeanUtils.copyProperties(aaQuestionBean, question);
		aaQuestionMapper.updateByPrimaryKey(question);

		aaOptionMapper.delByQuestionId(question.getId());

		if (aaQuestionBean.getOptions() != null && !aaQuestionBean.getOptions().isEmpty()) {
			for (AAOptionBean aaOptionBean : aaQuestionBean.getOptions()) {
				AAOption option = new AAOption();
				BeanUtils.copyProperties(aaOptionBean, option, "id");
				option.setQuestionId(question.getId());
				aaOptionMapper.insert(option);
			}
		}

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void aaQuestionDel(AAQuestionBean aaQuestionBean) {

		aaOptionMapper.delByQuestionId(aaQuestionBean.getId());

		aaQuestionMapper.deleteByPrimaryKey(aaQuestionBean.getId());
	}

	@Override
	public List<AAOption> aaOptionList(AAOptionBean aaOptionBean) {
		Example example = new Example(AAOption.class);
		example.createCriteria().andEqualTo("questionId", aaOptionBean.getQuestionId());

		example.orderBy("optionCode").asc();
		return aaOptionMapper.selectByExample(example);
	}

	@Override
	public List<AnswerActivity> queryByPreHeatTime(Date date) {
		Example example = new Example(AnswerActivity.class);
		example.createCriteria().andEqualTo("preHeatTime", date).andEqualTo("stage", WBaaStage.TOSTART);

		return answerActivityMapper.selectByExample(example);
	}

	@Override
	public void updateStage(Long id, String stage) {
		answerActivityMapper.updateStage(id, stage);

	}

	@Override
	public List<AnswerActivity> queryByStartTime(Date now) {
		Example example = new Example(AnswerActivity.class);
		example.createCriteria().andEqualTo("startTime", now).andEqualTo("stage", WBaaStage.PREHEATING);

		return answerActivityMapper.selectByExample(example);
	}

	@Override
	public JoinAnswerActivityRES joinAnswerActivity(JoinAnswerActivityREQ joinAnswerActivityREQ) {
		CheckParamUtil.checkBoolean(joinAnswerActivityREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));

		Investor investor = investorMapper.selectByPrimaryKey(joinAnswerActivityREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, joinAnswerActivityREQ.getId()));
		}

		JoinAnswerActivityRES result = new JoinAnswerActivityRES();
		result.setNow(new Date());
		// 获取当前活动
		AnswerActivity activity = queryCurrentActivity();
		if (activity != null) {
			result.setStartTime(activity.getStartTime());
			result.setStage(activity.getStage().name());
			result.setReward(activity.getReward());
			RAtomicLong joinNum = redissonClient
					.getAtomicLong(Joiner.on("::").join("ANSWER", "ACTIVITY", "JOINNUM", activity.getId()));
			RList<Long> joinIds = redissonClient
					.getList(Joiner.on("::").join("ANSWER", "ACTIVITY", "JOINIDS", activity.getId()));
			if (!joinIds.contains(joinAnswerActivityREQ.getId())) {
				joinIds.add(joinAnswerActivityREQ.getId());
				result.setJoinNum(joinNum.decrementAndGet());
			} else {
				result.setJoinNum(joinNum.get());
			}
		}
		// 获取rankList
		List<InvestorExt> rankList = queryAnswerActivityRank();
		if (rankList != null && !rankList.isEmpty()) {
			List<ElementRewardList> rewardList = Lists.newArrayList();
			for (InvestorExt ext : rankList) {
				ElementRewardList elementReward = new ElementRewardList();
				elementReward.setEmail(ext.getEmail());
				elementReward.setReward(ext.getAnswerReward());
				rewardList.add(elementReward);
			}
			result.setRewardList(rewardList);
		}
		// 获取person info
		Map<String, Object> personInfo = investorExtMapper.queryRankInfo(joinAnswerActivityREQ.getId());
		if (personInfo != null) {
			ElementPersonInfo elementPersonInfo = new ElementPersonInfo();
			elementPersonInfo.setRank(G.l(personInfo.get("rownum")));
			elementPersonInfo.setReward(G.bd(personInfo.get("answer_reward")));
			elementPersonInfo.setRcNum(G.i(personInfo.get("resurrection_card")));
			result.setPersonInfo(elementPersonInfo);
		}
		return result;
	}

	public List<InvestorExt> queryAnswerActivityRank() {
		Example example = new Example(InvestorExt.class);
		example.orderBy("answerReward").desc();

		return investorExtMapper.selectByExampleAndRowBounds(example, new RowBounds(0, 3));
	}

	public AnswerActivity queryCurrentActivity() {
		Example example = new Example(AnswerActivity.class);
		example.createCriteria().andEqualTo("status", WBrecordStatus.ENABLED.name())
				.andNotEqualTo("stage", WBaaStage.SCHEDULE.name()).andNotEqualTo("stage", WBaaStage.FINISHED.name());

		List<AnswerActivity> activitys = answerActivityMapper.selectByExample(example);
		if (activitys != null && !activitys.isEmpty()) {
			return activitys.get(0);
		}

		return null;
	}

}
