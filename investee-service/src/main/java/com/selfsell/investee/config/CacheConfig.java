package com.selfsell.investee.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

	@Autowired
	RedissonClient redissonClient;

	@Bean
	CacheManager cacheManager() {
		return new RedissonSpringCacheManager(redissonClient);
	}
}
