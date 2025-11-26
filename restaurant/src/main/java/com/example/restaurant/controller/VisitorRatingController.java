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

    @GetMapping("/{visitorId}/{restaurantId}")
    public VisitorRatingResponseDTO getById(@PathVariable Long visitorId,
                                            @PathVariable Long restaurantId) {
        return ratingService.getById(visitorId, restaurantId);
    }

    @PostMapping
    public VisitorRatingResponseDTO create(@RequestBody @Valid VisitorRatingRequestDTO dto) {
        return ratingService.create(dto);
    }

    @PutMapping("/{visitorId}/{restaurantId}")
    public VisitorRatingResponseDTO update(@PathVariable Long visitorId,
                                           @PathVariable Long restaurantId,
                                           @RequestBody @Valid VisitorRatingRequestDTO dto) {
        return ratingService.update(visitorId, restaurantId, dto);
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    public void delete(@PathVariable Long visitorId,
                       @PathVariable Long restaurantId) {
        ratingService.delete(visitorId, restaurantId);
    }
}