package com.selfsell.mgt.service.impl;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AAQuestionBean;
import com.selfsell.investor.share.AAQuestionBean.AAOptionBean;
import com.selfsell.investor.share.AnswerActivityBean;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.FundPlanBean;
import com.selfsell.investor.share.FundPlanBean.FundPlanLangBean;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.NewsBean;
import com.selfsell.investor.share.ParamSetBean;
import com.selfsell.investor.share.TransferBean;
import com.selfsell.mgt.client.InvestorClient;
import com.selfsell.mgt.service.FileSystemService;
import com.selfsell.mgt.service.InvestorService;

@Component
public class InvestorServiceImpl implements InvestorService {
	@Autowired
	@Qualifier("investorClientHystrix")
	InvestorClient investorClient;

	@Autowired
	FileSystemService fileSystemService;

	@Value("${web.domain}")
	String webDomain;

	@Override
	public ResultMap appBannerList(AppBannerListREQ appBannerListREQ) {
		return investorClient.appBannerList(appBannerListREQ);
	}

	@Override
	public ResultMap appBannerAdd(MultipartFile file, AppBannerBean appBannerBean) {
		String filepath = fileSystemService.save("/image/appbanner", file);
		String imgUrl = webDomain + "/file/show?path=" + filepath;

		appBannerBean.setImgUrl(imgUrl);

		return investorClient.appBannerAdd(appBannerBean);

	}

	@Override
	public ResultMap appBannerDel(Long id) {
		return investorClient.appBannerDel(id);
	}

	@Override
	public ResultMap updateStatus(AppBannerBean appBannerBean) {
		return investorClient.appBannerUpdateStatus(appBannerBean);
	}

	@Override
	public ResultMap appBannerUpdate(MultipartFile file, AppBannerBean appBannerBean) {
		String filepath = fileSystemService.save("/image/appbanner", file);
		String imgUrl = webDomain + "/file/show?path=" + filepath;

		if (!StringUtils.isEmpty(filepath))
			appBannerBean.setImgUrl(imgUrl);

		return investorClient.appBannerUpdate(appBannerBean);
	}

	@Override
	public ResultMap fundPlanList(FundPlanBean fundPlanBean) {
		return investorClient.fundPlanList(fundPlanBean);
	}

	@Override
	public ResultMap fundPlanLangList(FundPlanLangBean fundPlanLangBean) {
		return investorClient.fundPlanLangList(fundPlanLangBean);
	}

	@Override
	public ResultMap fundPlanAdd(FundPlanBean fundPlanBean,MultipartFile file) {
		String filepath = fileSystemService.save("/image/fundplan", file);
		String imgUrl = webDomain + "/file/show?path=" + filepath;

		if (!StringUtils.isEmpty(filepath))
			fundPlanBean.setIconUrl(imgUrl);
		return investorClient.fundPlanAdd(fundPlanBean);
	}

	@Override
	public ResultMap fundPlanUpdate(FundPlanBean fundPlanBean,MultipartFile file) {
		String filepath = fileSystemService.save("/image/fundplan", file);
		String imgUrl = webDomain + "/file/show?path=" + filepath;

		if (!StringUtils.isEmpty(filepath))
			fundPlanBean.setIconUrl(imgUrl);
		return investorClient.fundPlanUpdate(fundPlanBean);
	}

	@Override
	public ResultMap fundPlanDel(Long id) {
		return investorClient.fundPlanDel(id);
	}

	@Override
	public ResultMap fundPlanUpdateStatus(FundPlanBean fundPlanBean) {
		return investorClient.fundPlanUpdateStatus(fundPlanBean);
	}

	@Override
	public ResultMap transferList(TransferBean transferBean) {
		return investorClient.transferList(transferBean);
	}

	@Override
	public ResultMap transferAudit(TransferBean transferBean) {
		return investorClient.transferAudit(transferBean);
	}

	@Override
	public ResultMap list(InvestorListBean investorListBean) {
		return investorClient.list(investorListBean);
	}

