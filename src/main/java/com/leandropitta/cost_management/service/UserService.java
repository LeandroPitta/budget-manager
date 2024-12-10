package com.leandropitta.cost_management.service;

import com.leandropitta.cost_management.dto.request.AuthRequestDto;
import com.leandropitta.cost_management.dto.request.UpdateUserRequestDto;
import com.leandropitta.cost_management.dto.response.AuthResponseDto;
import com.leandropitta.cost_management.entity.User;
import com.leandropitta.cost_management.repository.UserRepository;
import com.leandropitta.cost_management.util.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    private final String jwtSecret = "yourSecretKey";
    private final long jwtExpirationDays = 1; // 1 day

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = generateJwtToken(authentication);

        User user = userRepository.findByUsername(authRequestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return AuthResponseDto.builder()
                .token(jwt)
                .id(user.getId())
                .username(user.getUsername())
                .build();
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
}