package com.example.restaurant.repository;

import com.example.restaurant.entity.Restaurant;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepository {

    private final List<Restaurant> restaurants = new ArrayList<>();
    private long nextId = 1;

    public void save(Restaurant restaurant) {
        
        if (restaurant.getId() == null) {
            restaurant.setId(nextId++);
        }

        restaurants.removeIf(r -> r.getId().equals(restaurant.getId()));
        restaurants.add(restaurant);
    }

    public void remove(Long id) {
        restaurants.removeIf(r -> r.getId().equals(id));
    }

    public List<Restaurant> findAll() {
        return new ArrayList<>(restaurants);
    }

    public Restaurant findById(Long id) {
        return restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Ресторан с Id: " + id + " не найден"));
    }
}
