package com.giffing.bucket4j.spring.boot.starter.examples.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import com.giffing.bucket4j.spring.boot.starter.webflux.WebfluxRateLimitException;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ExceptionHandler extends ResponseStatusExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (ex instanceof WebfluxRateLimitException) {
			HttpStatus status = ((WebfluxRateLimitException) ex).getStatus();
			if (exchange.getResponse().setStatusCode(status)) {
					logger.warn(buildMessage(exchange.getRequest()));
				return exchange.getResponse().setComplete();
			}
		}
		if (ex.getCause() instanceof WebfluxRateLimitException) {
			HttpStatus status = ((WebfluxRateLimitException) ex.getCause()).getStatus();
			if (exchange.getResponse().setStatusCode(status)) {
					logger.warn(buildMessage(exchange.getRequest()));
				return exchange.getResponse().setComplete();
			}
		}
		return super.handle(exchange, ex);
	}
	
	

	private String buildMessage(ServerHttpRequest request) {
		return "Failed to handle request [" + request.getMethod() + " "
				+ request.getURI() + "]: ";
	}
	

}
