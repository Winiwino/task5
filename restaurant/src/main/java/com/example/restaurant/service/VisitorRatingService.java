package com.example.restaurant.service;

import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRatingResponseDTO;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.VisitorRating;
import com.example.restaurant.mapper.VisitorRatingMapper;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.VisitorRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorRatingService {

    private final VisitorRatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final VisitorRatingMapper mapper;
    private final RestaurantService restaurantService;

    public VisitorRatingResponseDTO create(VisitorRatingRequestDTO dto) {
        VisitorRating rating = mapper.toEntity(dto);
        ratingRepository.save(rating); 
        updateRestaurantAverageRating(dto.restaurantId());
        return mapper.toResponseDTO(rating);
    }


    public VisitorRatingResponseDTO update(Long visitorId, Long restaurantId, VisitorRatingRequestDTO dto) {
        VisitorRating rating = ratingRepository.findById(visitorId, restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "RРейтинг не найден для visitorId=" + visitorId + " и restaurantId=" + restaurantId
                ));

        rating.setRating(dto.rating());
        rating.setComment(dto.comment());

        ratingRepository.save(rating);
        updateRestaurantAverageRating(rating.getRestaurantId());

        return mapper.toResponseDTO(rating);
    }

    public List<VisitorRatingResponseDTO> getAll() {
        return ratingRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public VisitorRatingResponseDTO getById(Long visitorId, Long restaurantId) {
        VisitorRating rating = ratingRepository.findById(visitorId, restaurantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Рейтинг не найден для visitorId=" + visitorId + " и restaurantId=" + restaurantId
                ));
        return mapper.toResponseDTO(rating);
    }

    public void delete(Long visitorId, Long restaurantId) {
        ratingRepository.remove(visitorId, restaurantId);
        updateRestaurantAverageRating(restaurantId);
    }

    private void updateRestaurantAverageRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId);

        List<Integer> ratings = ratingRepository.findAll().stream()
                .filter(r -> r.getRestaurantId().equals(restaurantId))
                .map(VisitorRating::getRating)
                .toList();

        restaurantService.updateAverageRating(restaurantId, ratings);
    }
}