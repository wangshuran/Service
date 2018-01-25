package com.selfsell.investor.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.News;
import com.selfsell.investor.share.NewsBean;

/**
 * 新闻服务
 * 
 * @author breeze
 *
 */
public interface NewsService {

	PageInfo<News> list(NewsBean newsBean);

	void add(NewsBean newsBean);

	void update(NewsBean newsBean);

	void del(NewsBean newsBean);

	void updateStatus(NewsBean newsBean);

	List<News> news(NewsBean newsBean, String language);


}
