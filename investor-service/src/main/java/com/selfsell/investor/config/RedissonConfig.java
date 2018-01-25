package com.selfsell.investor.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(RedissonVars.class)
public class RedissonConfig {
	@Autowired
	RedissonVars redissonVars;

	@Bean
	RedissonClient redissonSingle() {
		Config config = new Config();
		SingleServerConfig serverConfig = config.useSingleServer().setAddress(redissonVars.getAddress())
				.setTimeout(redissonVars.getTimeout()).setConnectionPoolSize(redissonVars.getConnectionPoolSize())
				.setConnectionMinimumIdleSize(redissonVars.getConnectionMinimumIdleSize());

		if (!StringUtils.isEmpty(redissonVars.getPassword())) {
			serverConfig.setPassword(redissonVars.getPassword());
		}

		return Redisson.create(config);
	}
}
