package com.example.consumer.kafka.dto;

public record BookingCreatedEvent(long bookingId, long movieId, long userId, long eventTimestamp) {
}
