package com.example.restaurant.mapper;

import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRatingResponseDTO;
import com.example.restaurant.entity.VisitorRating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitorRatingMapper {

    VisitorRating toEntity(VisitorRatingRequestDTO dto);

    VisitorRatingResponseDTO toResponseDTO(VisitorRating rating);
}