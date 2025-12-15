package com.example.restaurant.controller;
import com.example.restaurant.controller.VisitorRatingController;
import com.example.restaurant.dto.VisitorRatingRequestDTO;
import com.example.restaurant.dto.VisitorRatingResponseDTO;
import com.example.restaurant.service.VisitorRatingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitorRatingController.class)
class VisitorRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitorRatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        when(ratingService.getAll())
                .thenReturn(List.of(
                        new VisitorRatingResponseDTO(1L, 2L, 5, "Great")
                ));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rating").value(5));
    }

    @Test
    void getById() throws Exception {
        when(ratingService.getById(1L))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 5, "Great"));

        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void create() throws Exception {
        VisitorRatingRequestDTO dto =
                new VisitorRatingRequestDTO(1L, 2L, 5, "Great");

        when(ratingService.create(any()))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 5, "Great"));

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Great"));
    }

    @Test
    void update() throws Exception {
        VisitorRatingRequestDTO dto =
                new VisitorRatingRequestDTO(1L, 2L, 4, "Good");

        when(ratingService.update(eq(1L), any()))
                .thenReturn(new VisitorRatingResponseDTO(1L, 2L, 4, "Good"));

        mockMvc.perform(put("/api/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(4));
    }

    @Test
    void remove() throws Exception {
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isOk());

        verify(ratingService).delete(1L);
    }
}