package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.News;

import tk.mybatis.mapper.common.Mapper;

public interface NewsMapper extends Mapper<News> {

	@Update("update news set status=#{status} where id=#{id}")
	void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);

}
