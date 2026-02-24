package com.example.consumer.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.FixedBackOff;
import com.example.consumer.kafka.MonotonicityViolationException;

@Configuration
public class KafkaErrorHandlingConfig {

	private static final long RETRY_ATTEMPTS = 5L;
	private static final long BACKOFF_MS = 0L;

	@Bean
	public CommonErrorHandler kafkaErrorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
		// FixedBackOff expects the number of retries (not total attempts), so subtract 1.
		long maxRetries = Math.max(0L, RETRY_ATTEMPTS - 1);
		DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
			kafkaTemplate,
			(record, ex) -> new TopicPartition(record.topic() + ".dlt", record.partition())
		);
		DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, new FixedBackOff(BACKOFF_MS, maxRetries));
		handler.addNotRetryableExceptions(MonotonicityViolationException.class);
		return handler;
	}
}
