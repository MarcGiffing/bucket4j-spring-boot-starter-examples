package com.giffing.bucket4j.spring.boot.starter.examples.webflux;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("webflux") // Like this
public class WebfluxRateLimitTest {

	@Autowired
    ApplicationContext context;

    WebTestClient rest;

    @Before
    public void setup() {
        this.rest = WebTestClient
            .bindToApplicationContext(this.context)
            .configureClient()
            .build();
    }

	@Test
	public void helloTest() throws Exception {
		String url = "/hello";
		IntStream.rangeClosed(1, 5)
			.boxed()
			.sorted(Collections.reverseOrder())
			.forEach(counter -> {
				successfulWebRequest(url, counter - 1);
			});
		
			blockedWebRequestDueToRateLimit(url);
		}

	
	@Test
	public void worldTest() throws Exception {
		String url = "/world";
		IntStream.rangeClosed(1, 10)
			.boxed()
			.sorted(Collections.reverseOrder())
			.forEach(counter -> {
				System.out.println(counter);
				successfulWebRequest(url, counter - 1);
			});
		
			blockedWebRequestDueToRateLimit(url);
		}
	
	private void successfulWebRequest(String url, Integer remainingTries) {
		rest
			.get()
			.uri(url)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().valueEquals("X-Rate-Limit-Remaining", String.valueOf(remainingTries));	
	}	

	private void blockedWebRequestDueToRateLimit(String url) throws Exception {
		rest
		.get()
		.uri(url)
		.exchange()
		.expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS)
		.expectBody().jsonPath("error", "Too many requests!");
	}

}
