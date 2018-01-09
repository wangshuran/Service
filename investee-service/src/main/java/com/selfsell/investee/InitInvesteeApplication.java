package com.selfsell.investee;

import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitInvesteeApplication implements CommandLineRunner {
	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	RedissonClient redissonClient;

	@Override
	public void run(String... args) throws Exception {

		log.info("募资人服务启动完成>>");

	}

}
