package com.example.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorHandlingConfig {

	private static final long RETRY_ATTEMPTS = 5L;
	private static final long BACKOFF_MS = 0L;

	@Bean
	public CommonErrorHandler kafkaErrorHandler() {
		// FixedBackOff expects the number of retries (not total attempts), so subtract 1.
		long maxRetries = Math.max(0L, RETRY_ATTEMPTS - 1);
		return new DefaultErrorHandler(new FixedBackOff(BACKOFF_MS, maxRetries));
	}
}
