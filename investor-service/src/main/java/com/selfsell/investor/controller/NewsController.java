package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.News;
import com.selfsell.investor.service.NewsService;
import com.selfsell.investor.share.NewsBean;
import com.selfsell.investor.share.Urls;

@RestController
public class NewsController {

	@Autowired
	NewsService newsService;

	@RequestMapping(value = Urls.NEWS_LIST)
	ResultMap list(@RequestBody NewsBean newsBean) {
		PageInfo<News> appBannerPage = newsService.list(newsBean);

		return ResultMap.successResult().set("totalAmount", appBannerPage.getTotal()).set("resultList",
				appBannerPage.getList());
	}

	@RequestMapping(value = Urls.NEWS_ADD)
	ResultMap add(@RequestBody NewsBean newsBean) {
		newsService.add(newsBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.NEWS_UPDATE)
	ResultMap update(@RequestBody NewsBean newsBean) {
		newsService.update(newsBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.NEWS_DEL)
	ResultMap del(@RequestBody NewsBean newsBean) {
		newsService.del(newsBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.NEWS_UPDATE_STATUS)
	ResultMap updateStatus(@RequestBody NewsBean newsBean) {
		newsService.updateStatus(newsBean);

		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.NEWS)
	ResultMap news(@RequestBody NewsBean newsBean) {
		return ResultMap.successResult(newsService.news(newsBean,LocaleContextHolder.getLocale().getLanguage()));
	}
}
