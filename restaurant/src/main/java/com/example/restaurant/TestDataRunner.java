package com.example.restaurant;

import com.example.restaurant.entity.*;
import com.example.restaurant.service.*;
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

        // посетители
        Visitor v1 = new Visitor(1L, "Nastya", 25, "Женский");
        Visitor v2 = new Visitor(2L, null, 30, "Мужской"); // анонимный
        visitorService.save(v1);
        visitorService.save(v2);

        // рестораны
        Restaurant r1 = new Restaurant(1L, "Антресоль", "", CuisineType.GEORGIAN,
            BigDecimal.valueOf(20), BigDecimal.ZERO); // без описания
        Restaurant r2 = new Restaurant(2L, "Алазани", "Грузинская кухня", CuisineType.GEORGIAN,
            BigDecimal.valueOf(15), BigDecimal.ZERO);
        Restaurant r3 = new Restaurant(3L, "Перчини", "Итальянская кухня", CuisineType.ITALIAN,
            BigDecimal.valueOf(15), BigDecimal.ZERO);
        restaurantService.save(r1);
        restaurantService.save(r2);
        restaurantService.save(r3);

        // оценки
        VisitorRating rating1 = new VisitorRating(v1.getId(), r1.getId(), 5, ""); // без комментария
        VisitorRating rating2 = new VisitorRating(v2.getId(), r1.getId(), 4, "долго готовили");
        VisitorRating rating3 = new VisitorRating(v1.getId(), r2.getId(), 3,"невкусно"); 

        ratingService.save(rating1, r1);
        ratingService.save(rating2, r1);
        ratingService.save(rating3, r2);


        System.out.println("\nсредние оценки ресторанов");
        restaurantService.findAll().forEach(r ->
            System.out.println(r.getName() + " - средняя оценка: " + r.getRatingUser()));
    
        System.out.println("\nвсе посетители");
        visitorService.findAll().forEach(v ->
                System.out.println("ID: " + v.getId() + ", имя: " + v.getName() +
                        ", возраст: " + v.getAge() + ", пол: " + v.getGender()));
    
        System.out.println("\n все рестораны");
        restaurantService.findAll().forEach(r ->
            System.out.println("ID: " + r.getId() + ", название: " + r.getName() +
                    ", описание: '" + r.getDescription() + "'" +
                    ", тип: " + r.getCuisineType() +
                    ", средний чек: " + r.getAverageCheck() +
                    ", оценка: " + r.getRatingUser()));

        System.out.println("\nвсе оценки");
        ratingService.findAll().forEach(rating ->
                System.out.println("VisitorId: " + rating.getVisitorId() +
                        ", restaurantId: " + rating.getRestaurantId() +
                        ", рейтинг: " + rating.getRating() +
                        ", комментарий: " + rating.getComment()));
        
        System.out.println("\nУДАЛЯЕМ ДАННЫЕ");

        visitorService.remove(v2);    
        restaurantService.remove(r3);
        ratingService.remove(rating2, r1); 


        System.out.println("\nсредние оценки ресторанов после удаления оценки");
        
        restaurantService.findAll().forEach(r ->
            System.out.println(r.getName() + " - средняя оценка: " + r.getRatingUser()));
    
        System.out.println("\nпосетители после удаления");
        visitorService.findAll().forEach(v ->
                System.out.println("ID: " + v.getId() + ", имя: " + v.getName() +
                        ", возраст: " + v.getAge() + ", пол: " + v.getGender()));
    
        System.out.println("\nрестораны после удаления");
        restaurantService.findAll().forEach(r ->
            System.out.println("ID: " + r.getId() + ", название: " + r.getName() +
                    ", описание: '" + r.getDescription() + "'" +
                    ", тип: " + r.getCuisineType() +
                    ", средний чек: " + r.getAverageCheck() +
                    ", оценка: " + r.getRatingUser()));

        System.out.println("\nоценки после удаления");
        ratingService.findAll().forEach(rating ->
                System.out.println("VisitorId: " + rating.getVisitorId() +
                        ", restaurantId: " + rating.getRestaurantId() +
                        ", рейтинг: " + rating.getRating() +
                        ", комментарий: " + rating.getComment()));

        
     }

        
}
