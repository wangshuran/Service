package com.selfsell.investor.bean;

/**
 * 发送邮件类型
 * 
 * @author breeze
 *
 */
public enum MailType {
	/**
	 * 发送纯文本的简单邮件
	 */
	SIMPLE,
	/**
	 * 发送html格式的邮件
	 */
	HTML,
	/**
	 * 发送带附件的邮件
	 */
	ATTACHMENTS,
	/**
	 * 发送嵌入静态资源（一般是图片）的邮件
	 */
	INLINE_RESOURCE
}
