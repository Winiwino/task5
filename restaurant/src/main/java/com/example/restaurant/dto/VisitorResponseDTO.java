package com.example.restaurant.dto;

public record VisitorResponseDTO(
    Long id,
    String name,
    int age,
    String gender
) {}