package com.selfsell.investor.share;

import java.io.Serializable;
import java.util.List;

public class AAQuestionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long aaId;// 活动ID
	private Integer number;// 题号
	private String question;// 题目
	private String answer;// 答案

	private List<AAOptionBean> options;

	public static class AAOptionBean implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAaId() {
		return aaId;
	}

	public void setAaId(Long aaId) {
		this.aaId = aaId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<AAOptionBean> getOptions() {
		return options;
	}

	public void setOptions(List<AAOptionBean> options) {
		this.options = options;
	}

}
