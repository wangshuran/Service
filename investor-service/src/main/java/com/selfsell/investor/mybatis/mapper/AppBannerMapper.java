package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.AppBanner;

import tk.mybatis.mapper.common.Mapper;

public interface AppBannerMapper extends Mapper<AppBanner> {

	@Update("update app_banner set status=#{status} where id=#{id}")
	void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);

}
