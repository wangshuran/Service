package com.selfsell.investor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.selfsell.common.bean.ResultMap;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.InviteService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.share.FundInfoREQ;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorDisableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorEnableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.InvestorLoginREQ;
import com.selfsell.investor.share.InvestorRegisterREQ;
import com.selfsell.investor.share.InvestorResetPasswordREQ;
import com.selfsell.investor.share.InviteInfoREQ;
import com.selfsell.investor.share.ModifyPasswordREQ;
import com.selfsell.investor.share.QueryTransferInfoREQ;
import com.selfsell.investor.share.TradeInfoREQ;
import com.selfsell.investor.share.TransferREQ;
import com.selfsell.investor.share.Urls;

@RestController
public class InvestorController {

	@Autowired
	InvestorService investorService;

	@Autowired
	InviteService inviteService;

	@Autowired
	TradeRecordService tradeRecordService;

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

	@RequestMapping(value = Urls.FUND_INFO)
	ResultMap fundInfo(@RequestBody FundInfoREQ fundInfoREQ) {
		return ResultMap.successResult(investorService.fundInfo(fundInfoREQ));
	}

	@RequestMapping(value = Urls.TRADE_INFO)
	ResultMap tradeInfo(@RequestBody TradeInfoREQ tradeInfoREQ) {
		return ResultMap.successResult(tradeRecordService.tradeInfo(tradeInfoREQ));
	}

	@RequestMapping(value = Urls.QUERY_TRANSFER_INFO)
	ResultMap queryTransferInfo(@RequestBody QueryTransferInfoREQ queryTransferInfoREQ) {
		return ResultMap.successResult(investorService.queryTransferInfo(queryTransferInfoREQ));
	}

	@RequestMapping(value = Urls.TRANSFER)
	ResultMap transfer(@RequestBody TransferREQ transferREQ) {
		investorService.transfer(transferREQ);
		return ResultMap.successResult();
	}
	
	@RequestMapping(value = Urls.INVESTOR_LIST)
	ResultMap list(@RequestBody InvestorListBean investorListBean) {
		PageInfo<Investor> investorPage = investorService.pageList(investorListBean);
		
		List<InvestorBean> resultList = investorService.wrapper(investorPage.getList());
		
		return ResultMap.successResult().set("totalAmount", investorPage.getTotal()).set("resultList", resultList);
	}
	@RequestMapping(value = Urls.INVESTOR_UPDATE_STATUS)
	ResultMap updateStatus(@RequestBody InvestorBean investorBean) {
		
		investorService.updateStatus(investorBean);
		
		return ResultMap.successResult();
	}
}
