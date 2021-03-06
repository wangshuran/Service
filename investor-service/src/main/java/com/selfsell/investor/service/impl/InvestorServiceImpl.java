package com.selfsell.investor.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.common.util.G;
import com.selfsell.common.util.QRCodeUtil;
import com.selfsell.investor.bean.CurrencyType;
import com.selfsell.investor.bean.GoogleAuthStatus;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.bean.TradeRecordStatus;
import com.selfsell.investor.bean.TradeType;
import com.selfsell.investor.mybatis.domain.FinancialRecord;
import com.selfsell.investor.mybatis.domain.FundPlan;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.InvestorExt;
import com.selfsell.investor.mybatis.domain.InvestorGoogleAuth;
import com.selfsell.investor.mybatis.domain.ResurrectionCardRecord;
import com.selfsell.investor.mybatis.domain.TradeRecord;
import com.selfsell.investor.mybatis.domain.TransferRecord;
import com.selfsell.investor.mybatis.mapper.InvestorExtMapper;
import com.selfsell.investor.mybatis.mapper.InvestorGoogleAuthMapper;
import com.selfsell.investor.mybatis.mapper.InvestorMapper;
import com.selfsell.investor.mybatis.mapper.ResurrectionCardRecordMapper;
import com.selfsell.investor.service.FinancialService;
import com.selfsell.investor.service.FundPlanService;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.service.InvestorService;
import com.selfsell.investor.service.InviteService;
import com.selfsell.investor.service.ParamSetService;
import com.selfsell.investor.service.TokenPriceService;
import com.selfsell.investor.service.TradeRecordService;
import com.selfsell.investor.service.TransferService;
import com.selfsell.investor.share.BuyResurrectionCardREQ;
import com.selfsell.investor.share.BuyResurrectionCardRES;
import com.selfsell.investor.share.CapitalPasswordAddREQ;
import com.selfsell.investor.share.CapitalPasswordUpdateREQ;
import com.selfsell.investor.share.CheckGoogleAuthREQ;
import com.selfsell.investor.share.Constants;
import com.selfsell.investor.share.FundInfoREQ;
import com.selfsell.investor.share.FundInfoRES;
import com.selfsell.investor.share.FundInfoRES.ElementFundDetail;
import com.selfsell.investor.share.InvestorBean;
import com.selfsell.investor.share.InvestorDisableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorDisableGoogleAuthRES;
import com.selfsell.investor.share.InvestorEnableGoogleAuthREQ;
import com.selfsell.investor.share.InvestorEnableGoogleAuthRES;
import com.selfsell.investor.share.InvestorListBean;
import com.selfsell.investor.share.InvestorLoginREQ;
import com.selfsell.investor.share.InvestorLoginRES;
import com.selfsell.investor.share.InvestorLoginRES.ElementInvestor;
import com.selfsell.investor.share.InvestorRegisterREQ;
import com.selfsell.investor.share.InvestorRegisterRES;
import com.selfsell.investor.share.InvestorResetPasswordREQ;
import com.selfsell.investor.share.InvestorResetPasswordRES;
import com.selfsell.investor.share.ModifyPasswordREQ;
import com.selfsell.investor.share.ModifyPasswordRES;
import com.selfsell.investor.share.ParamKeys;
import com.selfsell.investor.share.QueryTransferInfoREQ;
import com.selfsell.investor.share.QueryTransferInfoRES;
import com.selfsell.investor.share.TransferREQ;
import com.selfsell.investor.share.TransferRES;
import com.selfsell.investor.share.UseResurrectionCardREQ;
import com.selfsell.investor.share.UseResurrectionCardRES;
import com.selfsell.investor.share.WBinout;
import com.selfsell.investor.share.WBinvestorStatus;
import com.selfsell.investor.share.WBrcChannel;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import com.warrenstrange.googleauth.ICredentialRepository;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
public class InvestorServiceImpl implements InvestorService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	I18nService i18nService;

	@Autowired
	InvestorMapper investorMapper;

	@Autowired
	InvestorExtMapper investorExtMapper;

	@Autowired
	RedissonClient redissonClient;

	@Autowired
	InviteService inviteService;

	@Autowired
	ICredentialRepository icredentialRepo;

	@Autowired
	InvestorGoogleAuthMapper investorGoogleAuthMapper;

	@Autowired
	@Qualifier("tokenPriceServiceOkex")
	TokenPriceService tokenPriceService;

	@Autowired
	FinancialService financialService;

	@Autowired
	FundPlanService fundPlanService;

	@Autowired
	TradeRecordService tradeRecordService;

	@Autowired
	ParamSetService paramSetService;

	@Autowired
	TransferService transferService;

	@Value("${act.ssc.address}")
	String mainSscAddress;// ACT主地址

	@Autowired
	ResurrectionCardRecordMapper resurrectionCardRecordMapper;

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public InvestorRegisterRES register(InvestorRegisterREQ investorRegisterREQ) {
		// 验证参数
		CheckParamUtil.checkEmpty(investorRegisterREQ.getEmail(), i18nService.getMessage(I18nMessageCode.PC_1000_01));
		CheckParamUtil.checkEmpty(investorRegisterREQ.getPassword(),
				i18nService.getMessage(I18nMessageCode.PC_1000_02));
		CheckParamUtil.checkEmpty(investorRegisterREQ.getEmailCheckCode(),
				i18nService.getMessage(I18nMessageCode.PC_1000_03));
		CheckParamUtil.checkBoolean(existsEmail(investorRegisterREQ.getEmail()),
				i18nService.getMessage(I18nMessageCode.PC_1000_04));

		// 验证验证码
		RBucket<String> registerCode = redissonClient
				.getBucket(Joiner.on("::").join("INVESTOR", "MAILCHECK", "REGISTER", investorRegisterREQ.getEmail()));
		if (!registerCode.isExists()) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_01));
		}
		if (!investorRegisterREQ.getEmailCheckCode().equals(registerCode.get())) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_02));
		}
		registerCode.delete();

		// 插入用户数据
		log.info("投资人注册[{}]", investorRegisterREQ.getEmail());
		Investor user = new Investor();
		user.setEmail(investorRegisterREQ.getEmail());
		user.setPassword(DigestUtils.md5Hex(investorRegisterREQ.getPassword()));
		user.setCreateTime(new Date());
		user.setStatus(WBinvestorStatus.NORMAL);
		user.setSscAddress(mainSscAddress + Joiner.on("").join(UUID.randomUUID().toString().split("-")));
		user.setGoogleAuthStatus(GoogleAuthStatus.OFF);
		investorMapper.insert(user);
		// 插入扩展数据
		InvestorExt investorExt = new InvestorExt();
		investorExt.setUserId(user.getId());
		investorExt.setEmail(user.getEmail());
		investorExt.setInviteNum(0);
		investorExt.setInviteReward(BigDecimal.ZERO);
		investorExt.setTotalSSC(BigDecimal.ZERO);
		investorExt.setAvailableSSC(BigDecimal.ZERO);
		investorExt.setAnswerReward(BigDecimal.ZERO);
		investorExt.setResurrectionCard(0);
		investorExtMapper.insert(investorExt);
		// 设置邀请码
		inviteService.setUserInviteCode(user.getId());

		// 推荐处理
		if (!StringUtils.isEmpty(investorRegisterREQ.getInviteCode())) {
			log.info("处理用户【{}】推荐【{}】", investorRegisterREQ.getEmail(), investorRegisterREQ.getInviteCode());
			inviteService.invite(user.getId(), investorRegisterREQ.getInviteCode());
		}

		return new InvestorRegisterRES();
	}

	public Boolean existsEmail(String email) {
		Example param = new Example(Investor.class);
		param.createCriteria().andEqualTo("email", email);

		return investorMapper.selectCountByExample(param) > 0;
	}

	@Override
	public InvestorLoginRES login(InvestorLoginREQ investorLoginREQ) {
		// 验证参数
		CheckParamUtil.checkEmpty(investorLoginREQ.getEmail(), i18nService.getMessage(I18nMessageCode.PC_1000_01));
		CheckParamUtil.checkEmpty(investorLoginREQ.getPassword(), i18nService.getMessage(I18nMessageCode.PC_1000_02));

		Investor investor = queryByEmail(investorLoginREQ.getEmail());
		if (investor == null) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_04));
		}
		if (!WBinvestorStatus.NORMAL.equals(investor.getStatus())) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_05));
		}

		if (!investor.getPassword().equals(DigestUtils.md5Hex(investorLoginREQ.getPassword()))) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.password_error));
		}

		/*
		 * if (GoogleAuthStatus.ON.equals(investor.getGoogleAuthStatus())) {
		 * CheckParamUtil.checkEmpty(investorLoginREQ.getGoogleAuthCode(),
		 * i18nService.getMessage(I18nMessageCode.PC_1000_07)); GoogleAuthenticator
		 * googleAuthenticator = new GoogleAuthenticator();
		 * googleAuthenticator.setCredentialRepository(icredentialRepo); if
		 * (!googleAuthenticator.authorizeUser(investor.getEmail(),
		 * G.i(investorLoginREQ.getGoogleAuthCode()))) { throw new
		 * BusinessException(i18nService.getMessage(I18nMessageCode.
		 * google_auth_check_exception)); } }
		 */

		InvestorLoginRES res = new InvestorLoginRES();

		// 生成登录token
		try {
			String token = JWT.create().withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).withSubject("INVESTORLOGIN")
					.withClaim("investorId", investor.getId()).sign(Algorithm.HMAC256(Constants.JWT_SECRET_AUTH));
			res.setToken("Bearer " + token);
		} catch (Exception e) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.jwt_build_exception));
		}

		ElementInvestor elementInvestor = new ElementInvestor();
		BeanUtils.copyProperties(investor, elementInvestor);
		elementInvestor.setGoogleAuthStatus(investor.getGoogleAuthStatus().name());
		elementInvestor.setStatus(investor.getStatus().name());
		res.setInvestor(elementInvestor);

		log.info("用户[{}]登录", investor.getEmail());

		return res;
	}

	@Override
	public Investor queryByEmail(String email) {
		Example param = new Example(Investor.class);
		param.createCriteria().andEqualTo("email", email);

		List<Investor> investorList = investorMapper.selectByExample(param);
		if (investorList != null && !investorList.isEmpty()) {
			return investorList.get(0);
		}
		return null;
	}

	@Override
	public void insertGoogleAuthKey(String userName, String secretKey) {

		Investor investor = queryByEmail(userName);
		InvestorGoogleAuth googleAuth = new InvestorGoogleAuth();
		googleAuth.setInvestorId(investor.getId());
		googleAuth.setGoogleAuthKey(secretKey);
		googleAuth.setUpdateTime(new Date());

		investorGoogleAuthMapper.insert(googleAuth);

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public InvestorEnableGoogleAuthRES enableGoogleAuth(InvestorEnableGoogleAuthREQ enableGoogleAuthREQ) {
		// 参数验证
		CheckParamUtil.checkBoolean(enableGoogleAuthREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkBoolean(enableGoogleAuthREQ.getStep() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_06));

		log.info("用户【{}】，开启google验证【{}】", enableGoogleAuthREQ.getId(), enableGoogleAuthREQ.getStep());

		Investor investor = queryById(enableGoogleAuthREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, enableGoogleAuthREQ.getId()));
		}

		InvestorEnableGoogleAuthRES result = new InvestorEnableGoogleAuthRES();

		if (enableGoogleAuthREQ.getStep() == 0) {// 第一步

			InvestorGoogleAuth googleAuth = investorGoogleAuthMapper.selectByPrimaryKey(investor.getId());
			if (googleAuth != null) {
				result.setGoogleAuthKey(googleAuth.getGoogleAuthKey());
				result.setGoogleAuthQrcode(googleAuth.getGoogleAuthQrcode());
			} else {
				GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
				googleAuthenticator.setCredentialRepository(icredentialRepo);
				GoogleAuthenticatorKey googleAuthenticatorKey = googleAuthenticator
						.createCredentials(investor.getEmail());
				result.setGoogleAuthKey(googleAuthenticatorKey.getKey());
				String otpUahtTotpUrl = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("SelfSell",
						investor.getEmail(), googleAuthenticatorKey);
				try {
					String qrcodeBase64 = QRCodeUtil.creatQrImageBase64(otpUahtTotpUrl, 200, 200);
					result.setGoogleAuthQrcode(qrcodeBase64);
					updateGoogleAuthQrcode(investor.getId(), qrcodeBase64);
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_qrcode_exception));
				}
			}
		} else if (enableGoogleAuthREQ.getStep() == 1) {// 第二步验证绑定
			CheckParamUtil.checkEmpty(enableGoogleAuthREQ.getGoogleAuthCode(),
					i18nService.getMessage(I18nMessageCode.PC_1000_07));

			GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
			googleAuthenticator.setCredentialRepository(icredentialRepo);
			if (googleAuthenticator.authorizeUser(investor.getEmail(), G.i(enableGoogleAuthREQ.getGoogleAuthCode()))) {
				investorMapper.updateGoogleAuthStatus(investor.getId(), GoogleAuthStatus.ON.name());
				InvestorGoogleAuth googleAuth = investorGoogleAuthMapper.selectByPrimaryKey(investor.getId());
				result.setGoogleAuthKey(googleAuth.getGoogleAuthKey());
				result.setGoogleAuthQrcode(googleAuth.getGoogleAuthQrcode());
			} else {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
			}
		}

		return result;
	}

	private void updateGoogleAuthQrcode(Long id, String qrcodeBase64) {

		investorGoogleAuthMapper.updateQrcode(id, qrcodeBase64);

	}

	private Investor queryById(Long id) {
		return investorMapper.selectByPrimaryKey(id);
	}

	@Override
	public InvestorDisableGoogleAuthRES disableGoogleAuth(InvestorDisableGoogleAuthREQ disableGoogleAuthREQ) {
		// 参数验证
		CheckParamUtil.checkBoolean(disableGoogleAuthREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkEmpty(disableGoogleAuthREQ.getGoogleAuthCode(),
				i18nService.getMessage(I18nMessageCode.PC_1000_07));

		log.info("用户【{}】，关闭google验证", disableGoogleAuthREQ.getId());

		Investor investor = queryById(disableGoogleAuthREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, disableGoogleAuthREQ.getId()));
		}
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		googleAuthenticator.setCredentialRepository(icredentialRepo);
		if (googleAuthenticator.authorizeUser(investor.getEmail(), G.i(disableGoogleAuthREQ.getGoogleAuthCode()))) {
			investorMapper.updateGoogleAuthStatus(investor.getId(), GoogleAuthStatus.OFF.name());

			investorGoogleAuthMapper.deleteByPrimaryKey(investor.getId());

			RBucket<String> bucket = redissonClient
					.getBucket(Joiner.on(":").join("INVESTOR", "GOOGLE", "AUTH", "SECRETKEY", investor.getEmail()));
			bucket.delete();
		} else {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
		}

		return new InvestorDisableGoogleAuthRES();
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public InvestorResetPasswordRES resetPassword(InvestorResetPasswordREQ resetPasswordREQ) {
		// 验证参数
		CheckParamUtil.checkEmpty(resetPasswordREQ.getEmail(), i18nService.getMessage(I18nMessageCode.PC_1000_01));
		CheckParamUtil.checkEmpty(resetPasswordREQ.getEmailCheckCode(),
				i18nService.getMessage(I18nMessageCode.PC_1000_03));
		CheckParamUtil.checkEmpty(resetPasswordREQ.getPassword(), i18nService.getMessage(I18nMessageCode.PC_1000_02));

		Investor investor = queryByEmail(resetPasswordREQ.getEmail());
		if (investor == null) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_04));
		}

		RBucket<String> resetpwdCode = redissonClient
				.getBucket(Joiner.on("::").join("INVESTOR", "MAILCHECK", "RESETPWD", resetPasswordREQ.getEmail()));
		if (!resetpwdCode.isExists()) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_01));
		}
		if (!resetPasswordREQ.getEmailCheckCode().equals(resetpwdCode.get())) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.EC_1002_02));
		}
		resetpwdCode.delete();

		investorMapper.resetPwd(investor.getId(), DigestUtils.md5Hex(resetPasswordREQ.getPassword()));

		return new InvestorResetPasswordRES();
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public ModifyPasswordRES modifyPassword(ModifyPasswordREQ modifyPasswordREQ) {
		// 参数验证
		CheckParamUtil.checkBoolean(modifyPasswordREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkEmpty(modifyPasswordREQ.getPassword(), i18nService.getMessage(I18nMessageCode.PC_1000_02));
		CheckParamUtil.checkEmpty(modifyPasswordREQ.getPassword(), i18nService.getMessage(I18nMessageCode.PC_1000_09));

		Investor investor = queryById(modifyPasswordREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, modifyPasswordREQ.getId()));
		}

		if (!investor.getPassword().equals(DigestUtils.md5Hex(modifyPasswordREQ.getPassword()))) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.password_error));
		}

		investorMapper.resetPwd(investor.getId(), DigestUtils.md5Hex(modifyPasswordREQ.getPasswordNew()));

		return new ModifyPasswordRES();
	}

	@Override
	public FundInfoRES fundInfo(FundInfoREQ fundInfoREQ) {
		// 参数验证
		CheckParamUtil.checkBoolean(fundInfoREQ.getId() == null, i18nService.getMessage(I18nMessageCode.PC_1000_05));
		Investor investor = queryById(fundInfoREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, fundInfoREQ.getId()));
		}

		FundInfoRES result = new FundInfoRES();
		// 汇总信息
		InvestorExt investorExt = investorExtMapper.selectByPrimaryKey(fundInfoREQ.getId());
		result.setTotalSSC(investorExt.getTotalSSC());
		result.setAvailableSSC(investorExt.getAvailableSSC());
		BigDecimal tokenPrice = BigDecimal.ZERO;
		if (LocaleContextHolder.getLocale().getLanguage().equals("zh")) {
			tokenPrice = tokenPriceService.queryPrice("ssc", CurrencyType.CNY);
		} else {
			tokenPrice = tokenPriceService.queryPrice("ssc", CurrencyType.USD);
		}
		result.setTotalPrice(tokenPrice.multiply(result.getTotalSSC()));

		// 理财详情
		List<FinancialRecord> financialRecords = financialService.queryInvestorFinancialRecords(investor.getId());
		if (financialRecords != null && !financialRecords.isEmpty()) {
			List<ElementFundDetail> fundDetails = Lists.transform(financialRecords,
					new Function<FinancialRecord, ElementFundDetail>() {

						@Override
						public ElementFundDetail apply(FinancialRecord input) {
							ElementFundDetail fundDetail = new ElementFundDetail();
							fundDetail.setId(input.getId());
							fundDetail.setPlanId(input.getFundPlanId());
							FundPlan fundPlan = fundPlanService.queryByIdAndLang(input.getFundPlanId(),
									LocaleContextHolder.getLocale().getLanguage());
							fundDetail.setPlanIconUrl(fundPlan.getIconUrl());
							fundDetail.setPlanTitle(fundPlan.getTitle());
							fundDetail.setAmount(input.getAmount());
							fundDetail.setInterest(input.getInterest());
							fundDetail.setCreateTime(input.getCreateTime());
							fundDetail.setFinishTime(input.getFinishTime());
							return fundDetail;
						}
					});

			result.setFundDetail(fundDetails);
		}

		return result;
	}

	@Override
	public Investor queryBySscAddress(String sscAddress) {
		Example example = new Example(Investor.class);
		example.createCriteria().andEqualTo("sscAddress", sscAddress);

		List<Investor> resultList = investorMapper.selectByExample(example);
		if (resultList != null && !resultList.isEmpty()) {
			return resultList.get(0);
		}

		return null;
	}

	@Override
	public void updateAssets(Long id, WBinout inout, BigDecimal amount, Boolean real) {
		RLock lock = redissonClient.getLock(Joiner.on("::").join("UPDATE", "ASSETS", "LOCK", id));
		lock.lock();

		InvestorExt investorExt = investorExtMapper.selectByPrimaryKey(id);
		if (investorExt != null) {
			if (WBinout.OUT.equals(inout)) {
				BigDecimal result = investorExt.getAvailableSSC().subtract(amount);
				if (result.compareTo(BigDecimal.ZERO) >= 0) {
					investorExt.setAvailableSSC(result);
					if (real) {
						investorExt.setTotalSSC(investorExt.getTotalSSC().subtract(amount));
					}
				} else {
					throw new BusinessException("账户[{0}]金额不足", id);
				}
			} else {
				investorExt.setAvailableSSC(investorExt.getAvailableSSC().add(amount));
				if (real) {
					investorExt.setTotalSSC(investorExt.getTotalSSC().add(amount));
				}
			}

			investorExtMapper.updateAssets(investorExt.getUserId(), investorExt.getTotalSSC(),
					investorExt.getAvailableSSC());

		}

		lock.unlock();
	}

	@Override
	public QueryTransferInfoRES queryTransferInfo(QueryTransferInfoREQ queryTransferInfoREQ) {
		// 参数验证
		CheckParamUtil.checkBoolean(queryTransferInfoREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		Investor investor = queryById(queryTransferInfoREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, queryTransferInfoREQ.getId()));
		}

		InvestorExt investorExt = investorExtMapper.selectByPrimaryKey(queryTransferInfoREQ.getId());

		BigDecimal fee = G.bd(paramSetService.findParamValue(ParamKeys.SSC_TRANSFER_FEE));

		QueryTransferInfoRES result = new QueryTransferInfoRES();
		result.setAvailableSSC(investorExt.getAvailableSSC().subtract(fee));
		result.setFee(fee);

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public TransferRES transfer(TransferREQ transferREQ) {
		CheckParamUtil.checkBoolean(transferREQ.getId() == null, i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkBoolean(transferREQ.getAmount() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_10));
		CheckParamUtil.checkEmpty(transferREQ.getAddress(), i18nService.getMessage(I18nMessageCode.PC_1000_11));

		Investor investor = queryById(transferREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, transferREQ.getId()));
		}

		if (!StringUtils.isEmpty(investor.getCapitalPassword())) {
			CheckParamUtil.checkEmpty(transferREQ.getCapitalPassword(),
					i18nService.getMessage(I18nMessageCode.PC_1000_14));
			if (!DigestUtils.md5Hex(transferREQ.getCapitalPassword()).equals(investor.getCapitalPassword())) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.capital_password_not_match));
			}
		}

		if (GoogleAuthStatus.ON.equals(investor.getGoogleAuthStatus())) {
			CheckParamUtil.checkEmpty(transferREQ.getGoogleAuthCode(),
					i18nService.getMessage(I18nMessageCode.PC_1000_07));
			GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
			googleAuthenticator.setCredentialRepository(icredentialRepo);
			if (!googleAuthenticator.authorizeUser(investor.getEmail(), G.i(transferREQ.getGoogleAuthCode()))) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
			}
		}

		updateAssets(transferREQ.getId(), WBinout.OUT, transferREQ.getAmount().add(transferREQ.getFee()), true);

		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setAmount(transferREQ.getAmount().add(transferREQ.getFee()));
		tradeRecord.setCreateTime(new Date());
		tradeRecord.setInoutFlag(WBinout.OUT);
		tradeRecord.setInvestorId(transferREQ.getId());
		tradeRecord.setStatus(TradeRecordStatus.audit);
		tradeRecord.settAddress(transferREQ.getAddress());
		tradeRecord.setType(TradeType.transfer_out);
		tradeRecordService.insert(tradeRecord);

		TransferRecord transferRecord = new TransferRecord();
		transferRecord.setAddress(transferREQ.getAddress());
		transferRecord.setAmount(transferREQ.getAmount());
		transferRecord.setCreateTime(new Date());
		transferRecord.setFee(transferREQ.getFee());
		transferRecord.setInvestorId(transferREQ.getId());
		transferRecord.setStatus(TradeRecordStatus.audit);
		transferRecord.setTradeRecordId(tradeRecord.getId());
		transferService.insert(transferRecord);

		return new TransferRES();
	}

	@Override
	public PageInfo<Investor> pageList(InvestorListBean investorListBean) {
		Example example = new Example(Investor.class);
		Criteria param = example.createCriteria();
		if (!StringUtils.isEmpty(investorListBean.getEmail())) {
			param.andEqualTo("email", investorListBean.getEmail());
		}
		if (!StringUtils.isEmpty(investorListBean.getStatus())) {
			param.andEqualTo("status", investorListBean.getStatus());
		}
		if (!StringUtils.isEmpty(investorListBean.getInviteCode())) {
			param.andEqualTo("inviteCode", investorListBean.getInviteCode());
		}
		if (!StringUtils.isEmpty(investorListBean.getSscAddress())) {
			param.andEqualTo("sscAddress", investorListBean.getSscAddress());
		}

		example.orderBy("createTime").desc();

		PageHelper.startPage(investorListBean.getPage() - 1, investorListBean.getLimit(), true);
		List<Investor> resultList = investorMapper.selectByExample(example);
		PageInfo<Investor> pageInfo = new PageInfo<Investor>(resultList);

		return pageInfo;
	}

	@Override
	public List<InvestorBean> wrapper(List<Investor> list) {
		if (list != null && !list.isEmpty()) {
			List<InvestorBean> resultList = Lists.newArrayList();
			for (Investor investor : list) {
				InvestorBean investorBean = new InvestorBean();
				BeanUtils.copyProperties(investor, investorBean);
				investorBean.setGoogleAuthStatus(investor.getGoogleAuthStatus().name());
				investorBean.setStatus(investor.getStatus().name());

				InvestorExt investorExt = investorExtMapper.selectByPrimaryKey(investor.getId());
				BeanUtils.copyProperties(investorExt, investorBean, "userId", "email");

				resultList.add(investorBean);
			}

			return resultList;
		}
		return null;
	}

	@Override
	public void updateStatus(InvestorBean investorBean) {
		CheckParamUtil.checkBoolean(investorBean.getId() == null, "ID为空");
		CheckParamUtil.checkEmpty(investorBean.getStatus(), "状态为空");

		investorMapper.updateStatus(investorBean.getId(), investorBean.getStatus());
	}

	@Override
	public void checkGoogleAuth(CheckGoogleAuthREQ checkGoogleAuthREQ) {
		CheckParamUtil.checkBoolean(checkGoogleAuthREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkEmpty(checkGoogleAuthREQ.getGoogleAuthCode(),
				i18nService.getMessage(I18nMessageCode.PC_1000_07));

		Investor investor = investorMapper.selectByPrimaryKey(checkGoogleAuthREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, checkGoogleAuthREQ.getId()));
		}

		if (GoogleAuthStatus.ON.equals(investor.getGoogleAuthStatus())) {
			CheckParamUtil.checkEmpty(checkGoogleAuthREQ.getGoogleAuthCode(),
					i18nService.getMessage(I18nMessageCode.PC_1000_07));
			GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
			googleAuthenticator.setCredentialRepository(icredentialRepo);
			if (!googleAuthenticator.authorizeUser(investor.getEmail(), G.i(checkGoogleAuthREQ.getGoogleAuthCode()))) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
			}
		}

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void capitalPasswordAdd(CapitalPasswordAddREQ capitalPasswordAddREQ) {
		CheckParamUtil.checkBoolean(capitalPasswordAddREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkEmpty(capitalPasswordAddREQ.getLoginPassword(),
				i18nService.getMessage(I18nMessageCode.PC_1000_02));
		CheckParamUtil.checkEmpty(capitalPasswordAddREQ.getCapitalPassword(),
				i18nService.getMessage(I18nMessageCode.PC_1000_14));

		Investor investor = investorMapper.selectByPrimaryKey(capitalPasswordAddREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, capitalPasswordAddREQ.getId()));
		}

		if (!DigestUtils.md5Hex(capitalPasswordAddREQ.getLoginPassword()).equals(investor.getPassword())) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.password_error));
		}

		if (GoogleAuthStatus.ON.equals(investor.getGoogleAuthStatus())) {
			CheckParamUtil.checkEmpty(capitalPasswordAddREQ.getGoogleAuthCode(),
					i18nService.getMessage(I18nMessageCode.PC_1000_07));
			GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
			googleAuthenticator.setCredentialRepository(icredentialRepo);
			if (!googleAuthenticator.authorizeUser(investor.getEmail(),
					G.i(capitalPasswordAddREQ.getGoogleAuthCode()))) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
			}
		}

		investorMapper.updateCapitalPassword(investor.getId(),
				DigestUtils.md5Hex(capitalPasswordAddREQ.getCapitalPassword()));

	}

	@Override
	public void capitalPasswordUpdate(CapitalPasswordUpdateREQ capitalPasswordUpdateREQ) {
		CheckParamUtil.checkBoolean(capitalPasswordUpdateREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkEmpty(capitalPasswordUpdateREQ.getCapitalPasswordNew(),
				i18nService.getMessage(I18nMessageCode.PC_1000_14));
		CheckParamUtil.checkEmpty(capitalPasswordUpdateREQ.getCapitalPasswordOld(),
				i18nService.getMessage(I18nMessageCode.PC_1000_14));

		Investor investor = investorMapper.selectByPrimaryKey(capitalPasswordUpdateREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, capitalPasswordUpdateREQ.getId()));
		}

		if (!DigestUtils.md5Hex(capitalPasswordUpdateREQ.getCapitalPasswordOld())
				.equals(investor.getCapitalPassword())) {
			throw new BusinessException(i18nService.getMessage(I18nMessageCode.capital_password_not_match));
		}

		if (GoogleAuthStatus.ON.equals(investor.getGoogleAuthStatus())) {
			CheckParamUtil.checkEmpty(capitalPasswordUpdateREQ.getGoogleAuthCode(),
					i18nService.getMessage(I18nMessageCode.PC_1000_07));
			GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
			googleAuthenticator.setCredentialRepository(icredentialRepo);
			if (!googleAuthenticator.authorizeUser(investor.getEmail(),
					G.i(capitalPasswordUpdateREQ.getGoogleAuthCode()))) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
			}
		}

		investorMapper.updateCapitalPassword(investor.getId(),
				DigestUtils.md5Hex(capitalPasswordUpdateREQ.getCapitalPasswordNew()));

	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public BuyResurrectionCardRES buyResurrectionCard(BuyResurrectionCardREQ buyResurrectionCardREQ) {
		CheckParamUtil.checkBoolean(buyResurrectionCardREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkBoolean(buyResurrectionCardREQ.getAmount() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_15));

		Investor investor = investorMapper.selectByPrimaryKey(buyResurrectionCardREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, buyResurrectionCardREQ.getId()));
		}

		if (!StringUtils.isEmpty(investor.getCapitalPassword())) {
			CheckParamUtil.checkEmpty(buyResurrectionCardREQ.getCapitalPassword(),
					i18nService.getMessage(I18nMessageCode.PC_1000_14));
			if (!DigestUtils.md5Hex(buyResurrectionCardREQ.getCapitalPassword())
					.equals(investor.getCapitalPassword())) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.capital_password_not_match));
			}
		}

		if (GoogleAuthStatus.ON.equals(investor.getGoogleAuthStatus())) {
			CheckParamUtil.checkEmpty(buyResurrectionCardREQ.getGoogleAuthCode(),
					i18nService.getMessage(I18nMessageCode.PC_1000_07));
			GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
			googleAuthenticator.setCredentialRepository(icredentialRepo);
			if (!googleAuthenticator.authorizeUser(investor.getEmail(),
					G.i(buyResurrectionCardREQ.getGoogleAuthCode()))) {
				throw new BusinessException(i18nService.getMessage(I18nMessageCode.google_auth_check_exception));
			}
		}

		Integer rcNum = updateResurrectionCardNum(investor.getId(), WBinout.IN, buyResurrectionCardREQ.getAmount());
		ResurrectionCardRecord resurrectionCardRecord = new ResurrectionCardRecord();
		resurrectionCardRecord.setAmount(buyResurrectionCardREQ.getAmount());
		resurrectionCardRecord.setChannel(WBrcChannel.BUY);
		resurrectionCardRecord.setCreateTime(new Date());
		resurrectionCardRecord.setInoutFlag(WBinout.IN);
		resurrectionCardRecord.setUserId(investor.getId());
		resurrectionCardRecordMapper.insert(resurrectionCardRecord);

		BigDecimal rcPrice = G.bd(paramSetService.findParamValue(ParamKeys.RESURRECTION_CARD_PRICE),
				new BigDecimal(100), 8);
		BigDecimal totalPrice = rcPrice.multiply(new BigDecimal(buyResurrectionCardREQ.getAmount()));
		updateAssets(investor.getId(), WBinout.OUT, totalPrice, true);
		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setAmount(totalPrice);
		tradeRecord.setCreateTime(new Date());
		tradeRecord.setInoutFlag(WBinout.OUT);
		tradeRecord.setInvestorId(investor.getId());
		tradeRecord.setRemark("购买复活卡");
		tradeRecord.setStatus(TradeRecordStatus.success);
		tradeRecord.setType(TradeType.buy_resurrection_card);
		tradeRecordService.insert(tradeRecord);

		BuyResurrectionCardRES result = new BuyResurrectionCardRES();
		result.setAmount(rcNum);
		return result;
	}

	public Integer updateResurrectionCardNum(Long id, WBinout inout, Integer amount) {
		RLock lock = redissonClient.getLock(Joiner.on("::").join("UPDATE", "RESURRECTION", "CARD", "LOCK", id));
		lock.lock();
		InvestorExt investorExt = investorExtMapper.selectByPrimaryKey(id);
		if (investorExt != null) {
			if (WBinout.OUT.equals(inout)) {
				Integer result = investorExt.getResurrectionCard() - amount;
				if (result >= 0) {
					investorExt.setResurrectionCard(result);
				} else {
					throw new BusinessException("账户[{0}]金额不足", id);
				}
			} else {
				investorExt.setResurrectionCard(investorExt.getResurrectionCard() + amount);
			}

			investorExtMapper.updateResurrectionCardNum(investorExt.getUserId(), investorExt.getResurrectionCard());

			return investorExt.getResurrectionCard();

		}
		lock.unlock();

		return null;
	}

	@Override
	public UseResurrectionCardRES useResurrectionCard(UseResurrectionCardREQ useResurrectionCardREQ) {
		CheckParamUtil.checkBoolean(useResurrectionCardREQ.getId() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_05));
		CheckParamUtil.checkBoolean(useResurrectionCardREQ.getAmount() == null,
				i18nService.getMessage(I18nMessageCode.PC_1000_15));

		Investor investor = investorMapper.selectByPrimaryKey(useResurrectionCardREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, useResurrectionCardREQ.getId()));
		}

		Integer rcNum = updateResurrectionCardNum(investor.getId(), WBinout.OUT, useResurrectionCardREQ.getAmount());
		ResurrectionCardRecord resurrectionCardRecord = new ResurrectionCardRecord();
		resurrectionCardRecord.setAmount(useResurrectionCardREQ.getAmount());
		resurrectionCardRecord.setChannel(WBrcChannel.USE);
		resurrectionCardRecord.setCreateTime(new Date());
		resurrectionCardRecord.setInoutFlag(WBinout.OUT);
		resurrectionCardRecord.setUserId(investor.getId());
		resurrectionCardRecordMapper.insert(resurrectionCardRecord);

		UseResurrectionCardRES result = new UseResurrectionCardRES();
		result.setAmount(rcNum);

		return result;
	}

}
