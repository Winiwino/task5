package com.example.restaurant.service;

import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.RestaurantResponseDTO;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.mapper.RestaurantMapper;
import com.example.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant restaurant = mapper.toEntity(dto);
        restaurantRepository.save(restaurant);
        return mapper.toResponseDTO(restaurant);
    }

    public RestaurantResponseDTO update(Long id, RestaurantRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден: " + id));

        restaurant.setName(dto.name());
        restaurant.setDescription(dto.description());
        restaurant.setCuisineType(dto.cuisineType());
        restaurant.setAverageCheck(dto.averageCheck());

        restaurantRepository.save(restaurant);
        return mapper.toResponseDTO(restaurant);
    }

    public List<RestaurantResponseDTO> getAll() {
        return restaurantRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public RestaurantResponseDTO getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден: " + id));
        return mapper.toResponseDTO(restaurant);
    }

    public void delete(Long id) {
        restaurantRepository.deleteById(id);
    }

    public void updateAverageRating(Long restaurantId, List<Integer> ratings) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден: " + restaurantId));

        double avg = ratings.isEmpty() ? 0 : ratings.stream().mapToInt(i -> i).average().orElse(0);
        restaurant.setRatingUser(avg == 0 ? null : BigDecimal.valueOf(avg));

        restaurantRepository.save(restaurant);
    }
}

