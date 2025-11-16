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

        if (restaurant.getAverageCheck() == null ||
            restaurant.getAverageCheck().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException("Средний чек должен быть больше 0");
        }

        if (restaurant.getName() == null || restaurant.getName().isBlank()) {
            throw new IllegalArgumentException("Название ресторана не может быть пустым");
        }

        if (restaurant.getCuisineType() == null) {
            throw new IllegalArgumentException("Тип кухни обязателен");
        }

        

        if (restaurant.getDescription() == null) {
            restaurant.setDescription("");
        }
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