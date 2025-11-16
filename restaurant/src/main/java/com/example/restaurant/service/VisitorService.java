package com.example.restaurant.service;

import com.example.restaurant.entity.Visitor;
import com.example.restaurant.repository.VisitorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public void save(Visitor visitor) {

        if (visitor.getAge() <= 0) {
            throw new IllegalArgumentException("Возраст должен быть больше 0");
        }

        if (visitor.getGender() == null || visitor.getGender().isBlank()) {
            throw new IllegalArgumentException("Пол обязателен");
        }

        visitorRepository.save(visitor);
    }

    public void remove(Visitor visitor) {
        visitorRepository.remove(visitor);
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
}
