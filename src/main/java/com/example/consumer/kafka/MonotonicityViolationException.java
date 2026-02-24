package com.example.consumer.kafka;

public class MonotonicityViolationException extends RuntimeException {

	public MonotonicityViolationException(long bookingId, long eventTimestamp, long lastTimestamp) {
		super("Non-monotonic eventTimestamp for bookingId=" + bookingId
			+ " (eventTimestamp=" + eventTimestamp
			+ ", lastTimestamp=" + lastTimestamp + ")");
	}
}