	@Override
	public ResultMap updateStatus(InvestorBean investorBean) {
		return investorClient.updateStatus(investorBean);
	}

	@Override
	public ResultMap paramSetList(ParamSetBean paramSetBean) {
		return investorClient.paramSetList(paramSetBean);
	}

	@Override
	public ResultMap paramSetAdd(ParamSetBean paramSetBean) {
		return investorClient.paramSetAdd(paramSetBean);
	}

	@Override
	public ResultMap paramSetUpdate(ParamSetBean paramSetBean) {
		return investorClient.paramSetUpdate(paramSetBean);
	}

	@Override
	public ResultMap paramSetDel(ParamSetBean paramSetBean) {
		return investorClient.paramSetDel(paramSetBean);
	}

	@Override
	public ResultMap answerActivityList(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityList(answerActivityBean);
	}

	@Override
	public ResultMap answerActivityAdd(AnswerActivityBean answerActivityBean) {
		if(!StringUtils.isEmpty(answerActivityBean.getStartTime_show())) {
			try {
				answerActivityBean.setStartTime(DateUtils.parseDate(answerActivityBean.getStartTime_show(), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return investorClient.answerActivityAdd(answerActivityBean);
	}

	@Override
	public ResultMap answerActivityUpdate(AnswerActivityBean answerActivityBean) {
		if(!StringUtils.isEmpty(answerActivityBean.getStartTime_show())) {
			try {
				answerActivityBean.setStartTime(DateUtils.parseDate(answerActivityBean.getStartTime_show(), "yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return investorClient.answerActivityUpdate(answerActivityBean);
	}

	@Override
	public ResultMap answerActivityUpdateStatus(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityUpdateStatus(answerActivityBean);
	}

	@Override
	public ResultMap answerActivityDel(AnswerActivityBean answerActivityBean) {
		return investorClient.answerActivityDel(answerActivityBean);
	}

	@Override
	public ResultMap aaQuestionList(AAQuestionBean aaQuestionBean) {
		return investorClient.aaQuestionList(aaQuestionBean);
	}

	@Override
	public ResultMap aaQuestionAdd(AAQuestionBean aaQuestionBean) {
		return investorClient.aaQuestionAdd(aaQuestionBean);
	}

	@Override
	public ResultMap aaQuestionUpdate(AAQuestionBean aaQuestionBean) {
		return investorClient.aaQuestionUpdate(aaQuestionBean);
	}

	@Override
	public ResultMap aaQuestionDel(AAQuestionBean aaQuestionBean) {
		return investorClient.aaQuestionDel(aaQuestionBean);
	}

	@Override
	public ResultMap aaOptionList(AAOptionBean aaOptionBean) {
		return investorClient.aaOptionList(aaOptionBean);
	}

	@Override
	public ResultMap newsList(NewsBean newsBean) {
		return investorClient.newsList(newsBean);
	}

	@Override
	public ResultMap newsAdd(MultipartFile file, NewsBean newsBean) {
		String filepath = fileSystemService.save("/image/news", file);
		String imgUrl = webDomain + "/file/show?path=" + filepath;

		if (!StringUtils.isEmpty(filepath))
			newsBean.setImgUrl(imgUrl);
		
		return investorClient.newsAdd(newsBean);
	}

	@Override
	public ResultMap newsUpdate(MultipartFile file, NewsBean newsBean) {
		String filepath = fileSystemService.save("/image/news", file);
		String imgUrl = webDomain + "/file/show?path=" + filepath;

		if (!StringUtils.isEmpty(filepath))
			newsBean.setImgUrl(imgUrl);
		
		return investorClient.newsUpdate(newsBean);
	}

	@Override
	public ResultMap newsDel(NewsBean newsBean) {
		return investorClient.newsDel(newsBean);
	}

	@Override
	public ResultMap newsUpdateStatus(NewsBean newsBean) {
		return investorClient.newsUpdateStatus(newsBean);
	}
}
