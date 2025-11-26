package com.example.restaurant.mapper;

import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.RestaurantResponseDTO;
import com.example.restaurant.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)

public interface RestaurantMapper {

    Restaurant toEntity(RestaurantRequestDTO dto);

    RestaurantResponseDTO toResponseDTO(Restaurant restaurant);
}
