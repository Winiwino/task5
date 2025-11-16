package com.example.restaurant.service;

import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurantRepository.remove(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public void updateAverageRating(Restaurant restaurant, List<Integer> ratings) {
        if (ratings.isEmpty()) {
            restaurant.setRatingUser(BigDecimal.ZERO);
        } else {
            double avg = ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            restaurant.setRatingUser(BigDecimal.valueOf(avg));
        }
    }
}