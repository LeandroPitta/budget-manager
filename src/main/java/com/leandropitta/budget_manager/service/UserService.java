package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.AuthRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.AuthResponseDto;
import com.leandropitta.budget_manager.entity.User;
import com.leandropitta.budget_manager.repository.UserRepository;
import com.leandropitta.budget_manager.util.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
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

    public void register(AuthRequestDto authRequestDto) {
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        User user = new User();
        user.setUsername(authRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(authRequestDto.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(UpdateUserRequestDto updateUserRequestDto) {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(updateUserRequestDto.getPassword()));
        userRepository.save(user);
    }

    private String getBackgroundGifUrl(Long gifId) {
        String gifPath = String.format("src/main/resources/static/gifs/background_gif/%s.gif", gifId);
        if (Files.exists(Paths.get(gifPath))) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return String.format("%s/gifs/background_gif/%s.gif", baseUrl, gifId);
        } else {
            return null;
        }
    }

    private String getBudgetGifUrl(String gifId) {
        String gifPath = String.format("src/main/resources/static/gifs/budget_gif/%s.gif", gifId);

        if (Files.exists(Paths.get(gifPath))) {
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return String.format("%s/gifs/budget_gif/%s.gif", baseUrl, gifId);
        } else {
            return gifId;
        }
    }
}