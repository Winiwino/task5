package com.example.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.VisitorRepository;
import com.example.restaurant.repository.VisitorRatingRepository;

import com.example.restaurant.mapper.VisitorRatingMapper;
import com.example.restaurant.mapper.RestaurantMapper;
import com.example.restaurant.mapper.VisitorMapper;

import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRatingResponseDTO;
import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.RestaurantResponseDTO;
import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.dto.VisitorResponseDTO;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.Visitor;
import com.example.restaurant.entity.VisitorRating;

@ExtendWith(MockitoExtension.class)
class VisitorRatingServiceTest {

    @Mock
    private VisitorRatingRepository ratingRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private VisitorRepository visitorRepository;
    @Mock
    private VisitorRatingMapper mapper;
    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private VisitorRatingService ratingService;

    @Test
    void create() {
        VisitorRatingRequestDTO dto =
                new VisitorRatingRequestDTO(1L, 2L, 5, "Great");

        Visitor visitor = new Visitor();
        visitor.setId(1L);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);

        VisitorRating rating = new VisitorRating();
        rating.setRating(5);

        when(visitorRepository.findById(1L))
                .thenReturn(Optional.of(visitor));
        when(restaurantRepository.findById(2L))
                .thenReturn(Optional.of(restaurant));
        when(mapper.toEntity(dto))
                .thenReturn(rating);
        when(mapper.toResponseDTO(rating))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 5, "Great"));
        when(ratingRepository.findAllByRestaurant(restaurant))
                .thenReturn(List.of(rating));

        VisitorRatingResponseDTO result = ratingService.create(dto);

        assertEquals(5, result.rating());
        verify(ratingRepository).save(rating);
        verify(restaurantService)
                .updateAverageRating(eq(2L), anyList());
    }

    @Test
    void create_whenVisitorNotFound() {
        VisitorRatingRequestDTO dto =
                new VisitorRatingRequestDTO(1L, 2L, 5, "Great");

        when(visitorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> ratingService.create(dto));
    }

    @Test
    void create_whenRestaurantNotFound() {
        VisitorRatingRequestDTO dto =
                new VisitorRatingRequestDTO(1L, 2L, 5, "Great");

        when(visitorRepository.findById(1L))
                .thenReturn(Optional.of(new Visitor()));
        when(restaurantRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> ratingService.create(dto));
    }

    @Test
    void update() {
        VisitorRatingRequestDTO dto =
                new VisitorRatingRequestDTO(1L, 2L, 4, "Ok");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);

        VisitorRating rating = new VisitorRating();
        rating.setRestaurant(restaurant);

        when(ratingRepository.findById(1L))
                .thenReturn(Optional.of(rating));
        when(mapper.toResponseDTO(rating))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 4, "Ok"));
        when(restaurantRepository.findById(2L))
                .thenReturn(Optional.of(restaurant));
        when(ratingRepository.findAllByRestaurant(restaurant))
                .thenReturn(List.of(rating));

        VisitorRatingResponseDTO result =
                ratingService.update(1L, dto);

        assertEquals(4, result.rating());
        verify(ratingRepository).save(rating);
    }

    @Test
    void getAll() {
        VisitorRating rating = new VisitorRating();
        when(ratingRepository.findAll())
                .thenReturn(List.of(rating));
        when(mapper.toResponseDTO(rating))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 5, "Good"));

        List<VisitorRatingResponseDTO> result =
                ratingService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById() {
        VisitorRating rating = new VisitorRating();

        when(ratingRepository.findById(1L))
                .thenReturn(Optional.of(rating));
        when(mapper.toResponseDTO(rating))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 5, "Good"));

        VisitorRatingResponseDTO result =
                ratingService.getById(1L);

        assertEquals(5, result.rating());
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(ratingRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> ratingService.getById(1L));
    }
    
    @Test
    void delete_shouldRemoveRating() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(2L);

        VisitorRating rating = new VisitorRating();
        rating.setRestaurant(restaurant);

        when(ratingRepository.findById(1L))
                .thenReturn(Optional.of(rating));
        when(restaurantRepository.findById(2L))
                .thenReturn(Optional.of(restaurant));
        when(ratingRepository.findAllByRestaurant(restaurant))
                .thenReturn(List.of());

        ratingService.delete(1L);

        verify(ratingRepository).delete(rating);
        verify(restaurantService)
                .updateAverageRating(eq(2L), anyList());
    }
}
