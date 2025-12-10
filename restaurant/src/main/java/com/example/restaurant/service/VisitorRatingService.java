package com.example.restaurant.service;

import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRatingResponseDTO;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.Visitor;
import com.example.restaurant.entity.VisitorRating;
import com.example.restaurant.mapper.VisitorRatingMapper;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.VisitorRatingRepository;
import com.example.restaurant.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitorRatingService {

    private final VisitorRatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final VisitorRepository visitorRepository;
    private final VisitorRatingMapper mapper;
    private final RestaurantService restaurantService;

    public VisitorRatingResponseDTO create(VisitorRatingRequestDTO dto) {
    
        Visitor visitor = visitorRepository.findById(dto.visitorId())
                .orElseThrow(() -> new IllegalArgumentException("Посетитель не найден: " + dto.visitorId()));
        Restaurant restaurant = restaurantRepository.findById(dto.restaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден: " + dto.restaurantId()));

        VisitorRating rating = mapper.toEntity(dto);
        rating.setVisitor(visitor);
        rating.setRestaurant(restaurant);

       
        ratingRepository.save(rating);

        updateRestaurantAverageRating(restaurant.getId());

        return mapper.toResponseDTO(rating);
    }

    public VisitorRatingResponseDTO update(Long ratingId, VisitorRatingRequestDTO dto) {
        VisitorRating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new IllegalArgumentException("Рейтинг не найден: " + ratingId));

        rating.setRating(dto.rating());
        rating.setComment(dto.comment());

        ratingRepository.save(rating);
        updateRestaurantAverageRating(rating.getRestaurant().getId());

        return mapper.toResponseDTO(rating);
    }

    public List<VisitorRatingResponseDTO> getAll() {
        return ratingRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public VisitorRatingResponseDTO getById(Long ratingId) {
        VisitorRating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new IllegalArgumentException("Рейтинг не найден: " + ratingId));
        return mapper.toResponseDTO(rating);
    }

    public void delete(Long ratingId) {
        VisitorRating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new IllegalArgumentException("Рейтинг не найден: " + ratingId));

        Long restaurantId = rating.getRestaurant().getId();
        ratingRepository.delete(rating);
        updateRestaurantAverageRating(restaurantId);
    }

    private void updateRestaurantAverageRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан не найден: " + restaurantId));

        List<Integer> ratings = ratingRepository.findAllByRestaurant(restaurant)
                .stream()
                .map(VisitorRating::getRating)
                .collect(Collectors.toList());

        restaurantService.updateAverageRating(restaurantId, ratings);
    }
}
