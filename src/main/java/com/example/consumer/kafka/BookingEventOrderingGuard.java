package com.example.consumer.kafka;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;

@Component
public class BookingEventOrderingGuard {

	private final ConcurrentMap<Long, Long> lastTimestampByBookingId = new ConcurrentHashMap<>();

	public void checkAndUpdate(long bookingId, long eventTimestamp) {
		lastTimestampByBookingId.compute(bookingId, (id, lastTimestamp) -> {
			if (lastTimestamp != null && eventTimestamp <= lastTimestamp) {
				throw new MonotonicityViolationException(bookingId, eventTimestamp, lastTimestamp);
			}
			return eventTimestamp;
		});
	}
}
