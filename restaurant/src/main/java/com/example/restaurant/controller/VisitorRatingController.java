package com.example.restaurant.controller;

import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRatingResponseDTO;
import com.example.restaurant.service.VisitorRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class VisitorRatingController {

    private final VisitorRatingService ratingService;

    @GetMapping
    public List<VisitorRatingResponseDTO> getAll() {
        return ratingService.getAll();
    }

    @GetMapping("/{id}")
    public VisitorRatingResponseDTO getById(@PathVariable Long id) {
        return ratingService.getById(id);
    }

    @PostMapping
    public VisitorRatingResponseDTO create(@RequestBody @Valid VisitorRatingRequestDTO dto) {
        return ratingService.create(dto);
    }

    @PutMapping("/{id}")
    public VisitorRatingResponseDTO update(@PathVariable Long id,
                                           @RequestBody VisitorRatingRequestDTO dto) {
        return ratingService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ratingService.delete(id);
    }
}