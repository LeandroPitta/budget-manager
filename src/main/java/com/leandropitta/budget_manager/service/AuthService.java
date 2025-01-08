package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.AuthRequestDto;
import com.leandropitta.budget_manager.dto.response.AuthResponseDto;
import com.leandropitta.budget_manager.entity.*;
import com.leandropitta.budget_manager.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    private final String jwtSecret = "yourSecretKey";
    private final long jwtExpirationDays = 1; // 1 day

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = generateJwtToken(authentication);

        User user = userRepository.findByUsername(authRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AuthResponseDto authResponseDto = modelMapper.map(user, AuthResponseDto.class);
        authResponseDto.setBackgroundGif(getBackgroundGifUrl(user.getBackgroundGif().getId()));
        authResponseDto.setBudgetGif(getBudgetGifUrl(user.getBudgetGif()));
        authResponseDto.setToken(jwt);

        return authResponseDto;
    }

    private String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        LocalDate expirationDate = LocalDate.now().plusDays(jwtExpirationDays);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String getBackgroundGifUrl(Long gifId) {
        String gifPath = String.format("assets/gifs/background_gif/%s.gif", gifId);
        if (getClass().getClassLoader().getResource(gifPath) != null) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return String.format("%s/assets/gifs/background_gif/%s.gif", baseUrl, gifId);
        } else {
            return null;
        }
    }

    private String getBudgetGifUrl(String gifId) {
        String gifPath = String.format("assets/gifs/budget_gif/%s.gif", gifId);
        if (getClass().getClassLoader().getResource(gifPath) != null) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return String.format("%s/assets/gifs/budget_gif/%s.gif", baseUrl, gifId);
        } else {
            return gifId;
        }
    }
}