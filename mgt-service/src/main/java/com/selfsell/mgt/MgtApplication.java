package com.selfsell.mgt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@MapperScan(basePackages= {"com.breeze.bms.**.mapper","com.selfsell.mgt.**.mapper"})
@ComponentScan(basePackages= {"com.breeze.bms","com.selfsell.mgt"})
@SpringBootApplication
public class MgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgtApplication.class, args);
	}
	
}
