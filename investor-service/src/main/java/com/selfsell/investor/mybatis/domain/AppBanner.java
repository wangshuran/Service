package com.selfsell.investor.mybatis.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.selfsell.investor.share.WBrecordStatus;
import com.selfsell.investor.share.WBlang;

@Table(name = "app_banner")
public class AppBanner implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imgUrl;// 图片URL
	private String title;// 标题
	private String subTitle;// 子标题
	private Integer weight;// 权重
	private WBrecordStatus status;// 状态
	private WBlang lang;//多语言
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public WBrecordStatus getStatus() {
		return status;
	}

	public void setStatus(WBrecordStatus status) {
		this.status = status;
	}

	public WBlang getLang() {
		return lang;
	}

	public void setLang(WBlang lang) {
		this.lang = lang;
	}

}
