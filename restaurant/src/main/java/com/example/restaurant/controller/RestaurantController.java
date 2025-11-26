package com.example.restaurant.controller;

import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.RestaurantResponseDTO;
import com.example.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantResponseDTO> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getById(@PathVariable Long id) {
        return restaurantService.getById(id);
    }

    @PostMapping
    public RestaurantResponseDTO create(@RequestBody @Valid RestaurantRequestDTO dto) {
        return restaurantService.create(dto);
    }

    @PutMapping("/{id}")
    public RestaurantResponseDTO update(@PathVariable Long id,
                                        @RequestBody @Valid RestaurantRequestDTO dto) {
        return restaurantService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.delete(id);
    }
}