package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.service.EmailService;
import com.selfsell.investor.share.SendEmailREQ;
import com.selfsell.investor.share.Urls;

@RestController
public class EmailController {
	@Autowired
	EmailService emailService;

	/**
	 * 发送邮件
	 * 
	 * @return
	 */
	@RequestMapping(value = Urls.SEND_EMAIL, method = RequestMethod.POST)
	public ResultMap sendRegisterEmail(@RequestBody SendEmailREQ sendEmailREQ) {
		return ResultMap.successResult(emailService.sendEmail(sendEmailREQ));
	}
}
