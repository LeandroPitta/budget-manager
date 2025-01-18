package com.leandropitta.budget_manager.controller;

import com.leandropitta.budget_manager.dto.request.RegisterRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.*;
import com.leandropitta.budget_manager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegister() throws Exception {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();
        mockMvc.perform(put("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"newpassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllBackgroundColors() throws Exception {
        BackgroundColorsResponseDto responseDto = new BackgroundColorsResponseDto();
        when(userService.getAllBackgroundColors()).thenReturn(responseDto);
        mockMvc.perform(get("/user/background-colors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllBackgroundGifs() throws Exception {
        BackgroundGifsResponseDto responseDto = new BackgroundGifsResponseDto();
        when(userService.getAllBackgroundGifs()).thenReturn(responseDto);
        mockMvc.perform(get("/user/background-gifs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllBudgetGifs() throws Exception {
        BudgetGifsResponseDto responseDto = new BudgetGifsResponseDto();
        when(userService.getAllBudgetsGif()).thenReturn(responseDto);
        mockMvc.perform(get("/user/budget-gifs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllFontFamilies() throws Exception {
        FontFamiliesResponseDto responseDto = new FontFamiliesResponseDto();
        when(userService.getAllFontFamilies()).thenReturn(responseDto);
        mockMvc.perform(get("/user/font-families"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetAllTitleColors() throws Exception {
        TitleColorsResponseDto responseDto = new TitleColorsResponseDto();
        when(userService.getAllTitleColors()).thenReturn(responseDto);
        mockMvc.perform(get("/user/title-colors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}