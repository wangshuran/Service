package com.selfsell.investor.bean;

/**
 * 发送邮件对象
 * 
 * @author breeze
 *
 */
public class MailObject {

	/**
	 * 发送邮件类型
	 */
	MailType mailType;
	/**
	 * 发送邮件地址
	 */
	private String to;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 附件路径 MailType为ATTACHMENTS可用
	 */
	private String filePath;
	/**
	 * 静态资源路径和文件名 MailType为INLINE_RESOURCE可用
	 */
	private String rscPath;
	/**
	 * 静态资源id MailType为INLINE_RESOURCE可用
	 */
	private String rscId;

	public MailType getMailType() {
		return mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRscPath() {
		return rscPath;
	}

	public void setRscPath(String rscPath) {
		this.rscPath = rscPath;
	}

	public String getRscId() {
		return rscId;
	}

	public void setRscId(String rscId) {
		this.rscId = rscId;
	}

}
