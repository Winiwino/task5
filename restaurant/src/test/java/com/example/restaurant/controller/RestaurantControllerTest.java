package com.example.restaurant.controller;

import com.example.restaurant.controller.RestaurantController;
import com.example.restaurant.dto.RestaurantRequestDTO;
import com.example.restaurant.dto.RestaurantResponseDTO;
import com.example.restaurant.entity.CuisineType;
import com.example.restaurant.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        when(restaurantService.getAll())
                .thenReturn(List.of(
                        new RestaurantResponseDTO(1L, "Cafe", "Nice",
                                CuisineType.ITALIAN, BigDecimal.valueOf(100), null)
                ));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cafe"));
    }

    @Test
    void getById() throws Exception {
        when(restaurantService.getById(1L))
                .thenReturn(new RestaurantResponseDTO(1L, "Cafe", "Nice",
                        CuisineType.ITALIAN, BigDecimal.valueOf(100), null));

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cafe"));
    }

    @Test
    void create() throws Exception {
        RestaurantRequestDTO dto =
                new RestaurantRequestDTO("Cafe", "Nice",
                        CuisineType.ITALIAN, BigDecimal.valueOf(100));

        when(restaurantService.create(any()))
                .thenReturn(new RestaurantResponseDTO(1L, "Cafe", "Nice",
                        CuisineType.ITALIAN, BigDecimal.valueOf(100), null));

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update() throws Exception {
        RestaurantRequestDTO dto =
                new RestaurantRequestDTO("New Cafe", "Updated",
                        CuisineType.ITALIAN, BigDecimal.valueOf(200));

        when(restaurantService.update(eq(1L), any()))
                .thenReturn(new RestaurantResponseDTO(1L, "New Cafe", "Updated",
                        CuisineType.ITALIAN, BigDecimal.valueOf(200), null));

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Cafe"));
    }

    @Test
    void remove() throws Exception {
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());

        verify(restaurantService).delete(1L);
    }
}
