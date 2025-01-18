package com.leandropitta.budget_manager.controller;

import com.leandropitta.budget_manager.dto.request.CostRequestDto;
import com.leandropitta.budget_manager.dto.response.CostResponseDto;
import com.leandropitta.budget_manager.dto.response.CostsResponseDto;
import com.leandropitta.budget_manager.dto.response.GiftResponseDto;
import com.leandropitta.budget_manager.service.CostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CostService costService;

    @InjectMocks
    private CostController costController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(costController).build();
    }

    @Test
    void testGetCosts() throws Exception {
        CostsResponseDto costsResponseDto = new CostsResponseDto();
        when(costService.getCosts()).thenReturn(costsResponseDto);

        mockMvc.perform(get("/cost"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(costService, times(1)).getCosts();
    }

    @Test
    void testCalculateGift() throws Exception {
        GiftResponseDto giftResponseDto = new GiftResponseDto();
        when(costService.calculateGift()).thenReturn(giftResponseDto);

        mockMvc.perform(get("/cost/gift"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(costService, times(1)).calculateGift();
    }

    @Test
    void testCreateCost() throws Exception {
        CostRequestDto costRequestDto = new CostRequestDto();
        CostResponseDto costResponseDto = new CostResponseDto();
        when(costService.createCost(any(CostRequestDto.class))).thenReturn(costResponseDto);

        mockMvc.perform(post("/cost")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Cost\",\"amount\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(costService, times(1)).createCost(any(CostRequestDto.class));
    }

    @Test
    void testUpdateCost() throws Exception {
        CostRequestDto costRequestDto = new CostRequestDto();
        CostResponseDto costResponseDto = new CostResponseDto();
        when(costService.updateCost(anyLong(), any(CostRequestDto.class))).thenReturn(costResponseDto);

        mockMvc.perform(put("/cost/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Cost\",\"amount\":150.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());

        verify(costService, times(1)).updateCost(anyLong(), any(CostRequestDto.class));
    }

    @Test
    void testDeleteCost() throws Exception {
        doNothing().when(costService).deleteCost(anyLong());

        mockMvc.perform(delete("/cost/1"))
                .andExpect(status().isNoContent());

        verify(costService, times(1)).deleteCost(anyLong());
    }
}