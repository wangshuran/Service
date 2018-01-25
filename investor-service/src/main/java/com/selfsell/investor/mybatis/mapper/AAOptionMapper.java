package com.selfsell.investor.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.selfsell.investor.mybatis.domain.AAOption;

import tk.mybatis.mapper.common.Mapper;

public interface AAOptionMapper extends Mapper<AAOption> {

	@Update("delete from aa_option where question_id=#{questionId}")
	void delByQuestionId(@Param(value = "questionId") Long questionId);

}
