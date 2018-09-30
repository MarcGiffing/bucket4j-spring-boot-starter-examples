package com.giffing.bucket4j.spring.boot.starter.examples.ehcache.metrics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricTagResult;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricTagStrategy;

@Component
public class UrlMetricTagStrategy implements MetricTagStrategy<HttpServletRequest> {

	@Override
	public MetricTagResult getTags(HttpServletRequest request) {
		return new MetricTagResult(key(), request.getRequestURI());
	}

	@Override
	public String key() {
		return "URL";
	}

	@Override
	public boolean supports(Object request) {
		return request instanceof HttpServletRequest;
	}

}
