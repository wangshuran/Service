package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * 加入答题活动
 *
 * messageId[joinAnswerActivity]
 * 
 * @author breeze
 * 
 */
public class JoinAnswerActivityREQ implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private Long id;

	/**
	 * @return 主键ID
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}