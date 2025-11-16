package com.example.restaurant.repository;

import com.example.restaurant.entity.VisitorRating; 
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VisitorRatingRepository {

    private final List<VisitorRating> ratings = new ArrayList<>();

    public void save(VisitorRating rating) {
        ratings.add(rating);
    }

    public void remove(VisitorRating rating) {
        ratings.remove(rating);
    }

    public List<VisitorRating> findAll() {
        return new ArrayList<>(ratings);
    }

    public Optional<VisitorRating> findById(Long visitorId, Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId))
                .findFirst();
    }
}
