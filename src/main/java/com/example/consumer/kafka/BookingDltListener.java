package com.example.consumer.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingDltListener {

	private static final Logger log = LoggerFactory.getLogger(BookingDltListener.class);

	@KafkaListener(topics = "booking.created.dlt")
	public void onBookingCreatedDlt(@Payload String payload,
		@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
		@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
		@Header(KafkaHeaders.OFFSET) long offset,
		@Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
		log.error("DLT message received. topic={}, partition={}, offset={}, key={}, payload={}",
			topic, partition, offset, key, payload);
	}

	@KafkaListener(topics = "booking.cancelled.dlt")
	public void onBookingCancelledDlt(@Payload String payload,
		@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
		@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
		@Header(KafkaHeaders.OFFSET) long offset,
		@Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
		log.error("DLT message received. topic={}, partition={}, offset={}, key={}, payload={}",
			topic, partition, offset, key, payload);
	}
}
