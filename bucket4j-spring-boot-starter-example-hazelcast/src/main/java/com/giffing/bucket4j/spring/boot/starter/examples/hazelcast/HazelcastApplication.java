package com.giffing.bucket4j.spring.boot.starter.examples.hazelcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

@SpringBootApplication
@EnableCaching
public class HazelcastApplication {

	public static void main(String[] args) {
		SpringApplication.run(HazelcastApplication.class, args);
	}
	
}
