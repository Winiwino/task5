package com.example.restaurant.service;

import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.dto.VisitorResponseDTO;
import com.example.restaurant.entity.Visitor;
import com.example.restaurant.mapper.VisitorMapper;
import com.example.restaurant.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper mapper;

    public VisitorResponseDTO create(VisitorRequestDTO dto) {

        Visitor visitor = mapper.toEntity(dto);

        visitorRepository.save(visitor);

        return mapper.toResponseDTO(visitor);
    }

    public VisitorResponseDTO update(Long id, VisitorRequestDTO dto) {

        Visitor visitor = visitorRepository.findById(id);

        visitor.setName(dto.name());
        visitor.setAge(dto.age());
        visitor.setGender(dto.gender());

        visitorRepository.save(visitor);

        return mapper.toResponseDTO(visitor);
    }

    public List<VisitorResponseDTO> getAll() {
        return visitorRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public VisitorResponseDTO getById(Long id) {
        return mapper.toResponseDTO(
                visitorRepository.findById(id)
        );
    }

    public void delete(Long id) {
        visitorRepository.remove(id);
    }
}