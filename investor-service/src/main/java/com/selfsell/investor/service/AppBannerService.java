package com.selfsell.investor.service;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.AppBanner;
import com.selfsell.investor.share.AppBannerListREQ;

/**
 * app 欢迎页服务
 * 
 * @author breeze
 *
 */
public interface AppBannerService {

	PageInfo<AppBanner> list(AppBannerListREQ appBannerListREQ);

}
