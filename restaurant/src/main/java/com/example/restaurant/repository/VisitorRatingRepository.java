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
        
        ratings.removeIf(r -> r.getVisitorId().equals(rating.getVisitorId())
                            && r.getRestaurantId().equals(rating.getRestaurantId()));
        ratings.add(rating);
    }

    public void remove(Long visitorId, Long restaurantId) {
        ratings.removeIf(r -> r.getVisitorId().equals(visitorId)
                            && r.getRestaurantId().equals(restaurantId));
    }

    public List<VisitorRating> findAll() {
        return new ArrayList<>(ratings);
    }

    public Optional<VisitorRating> findById(Long visitorId, Long restaurantId) {
        return ratings.stream()
                .filter(r -> r.getVisitorId().equals(visitorId)
                          && r.getRestaurantId().equals(restaurantId))
                .findFirst();
    }
}
