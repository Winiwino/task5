package com.example.restaurant.dto;

import com.example.restaurant.entity.CuisineType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RestaurantRequestDTO(
    @NotBlank(message = "Название ресторана обязательно")
    String name,
    
    String description, 
    
    @NotNull(message = "Тип кухни обязателен")
    CuisineType cuisineType,
    
    @NotNull(message = "Средний чек обязателен")
    @DecimalMin(value = "0.01", message = "Средний чек должен быть больше 0")
    BigDecimal averageCheck
) {}
