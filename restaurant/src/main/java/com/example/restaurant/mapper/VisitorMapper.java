package com.example.restaurant.mapper;

import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.dto.VisitorResponseDTO;
import com.example.restaurant.entity.Visitor;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")

public interface VisitorMapper {

    Visitor toEntity(VisitorRequestDTO dto);

    VisitorResponseDTO toResponseDTO(Visitor visitor);
}
