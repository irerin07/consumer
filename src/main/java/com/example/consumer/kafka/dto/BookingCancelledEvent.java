package com.example.consumer.kafka.dto;

public record BookingCancelledEvent(long bookingId, long eventTimestamp) {
}
