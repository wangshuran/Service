package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.AnswerActivity;

import tk.mybatis.mapper.common.Mapper;

public interface AnswerActivityMapper extends Mapper<AnswerActivity> {

	@Update("update answer_activity set status=#{status} where id=#{id}")
	void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);

	@Update("update answer_activity set stage=#{stage} where id=#{id}")
	void updateStage(@Param(value = "id") Long id, @Param(value = "stage") String stage);

}
