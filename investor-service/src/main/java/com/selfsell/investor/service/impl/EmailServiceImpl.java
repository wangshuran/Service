package com.selfsell.investor.service.impl;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.common.util.G;
import com.selfsell.common.util.RandomUtil;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.bean.MailObject;
import com.selfsell.investor.bean.MailType;
import com.selfsell.investor.service.EmailService;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.share.SendEmailREQ;
import com.selfsell.investor.share.SendEmailRES;

@Component
public class EmailServiceImpl implements EmailService, InitializingBean {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RedissonClient redissonClient;

	@Autowired
	I18nService i18nService;

	@Autowired
	JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	String from;

	AsyncEventBus mailEventBus;

	@Override
	public SendEmailRES sendEmail(SendEmailREQ sendEmailREQ) {
		// 验证参数
		CheckParamUtil.checkEmpty(sendEmailREQ.getEmail(), i18nService.getMessage(I18nMessageCode.PC_1000_01));
		CheckParamUtil.checkEmpty(sendEmailREQ.getType(), i18nService.getMessage(I18nMessageCode.PC_1000_08));

		if ("REGISTER".equals(sendEmailREQ.getType())) {
			return sendRegisterEmail(sendEmailREQ);
		} else if ("RESETPWD".equals(sendEmailREQ.getType())) {
			return sendResetPwdEmail(sendEmailREQ);
		}

		return null;
	}

	/**
	 * 发送重置密码邮件
	 * 
	 * @param sendEmailREQ
	 * @return
	 */
	private SendEmailRES sendResetPwdEmail(SendEmailREQ sendEmailREQ) {
		RBucket<String> resetpwdCode = redissonClient
				.getBucket(Joiner.on("::").join("INVESTOR", "MAILCHECK", "RESETPWD", sendEmailREQ.getEmail()));
		if (!resetpwdCode.isExists()) {
			String code = RandomUtil.genNum(6);
			String subject = i18nService.getMessage(I18nMessageCode.resetpwd_mail_subject);
			String content = i18nService.getMessage(I18nMessageCode.resetpwd_mail_text, code, 5);

			MailObject mailObject = new MailObject();
			mailObject.setMailType(MailType.SIMPLE);
			mailObject.setTo(sendEmailREQ.getEmail());
			mailObject.setSubject(subject);
			mailObject.setContent(content);
			sendMail(mailObject);

			resetpwdCode.set(code, 5 * 60 * 1000, TimeUnit.MILLISECONDS);
		}
		SendEmailRES result = new SendEmailRES();
		result.setTimeAlive(G.i(resetpwdCode.remainTimeToLive()));
		return result;
	}

	/**
	 * 发送注册邮件
	 * 
	 * @param sendEmailREQ
	 * @return
	 */
	private SendEmailRES sendRegisterEmail(SendEmailREQ sendEmailREQ) {
		RBucket<String> registerCode = redissonClient
				.getBucket(Joiner.on("::").join("INVESTOR", "MAILCHECK", "REGISTER", sendEmailREQ.getEmail()));
		if (!registerCode.isExists()) {
			String code = RandomUtil.genNum(6);
			String subject = i18nService.getMessage(I18nMessageCode.register_mail_subject);
			String content = i18nService.getMessage(I18nMessageCode.register_mail_text, code, 5);

			MailObject mailObject = new MailObject();
			mailObject.setMailType(MailType.SIMPLE);
			mailObject.setTo(sendEmailREQ.getEmail());
			mailObject.setSubject(subject);
			mailObject.setContent(content);
			sendMail(mailObject);

			registerCode.set(code, 5 * 60 * 1000, TimeUnit.MILLISECONDS);
		}
		SendEmailRES result = new SendEmailRES();
		result.setTimeAlive(G.i(registerCode.remainTimeToLive()));
		return result;
	}

	@Override
	public void sendMail(MailObject mailObject) {
		mailEventBus.post(mailObject);
	}

	@Override
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		try {
			mailSender.send(message);
		} catch (Exception e) {
			log.error("发送邮件异常", e);
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_03));
		}
	}

	@Override
	public void sendHtmlMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			mailSender.send(message);
		} catch (Exception e) {
			log.error("发送邮件异常", e);
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_03));
		}
	}

	@Override
	public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource file = new FileSystemResource(new File(filePath));
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
			helper.addAttachment(fileName, file);

			mailSender.send(message);
		} catch (Exception e) {
			log.error("发送邮件异常", e);
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_03));
		}
	}

	@Override
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource res = new FileSystemResource(new File(rscPath));
			helper.addInline(rscId, res);

			mailSender.send(message);
		} catch (Exception e) {
			log.error("发送邮件异常", e);
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_03));
		}
	}

	public class MailEvent {

		@Subscribe
		public void sub(MailObject mailObject) throws Exception {
			log.info(objectMapper.writeValueAsString(mailObject));
			switch (mailObject.getMailType()) {
			case SIMPLE:
				sendSimpleMail(mailObject.getTo(), mailObject.getSubject(), mailObject.getContent());
				break;
			case HTML:
				sendHtmlMail(mailObject.getTo(), mailObject.getSubject(), mailObject.getContent());
				break;
			case ATTACHMENTS:
				sendAttachmentsMail(mailObject.getTo(), mailObject.getSubject(), mailObject.getContent(),
						mailObject.getFilePath());
				break;
			case INLINE_RESOURCE:
				sendInlineResourceMail(mailObject.getTo(), mailObject.getSubject(), mailObject.getContent(),
						mailObject.getRscPath(), mailObject.getRscId());
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		mailEventBus = new AsyncEventBus(Executors.newFixedThreadPool(8));
		mailEventBus.register(new MailEvent());
	}

}
