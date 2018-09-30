package com.giffing.bucket4j.spring.boot.starter.examples.ehcache.metrics;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricTagResult;
import com.giffing.bucket4j.spring.boot.starter.context.metrics.MetricTagStrategy;
import com.giffing.bucket4j.spring.boot.starter.examples.ehcache.config.security.SecurityService;

@Component
public class UsernameMetricTagStrategy implements MetricTagStrategy<ServletRequest> {

	@Autowired
	private SecurityService securityService;
	
	@Override
	public MetricTagResult getTags(ServletRequest request) {
		String username = securityService.username();
		return new MetricTagResult(key(), username == null ? "anonym" : username);
	}

	@Override
	public String key() {
		return "USERNAME";
	}

	@Override
	public boolean supports(Object request) {
		return request instanceof ServletRequest;
	}

}
