package com.selfsell.investor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.InviteService;
import com.selfsell.investor.share.InvestorDisableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorEnableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorLoginREQ;
import com.selfsell.investor.share.InvestorRegisterREQ;
import com.selfsell.investor.share.InvestorResetPasswordREQ;
import com.selfsell.investor.share.InviteInfoREQ;
import com.selfsell.investor.share.ModifyPasswordREQ;
import com.selfsell.investor.share.Urls;

@RestController
public class InvestorController {

	@Autowired
	InvestorService investorService;
	
	@Autowired
	InviteService inviteService;

	@RequestMapping(value = Urls.INVESTOR_REGISTER)
	ResultMap register(@RequestBody InvestorRegisterREQ investorRegisterREQ) {
		investorService.register(investorRegisterREQ);
		return ResultMap.successResult();
	}

	@RequestMapping(value = Urls.INVESTOR_LOGIN)
	ResultMap login(@RequestBody InvestorLoginREQ investorLoginREQ) {
		return ResultMap.successResult(investorService.login(investorLoginREQ));
	}

	@RequestMapping(value = Urls.INVESTOR_ENABLE_GOOGLEAUTH)
	ResultMap enableGoogleAuth(@RequestBody InvestorEnableGoogleAuthREQ enableGoogleAuthREQ) {
		return ResultMap.successResult(investorService.enableGoogleAuth(enableGoogleAuthREQ));
	}

	@RequestMapping(value = Urls.INVESTOR_DISABLE_GOOGLEAUTH)
	ResultMap disableGoogleAuth(@RequestBody InvestorDisableGoogleAuthREQ disableGoogleAuthREQ) {
		investorService.disableGoogleAuth(disableGoogleAuthREQ);
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.INVESTOR_RESET_PASSWORD)
	ResultMap resetPassword(@RequestBody InvestorResetPasswordREQ resetPasswordREQ) {
		investorService.resetPassword(resetPasswordREQ);
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.INVITE_INFO)
	ResultMap inviteInfo(@RequestBody InviteInfoREQ inviteInfoREQ) {
		return ResultMap.successResult(inviteService.inviteInfo(inviteInfoREQ));
	}
	
	@RequestMapping(value = Urls.INVESTOR_MODIFY_PASSWORD)
	ResultMap modifyPassword(@RequestBody ModifyPasswordREQ modifyPasswordREQ) {
		investorService.modifyPassword(modifyPasswordREQ);
		return ResultMap.successResult();
	}
}
