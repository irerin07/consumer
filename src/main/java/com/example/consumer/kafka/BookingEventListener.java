package com.example.consumer.kafka;

import com.example.consumer.kafka.dto.BookingCancelledEvent;
import com.example.consumer.kafka.dto.BookingCreatedEvent;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventListener {

	private static final Logger log = LoggerFactory.getLogger(BookingEventListener.class);
	private final ObjectMapper objectMapper;

	@KafkaListener(topics = {
		"${app.kafka.topics.booking-created}"
	})
	public void onBookingCreated(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		try {
			BookingCreatedEvent event = objectMapper.readValue(payload, BookingCreatedEvent.class);
			log.info("Booking created. topic={}, event={}", topic, event);
		} catch (JacksonException ex) {
			log.warn("Failed to parse booking created payload. topic={}, payload={}", topic, payload, ex);
		}
	}

	@KafkaListener(topics = {
		"${app.kafka.topics.booking-cancelled}"
	})
	public void onBookingCancelled(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		try {
			BookingCancelledEvent event = objectMapper.readValue(payload, BookingCancelledEvent.class);
			log.info("Booking cancelled. topic={}, event={}", topic, event);
		} catch (JacksonException ex) {
			log.warn("Failed to parse booking cancelled payload. topic={}, payload={}", topic, payload, ex);
		}
	}
}
