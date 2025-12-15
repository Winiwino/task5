package com.example.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.example.restaurant.repository.VisitorRepository;

import com.example.restaurant.mapper.VisitorMapper;

import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.dto.VisitorResponseDTO;
import com.example.restaurant.entity.Visitor;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper mapper;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void create() {
        VisitorRequestDTO dto = new VisitorRequestDTO("Anna", 20, "female");
        Visitor visitor = new Visitor(1L, "Anna", 20, "female", new ArrayList<>());
        VisitorResponseDTO responseDTO =
                new VisitorResponseDTO(1L, "Anna", 20, "female");

        when(mapper.toEntity(dto)).thenReturn(visitor);
        when(mapper.toResponseDTO(visitor)).thenReturn(responseDTO);
        when(visitorRepository.save(visitor)).thenReturn(visitor);

        VisitorResponseDTO result = visitorService.create(dto);

        assertEquals("Anna", result.name());
        verify(visitorRepository).save(visitor);
    }

    @Test
    void getById_whenNotFound() {
        when(visitorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> visitorService.getById(1L));
    }

    @Test
    void getById() {
    Visitor visitor = new Visitor(1L, "Anna", 20, "female", new ArrayList<>());
    VisitorResponseDTO response =
            new VisitorResponseDTO(1L, "Anna", 20, "female");

    when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
    when(mapper.toResponseDTO(visitor)).thenReturn(response);

    VisitorResponseDTO result = visitorService.getById(1L);

    assertEquals(1L, result.id());
    verify(visitorRepository).findById(1L);
}

    @Test
    void getAll() {
        Visitor visitor = new Visitor(1L, "Anna", 20, "female", new ArrayList<>());
        when(visitorRepository.findAll()).thenReturn(List.of(visitor));
        when(mapper.toResponseDTO(visitor))
                .thenReturn(new VisitorResponseDTO(1L, "Anna", 20, "female"));

        List<VisitorResponseDTO> result = visitorService.getAll();

        assertEquals(1, result.size());
        verify(visitorRepository).findAll();
    }

    @Test
    void update() {
        VisitorRequestDTO dto = new VisitorRequestDTO("Maria", 25, "female");
        Visitor visitor = new Visitor(1L, "Anna", 20, "female", new ArrayList<>());
        VisitorResponseDTO response =
                new VisitorResponseDTO(1L, "Maria", 25, "female");

        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitor));
        when(mapper.toResponseDTO(visitor)).thenReturn(response);

        VisitorResponseDTO result = visitorService.update(1L, dto);

        assertEquals("Maria", result.name());
        verify(visitorRepository).save(visitor);
    }
    @Test
    void delete() {
        visitorService.delete(1L);

        verify(visitorRepository).deleteById(1L);
    }
}
