package com.selfsell.investor.service.impl;

import java.util.List;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.selfsell.investor.mybatis.domain.Investor;
import com.selfsell.investor.mybatis.domain.InvestorGoogleAuth;
import com.selfsell.investor.mybatis.mapper.InvestorGoogleAuthMapper;
import com.selfsell.investor.service.InvestorService;
import com.warrenstrange.googleauth.ICredentialRepository;

/**
 * google auth 密钥管理
 * 
 * @author breeze
 *
 */
@Component
public class ICredentialRepositoryImpl implements ICredentialRepository {

	@Autowired
	RedissonClient redissonClient;

	@Autowired
	InvestorService investorService;

	@Autowired
	InvestorGoogleAuthMapper investorGoogleAuthMapper;

	@Override
	public String getSecretKey(String userName) {
		RBucket<String> bucket = redissonClient
				.getBucket(Joiner.on(":").join("INVESTOR", "GOOGLE", "AUTH", "SECRETKEY", userName));

		if (!bucket.isExists()) {
			Investor user = investorService.queryByEmail(userName);
			if (user != null) {
				InvestorGoogleAuth googleAuth = investorGoogleAuthMapper.selectByPrimaryKey(user.getId());
				if (googleAuth != null)
					bucket.set(googleAuth.getGoogleAuthKey());
			}
		}
		return bucket.get();
	}

	@Override
	public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {
		investorService.insertGoogleAuthKey(userName, secretKey);

		RBucket<String> bucket = redissonClient
				.getBucket(Joiner.on(":").join("INVESTOR", "GOOGLE", "AUTH", "SECRETKEY", userName));
		bucket.set(secretKey);
	}

}
