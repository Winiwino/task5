package com.example.restaurant.controller;

import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.dto.VisitorResponseDTO;
import com.example.restaurant.service.VisitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping
    public List<VisitorResponseDTO> getAll() {
        return visitorService.getAll();
    }

    @GetMapping("/{id}")
    public VisitorResponseDTO getById(@PathVariable Long id) {
        return visitorService.getById(id);
    }

    @PostMapping
    public VisitorResponseDTO create(@RequestBody @Valid VisitorRequestDTO dto) {
        return visitorService.create(dto);
    }

    @PutMapping("/{id}")
    public VisitorResponseDTO update(@PathVariable Long id,
                                     @RequestBody @Valid VisitorRequestDTO dto) {
        return visitorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        visitorService.delete(id);
    }
}