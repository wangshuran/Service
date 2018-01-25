package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 题目选项
 * 
 * @author breeze
 *
 */
@Table(name = "aa_option")
public class AAOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long questionId;// 活动ID
	private String optionCode;// 选项
	private String optionContent;// 题目

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String code) {
		this.optionCode = code;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String option) {
		this.optionContent = option;
	}

}
