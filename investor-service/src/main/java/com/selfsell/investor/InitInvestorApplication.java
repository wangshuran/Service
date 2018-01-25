package com.selfsell.investor;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitInvestorApplication implements CommandLineRunner {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	RedissonClient redissonClient;

	@Override
	public void run(String... args) throws Exception {

		log.info("投资人服务启动完成>>");

	}

}
