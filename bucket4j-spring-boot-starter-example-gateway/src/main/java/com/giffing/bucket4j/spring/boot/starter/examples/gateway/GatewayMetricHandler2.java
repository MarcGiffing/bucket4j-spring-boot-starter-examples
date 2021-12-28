package com.giffing.bucket4j.spring.boot.starter.examples.gateway;

import java.util.List;

import org.springframework.stereotype.Component;

import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricHandler;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricTagResult;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricType;


@Component
public class GatewayMetricHandler2 implements MetricHandler {

	@Override
	public void handle(MetricType type, String name, long tokens, List<MetricTagResult> tags) {
		System.out.println(type);
		System.out.println(name);
		System.out.println(tokens);
		System.out.println("################");
		
	}

}
