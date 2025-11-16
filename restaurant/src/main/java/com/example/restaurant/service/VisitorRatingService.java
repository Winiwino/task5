package com.example.restaurant.service;

import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.VisitorRating;
import com.example.restaurant.repository.VisitorRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitorRatingService {

    private final VisitorRatingRepository ratingRepository;
    private final RestaurantService restaurantService;

    public void save(VisitorRating rating, Restaurant restaurant) {
        ratingRepository.save(rating);

        List<Integer> restaurantRatings = ratingRepository.findAll().stream()
                .filter(r -> r.getRestaurantId().equals(restaurant.getId()))
                .map(VisitorRating::getRating)
                .collect(Collectors.toList());

        restaurantService.updateAverageRating(restaurant, restaurantRatings);
    }

    public void remove(VisitorRating rating, Restaurant restaurant) {
        ratingRepository.remove(rating);

        List<Integer> restaurantRatings = ratingRepository.findAll().stream()
                .filter(r -> r.getRestaurantId().equals(restaurant.getId()))
                .map(VisitorRating::getRating)
                .collect(Collectors.toList());

        restaurantService.updateAverageRating(restaurant, restaurantRatings);
    }

    public List<VisitorRating> findAll() {
        return ratingRepository.findAll();
    }
}