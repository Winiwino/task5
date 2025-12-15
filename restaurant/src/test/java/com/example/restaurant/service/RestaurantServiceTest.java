package com.example.restaurant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.mapper.RestaurantMapper;
import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.RestaurantResponseDTO;

import com.example.restaurant.entity.CuisineType;
import com.example.restaurant.entity.Restaurant;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper mapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void create() {
        RestaurantRequestDTO dto =
                new RestaurantRequestDTO("Cafe", "Nice", CuisineType.ITALIAN, BigDecimal.valueOf(100));

        Restaurant restaurant = new Restaurant();
        RestaurantResponseDTO responseDTO =
                new RestaurantResponseDTO(1L, "Cafe", "Nice",
                        CuisineType.ITALIAN, BigDecimal.valueOf(100), null);

        when(mapper.toEntity(dto)).thenReturn(restaurant);
        when(mapper.toResponseDTO(restaurant)).thenReturn(responseDTO);

        RestaurantResponseDTO result = restaurantService.create(dto);

        assertEquals("Cafe", result.name());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void updateAverageRating() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        restaurantService.updateAverageRating(1L, List.of(5, 4, 3));

        assertEquals(BigDecimal.valueOf(4.0), restaurant.getRatingUser());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void update() {
        RestaurantRequestDTO dto =
                new RestaurantRequestDTO("New", "Desc", CuisineType.JAPANESE, BigDecimal.valueOf(200));

        Restaurant restaurant = new Restaurant();
        RestaurantResponseDTO response =
                new RestaurantResponseDTO(1L, "New", "Desc",
                        CuisineType.JAPANESE, BigDecimal.valueOf(200), null);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mapper.toResponseDTO(restaurant)).thenReturn(response);

        RestaurantResponseDTO result = restaurantService.update(1L, dto);

        assertEquals("New", result.name());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void getAll() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(mapper.toResponseDTO(restaurant))
                .thenReturn(new RestaurantResponseDTO(1L, "Cafe", null, null, null, null));

        List<RestaurantResponseDTO> result = restaurantService.getAll();

        assertEquals(1, result.size());
        verify(restaurantRepository).findAll();
}

    @Test
    void getById() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(mapper.toResponseDTO(restaurant))
                .thenReturn(new RestaurantResponseDTO(1L, "Cafe", null, null, null, null));

        RestaurantResponseDTO result = restaurantService.getById(1L);

        assertEquals(1L, result.id());
}

    @Test
    void getById_whenNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.getById(1L));
    }
    @Test
    void delete() {
        restaurantService.delete(1L);

        verify(restaurantRepository).deleteById(1L);
    }

    @Test
    void updateAverageRating_whenRestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> restaurantService.updateAverageRating(1L, List.of(5)));
    }
}