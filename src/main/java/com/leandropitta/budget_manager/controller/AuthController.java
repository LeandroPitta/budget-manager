package com.leandropitta.budget_manager.controller;

import com.leandropitta.budget_manager.dto.request.AuthRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.AuthResponseDto;
import com.leandropitta.budget_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(userService.login(authRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AuthRequestDto authRequestDto) {
        userService.register(authRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        userService.updateUser(updateUserRequestDto);
        return ResponseEntity.ok().build();
    }
}