package com.example.restaurant.repository;

import com.example.restaurant.entity.Visitor;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class VisitorRepository {

    private final List<Visitor> visitors = new ArrayList<>();
    private long nextId = 1;

    public void save(Visitor visitor) {

        if (visitor.getId() == null) {
            visitor.setId(nextId++);
        }

        visitors.removeIf(v -> v.getId().equals(visitor.getId()));
        visitors.add(visitor);
    }

    public void remove(Long id) {
        visitors.removeIf(v -> v.getId().equals(id));
    }

    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }

    public Visitor findById(Long id) {
        return visitors.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Пользователь с id: " + id + " не найден"));
    }
}
