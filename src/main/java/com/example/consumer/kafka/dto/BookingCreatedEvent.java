package com.example.consumer.kafka.dto;

public record BookingCreatedEvent(long movieId, long userId) {
}
