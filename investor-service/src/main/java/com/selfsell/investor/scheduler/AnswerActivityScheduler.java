package com.selfsell.investor.scheduler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.selfsell.investor.mybatis.domain.AnswerActivity;
import com.selfsell.investor.service.AnswerActivityService;
import com.selfsell.investor.share.WBaaStage;

@Component
public class AnswerActivityScheduler {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	AnswerActivityService answerActivityService;

	@Scheduled(fixedDelay = 1 * 1000)
	public void execute() throws Exception {
		Date now = new Date();
		List<AnswerActivity> toPreHeatActivitys = answerActivityService.queryByPreHeatTime(now);
		if (toPreHeatActivitys != null && !toPreHeatActivitys.isEmpty()) {
			for (AnswerActivity activity : toPreHeatActivitys) {
				log.info("活动【{}】开始预热", activity.getTitle());

				answerActivityService.updateStage(activity.getId(), WBaaStage.PREHEATING.name());
			}
		}

		List<AnswerActivity> toAnswerActivitys = answerActivityService.queryByStartTime(now);
		if (toAnswerActivitys != null && !toAnswerActivitys.isEmpty()) {
			for (AnswerActivity activity : toAnswerActivitys) {
				log.info("活动【{}】开始答题", activity.getTitle());

				answerActivityService.updateStage(activity.getId(), WBaaStage.ANSWERING.name());
			}
		}
	}

}
