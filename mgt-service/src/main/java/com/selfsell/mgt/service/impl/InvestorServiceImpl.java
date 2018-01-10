package com.selfsell.mgt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.breeze.bms.mgt.bean.ResultMap;
import com.selfsell.investor.share.AppBannerBean;
import com.selfsell.investor.share.AppBannerListREQ;
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
}
