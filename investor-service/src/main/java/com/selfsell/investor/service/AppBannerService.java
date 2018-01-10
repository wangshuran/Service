package com.selfsell.investor.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.AppBanner;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
import com.selfsell.investor.share.AppBannerREQ;
import com.selfsell.investor.share.AppBannerRES;

/**
 * app 欢迎页服务
 * 
 * @author breeze
 *
 */
public interface AppBannerService {

	PageInfo<AppBanner> list(AppBannerListREQ appBannerListREQ);

	void add(AppBannerBean appBannerBean);

	void del(Long id);

	void update(AppBannerBean appBannerBean);

	void updateStatus(AppBannerBean appBannerBean);

	List<AppBannerRES> appBanner(AppBannerREQ appBannerREQ);

}
