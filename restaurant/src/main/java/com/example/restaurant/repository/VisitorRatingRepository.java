package com.example.restaurant.repository;

import com.example.restaurant.entity.VisitorRating;
import com.example.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisitorRatingRepository extends JpaRepository<VisitorRating, Long> {
    Optional<VisitorRating> findByVisitorIdAndRestaurantId(Long visitorId, Long restaurantId);

    void deleteAllByVisitorId(Long visitorId);
    List<VisitorRating> findAllByRestaurant(Restaurant restaurant);
}


