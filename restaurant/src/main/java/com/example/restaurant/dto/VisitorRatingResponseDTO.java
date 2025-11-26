package com.example.restaurant.dto;

public record VisitorRatingResponseDTO(
    Long visitorId,
    Long restaurantId,
    int rating,
    String comment
) {}
