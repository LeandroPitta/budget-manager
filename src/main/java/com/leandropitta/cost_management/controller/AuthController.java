package com.leandropitta.cost_management.controller;

import com.leandropitta.cost_management.dto.request.AuthRequestDto;
import com.leandropitta.cost_management.dto.response.AuthResponseDto;
import com.leandropitta.cost_management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return ResponseEntity.ok(userService.login(authRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthRequestDto authRequestDto) {
        userService.register(authRequestDto);
        return ResponseEntity.ok().build();
    }
}