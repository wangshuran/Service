package com.selfsell.mgt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitApplication implements CommandLineRunner {
	Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String... args) throws Exception {
		log.info(">>SelfSell管理后台初始化完成<<");
	}

}
