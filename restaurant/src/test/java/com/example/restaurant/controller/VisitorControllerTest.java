package com.example.restaurant.controller;

import com.example.restaurant.controller.VisitorController;
import com.example.restaurant.dto.VisitorRequestDTO;
import com.example.restaurant.dto.VisitorResponseDTO;
import com.example.restaurant.service.VisitorService;
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


@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitorService visitorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        when(visitorService.getAll())
                .thenReturn(List.of(
                        new VisitorResponseDTO(1L, "Anna", 20, "female")
                ));

        mockMvc.perform(get("/api/visitors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Anna"));
    }

    @Test
    void getById() throws Exception {
        when(visitorService.getById(1L))
                .thenReturn(new VisitorResponseDTO(1L, "Anna", 20, "female"));

        mockMvc.perform(get("/api/visitors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna"));
    }

    @Test
    void create() throws Exception {
        VisitorRequestDTO dto = new VisitorRequestDTO("Anna", 20, "female");

        when(visitorService.create(any()))
                .thenReturn(new VisitorResponseDTO(1L, "Anna", 20, "female"));

        mockMvc.perform(post("/api/visitors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void update() throws Exception {
        VisitorRequestDTO dto = new VisitorRequestDTO("Kate", 22, "female");

        when(visitorService.update(eq(1L), any()))
                .thenReturn(new VisitorResponseDTO(1L, "Kate", 22, "female"));

        mockMvc.perform(put("/api/visitors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kate"));
    }

    @Test
    void remove() throws Exception {
        mockMvc.perform(delete("/api/visitors/1"))
                .andExpect(status().isOk());

        verify(visitorService).delete(1L);
    }
}