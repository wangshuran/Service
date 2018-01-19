package com.selfsell.investor.bean;

public enum I18nMessageCode {
	/**
	 * 授权令牌签名验证失败
	 */
	ATF_1001_01,
	/**
	 * 授权令牌过期
	 */
	ATF_1001_02,
	/**
	 * 授权令牌参数验证失败
	 */
	ATF_1001_03,
	/**
	 * 授权令牌格式错误
	 */
	ATF_1001_04,
	/**
	 * 邮箱为空
	 */
	PC_1000_01,
	/**
	 * 密码为空
	 */
	PC_1000_02,
	/**
	 * 邮箱验证码为空
	 */
	PC_1000_03,
	/**
	 * 邮箱已注册
	 */
	PC_1000_04,
	/**
	 * 用户ID为空
	 */
	PC_1000_05,
	/**
	 * 步骤号为空
	 */
	PC_1000_06,
	/**
	 * google验证码为空
	 */
	PC_1000_07,
	/**
	 * 发送邮件类型为空
	 */
	PC_1000_08,
	/**
	 * 新密码为空
	 */
	PC_1000_09,
	/**
	 * 转账数量为空
	 */
	PC_1000_10,
	/**
	 * 转账地址为空
	 */
	PC_1000_11,
	/**
	 * 请先发送邮箱验证码
	 */
	EC_1002_01,
	/**
	 * 邮箱验证码验证失败
	 */
	EC_1002_02,
	/**
	 * 发送邮件异常
	 */
	EC_1002_03,
	/**
	 * 邮箱账户为空
	 */
	EC_1002_04,
	/**
	 * 密码错误
	 */
	password_error,
	/**
	 * 注册邮件验证主题
	 */
	register_mail_subject,
	/**
	 * 注册邮件验证内容
	 */
	register_mail_text,
	/**
	 * 重置密码邮件验证主题
	 */
	resetpwd_mail_subject,
	/**
	 * 重置密码邮件验证内容
	 */
	resetpwd_mail_text,
	/**
	 * 忘记密码邮件主题
	 */
	forget_password_mail_subject,
	/**
	 * 忘记密码邮件内容
	 */
	forget_password_mail_text,
	/**
	 * 用户ID账号不存在
	 */
	account_id_not_exists,
	/**
	 * 创建google验证二维码异常
	 */
	google_qrcode_exception,
	/**
	 * google验证异常
	 */
	google_auth_check_exception,
	/**
	 * 生成令牌异常
	 */
	jwt_build_exception
}
