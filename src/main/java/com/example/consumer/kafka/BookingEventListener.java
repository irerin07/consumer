package com.example.consumer.kafka;

import com.example.consumer.kafka.dto.BookingCancelledEvent;
import com.example.consumer.kafka.dto.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventListener {

	private static final Logger log = LoggerFactory.getLogger(BookingEventListener.class);

	@KafkaListener(topics = {
		"${app.kafka.topics.booking-created}"
	})
	public void onBookingCreated(@Payload BookingCreatedEvent event,
		@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("Booking created. topic={}, event={}", topic, event);
	}

	@KafkaListener(topics = {
		"${app.kafka.topics.booking-cancelled}"
	})
	@RetryableTopic(
		attempts = "7",
		backOff = @BackOff(
			delay = 1000,
			multiplier = 2
		)
	)
	public void onBookingCancelled(@Payload BookingCancelledEvent event,
		@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		log.info("Booking cancelled. topic={}, event={}", topic, event);
	}
}
