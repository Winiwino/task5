package com.example.restaurant.entity;

import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
@Table(name = "visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name; // можно оставить null
    private int age;
    private String gender;

    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitorRating> ratings = new ArrayList<>();
}
