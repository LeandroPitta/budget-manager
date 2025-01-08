package com.leandropitta.budget_manager.controller;

import com.leandropitta.budget_manager.dto.request.RegisterRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.BackgroundColorsResponseDto;
import com.leandropitta.budget_manager.dto.response.BackgroundGifsResponseDto;
import com.leandropitta.budget_manager.dto.response.BudgetGifsResponseDto;
import com.leandropitta.budget_manager.dto.response.FontFamiliesResponseDto;
import com.leandropitta.budget_manager.dto.response.TitleColorsResponseDto;
import com.leandropitta.budget_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        userService.register(registerRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        userService.updateUser(updateUserRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/background-colors")
    public ResponseEntity<BackgroundColorsResponseDto> getAllBackgroundColors() {
        return ResponseEntity.ok(userService.getAllBackgroundColors());
    }

    @GetMapping("/background-gifs")
    public ResponseEntity<BackgroundGifsResponseDto> getAllBackgroundGifs() {
        return ResponseEntity.ok(userService.getAllBackgroundGifs());
    }

    @GetMapping("/budget-gifs")
    public ResponseEntity<BudgetGifsResponseDto> getAllBudgetGifs() {
        return ResponseEntity.ok(userService.getAllBudgetsGif());
    }

    @GetMapping("/font-families")
    public ResponseEntity<FontFamiliesResponseDto> getAllFontFamilies() {
        return ResponseEntity.ok(userService.getAllFontFamilies());
    }

    @GetMapping("/title-colors")
    public ResponseEntity<TitleColorsResponseDto> getAllTitleColors() {
        return ResponseEntity.ok(userService.getAllTitleColors());
    }
}