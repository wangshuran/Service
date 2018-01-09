package com.selfsell.investee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@MapperScan("com.selfsell.investee.**.mapper")
@ServletComponentScan
@SpringBootApplication
@EnableAsync
public class InvesteeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvesteeApplication.class, args);
	}
}
