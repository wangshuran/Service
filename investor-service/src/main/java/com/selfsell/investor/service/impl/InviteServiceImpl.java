package com.selfsell.investor.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.RowBounds;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.selfsell.common.exception.BusinessException;
import com.selfsell.common.util.CheckParamUtil;
import com.selfsell.investor.bean.I18nMessageCode;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.InvestorExt;
import com.selfsell.investor.mybatis.domain.InviteRecord;
import com.selfsell.investor.mybatis.mapper.InvestorExtMapper;
import com.selfsell.investor.mybatis.mapper.InvestorMapper;
import com.selfsell.investor.mybatis.mapper.InviteRecordMapper;
import com.selfsell.investor.service.I18nService;
import com.selfsell.investor.service.InviteService;
import com.selfsell.investor.share.InviteInfoREQ;
import com.selfsell.investor.share.InviteInfoRES;
import com.selfsell.investor.share.InviteInfoRES.ElementRewardRankList;

import tk.mybatis.mapper.entity.Example;

@Component
public class InviteServiceImpl implements InviteService {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	InvestorMapper investorMapper;
	@Autowired
	InviteRecordMapper inviteRecordMapper;
	@Autowired
	I18nService i18nService;
	@Autowired
	InvestorExtMapper investorExtMapper;

	@Autowired
	RedissonClient redissonClient;

	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };

	/** (不能与自定义进制有重复) */
	private static final char b = 'o';

	/** 进制长度 */
	private static final int binLen = r.length;

	/** 序列最小长度 */
	private static final int s = 6;

	@Override
	@Async
	public void setUserInviteCode(Long userId) {
		investorMapper.updateInviteCode(userId, genInviteCode(userId));
	}

	/**
	 * 生成邀请码
	 * 
	 * @param id
	 * @return
	 */
	public static String genInviteCode(long id) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}

	@Override
	public void invite(Long userId, String inviteCode) {
		Example param = new Example(Investor.class);
		param.createCriteria().andEqualTo("inviteCode", inviteCode);
		List<Investor> inviterList = investorMapper.selectByExample(param);
		if (inviterList == null || inviterList.size() != 1) {
			log.info("用户【{}】邀请码【{}】不正常", userId, inviteCode);
			return;
		}
		Investor inviter = inviterList.get(0);
		// 插入邀请记录
		InviteRecord inviteRecord = new InviteRecord();
		inviteRecord.setUserId(userId);
		inviteRecord.setInviterId(inviter.getId());
		inviteRecord.setCreateTime(new Date());
		inviteRecord.setInviteReward(BigDecimal.ZERO);// TODO 邀请奖励??
		inviteRecordMapper.insert(inviteRecord);

		RLock lock = redissonClient.getLock(Joiner.on("::").join("INVESTOR", "INVITE", "LOCK", userId));
		lock.lock();
		investorExtMapper.updateInvite(userId, inviteRecord.getInviteReward());
		lock.unlock();
	}

	@Override
	public InviteInfoRES inviteInfo(InviteInfoREQ inviteInfoREQ) {
		// 验证参数
		CheckParamUtil.checkBoolean(inviteInfoREQ.getId() == null, i18nService.getMessage(I18nMessageCode.PC_1000_05));

		Investor investor = investorMapper.selectByPrimaryKey(inviteInfoREQ.getId());
		if (investor == null) {
			throw new BusinessException(
					i18nService.getMessage(I18nMessageCode.account_id_not_exists, inviteInfoREQ.getId()));
		}
		InvestorExt investorExt = investorExtMapper.selectByPrimaryKey(investor.getId());

		InviteInfoRES result = new InviteInfoRES();
		result.setInviteCode(investor.getInviteCode());
		if (investorExt != null) {
			result.setInviteNum(investorExt.getInviteNum());
			result.setReward(investorExt.getInviteReward());
		}

		Example param = new Example(InvestorExt.class);
		param.orderBy("inviteReward").desc();
		RowBounds rowBounds = new RowBounds(0, 5);
		List<InvestorExt> rankList = investorExtMapper.selectByExampleAndRowBounds(param, rowBounds);
		if (rankList != null && !rankList.isEmpty()) {
			List<ElementRewardRankList> resultRankList = Lists.transform(rankList,
					new Function<InvestorExt, ElementRewardRankList>() {

						@Override
						public ElementRewardRankList apply(InvestorExt input) {
							ElementRewardRankList output = new ElementRewardRankList();
							output.setEmail(input.getEmail());
							output.setReward(input.getInviteReward());
							return output;
						}

					});
			result.setRewardRankList(resultRankList);
		}
		return result;
	}
}
