package com.example.restaurant;

import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.entity.CuisineType;
import com.example.restaurant.service.RestaurantService;
import com.example.restaurant.service.VisitorRatingService;
import com.example.restaurant.service.VisitorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestDataRunner implements CommandLineRunner {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final VisitorRatingService ratingService;

    public TestDataRunner(VisitorService visitorService,
                          RestaurantService restaurantService,
                          VisitorRatingService ratingService) {
        this.visitorService = visitorService;
        this.restaurantService = restaurantService;
        this.ratingService = ratingService;
    }

    @Override
    public void run(String... args) {

        // Посетители
        var v1 = visitorService.create(new VisitorRequestDTO(
                "Nastya", 25, "Женский"
        ));
        var v2 = visitorService.create(new VisitorRequestDTO(
                null, 30, "Мужской"
        ));

        // Рестораны
        var r1 = restaurantService.create(new RestaurantRequestDTO(
                "Антресоль", null,
                CuisineType.GEORGIAN,
                BigDecimal.valueOf(20)
        ));
        var r2 = restaurantService.create(new RestaurantRequestDTO(
                "Алазани", "Грузинская кухня",
                CuisineType.GEORGIAN,
                BigDecimal.valueOf(15)
        ));
        var r3 = restaurantService.create(new RestaurantRequestDTO(
                "Перчини", "Итальянская кухня",
                CuisineType.ITALIAN,
                BigDecimal.valueOf(15)
        ));

        // Оценки
        ratingService.create(new VisitorRatingRequestDTO(
                v1.id(), r1.id(), 5, null
        ));
        ratingService.create(new VisitorRatingRequestDTO(
                v2.id(), r1.id(), 4, "долго готовили"
        ));
        ratingService.create(new VisitorRatingRequestDTO(
                v1.id(), r2.id(), 3, "невкусно"
        ));

        // Просмотр результатов
        System.out.println("\nСредние оценки ресторанов:");
        restaurantService.getAll().forEach(r ->
                System.out.println(r.name() + ": " + r.ratingUser())
        );

        System.out.println("\nВсе посетители:");
        visitorService.getAll().forEach(System.out::println);

        System.out.println("\nВсе рестораны:");
        restaurantService.getAll().forEach(System.out::println);

        System.out.println("\nВсе оценки:");
        ratingService.getAll().forEach(System.out::println);

        // Удаление
        System.out.println("\nУдаляем данные");

        visitorService.delete(v2.id());
        restaurantService.delete(r3.id());

        // Проверка после удаления
        System.out.println("\nСредние оценки ресторанов после удаления:");
        restaurantService.getAll().forEach(r ->
                System.out.println(r.name() + ": " + r.ratingUser())
        );

        System.out.println("\nПосетители после удаления:");
        visitorService.getAll().forEach(System.out::println);

        System.out.println("\nРестораны после удаления:");
        restaurantService.getAll().forEach(System.out::println);
    }
}
