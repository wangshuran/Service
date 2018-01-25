package com.selfsell.investor.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.News;
import com.selfsell.investor.mybatis.mapper.NewsMapper;
import com.selfsell.investor.service.NewsService;
import com.selfsell.investor.share.NewsBean;
import com.selfsell.investor.share.WBlang;
import com.selfsell.investor.share.WBrecordStatus;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsMapper newsMapper;

	@Override
	public PageInfo<News> list(NewsBean newsBean) {
		Example example = new Example(News.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(newsBean.getTitle())) {
			param.andLike("title", "%" + newsBean.getTitle() + "%");
		}
		if (!StringUtils.isEmpty(newsBean.getStatus())) {
			param.andEqualTo("status", newsBean.getStatus());
		}
		if(!StringUtils.isEmpty(newsBean.getLang())) {
			param.andEqualTo("lang", newsBean.getLang());
		}

		example.orderBy("status").desc().orderBy("weight").asc();
		PageHelper.startPage(newsBean.getPage() - 1, newsBean.getLimit(), true);
		List<News> resultList = newsMapper.selectByExample(example);
		PageInfo<News> pageInfo = new PageInfo<News>(resultList);

		return pageInfo;
	}

	@Override
	public void add(NewsBean newsBean) {
		News news = new News();
		BeanUtils.copyProperties(newsBean, news, "id");
		news.setLang(WBlang.valueOf(newsBean.getLang()));
		news.setStatus(WBrecordStatus.valueOf(newsBean.getStatus()));

		newsMapper.insert(news);

	}

	@Override
	public void update(NewsBean newsBean) {
		News news = new News();
		BeanUtils.copyProperties(newsBean, news);
		news.setLang(WBlang.valueOf(newsBean.getLang()));
		news.setStatus(WBrecordStatus.valueOf(newsBean.getStatus()));

		newsMapper.updateByPrimaryKey(news);
	}

	@Override
	public void del(NewsBean newsBean) {
		newsMapper.deleteByPrimaryKey(newsBean.getId());

	}

	@Override
	public void updateStatus(NewsBean newsBean) {
		newsMapper.updateStatus(newsBean.getId(), newsBean.getStatus());

	}

	@Override
	public List<News> news(NewsBean newsBean, String language) {
		Example param = new Example(News.class);
		param.createCriteria().andEqualTo("status", WBrecordStatus.ENABLED.name()).andEqualTo("lang",language);
		param.orderBy("weight").asc();

		return newsMapper.selectByExample(param);
	}

}
