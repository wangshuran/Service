package com.selfsell.investor.share;

import java.io.Serializable;




/**
 * APP欢迎页
 *
 * messageId[appBanner]
 * 
 * @author breeze
 * 
 */
public class AppBannerRES implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String imgUrl;
	private String title;
	private String subTitle;

		
	/**
	 * @return 图片链接
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

		
	/**
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

		
	/**
	 * @return 子标题
	 */
	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}


}