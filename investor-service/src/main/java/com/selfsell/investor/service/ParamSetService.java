package com.selfsell.investor.service;

import com.github.pagehelper.PageInfo;
import com.selfsell.investor.mybatis.domain.ParamSet;
import com.selfsell.investor.share.ParamSetBean;

/**
 * 参数设置服务
 * 
 * @author breeze
 *
 */
public interface ParamSetService {

	public String findParamValue(String paramKey);

	public PageInfo<ParamSet> list(ParamSetBean paramSetBean);

	public void add(ParamSetBean paramSetBean);

	public void update(ParamSetBean paramSetBean);

	public void del(ParamSetBean paramSetBean);
}
