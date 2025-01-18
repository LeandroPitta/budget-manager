package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.AuthRequestDto;
import com.leandropitta.budget_manager.dto.response.AuthResponseDto;
import com.leandropitta.budget_manager.entity.BackgroundGif;
import com.leandropitta.budget_manager.entity.User;
import com.leandropitta.budget_manager.repository.UserRepository;
import com.leandropitta.budget_manager.util.GifUtil;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AuthService authService;

    private AuthRequestDto authRequestDto;
    private User user;
    private Authentication authentication;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername("userTest");
        authRequestDto.setPassword("password");
    
        user = new User();
        user.setUsername("userTest");
        user.setBackgroundGif(new BackgroundGif());
        user.setBudgetGif("budgetGifUrl");
    
        authentication = mock(Authentication.class);
        userDetails = mock(UserDetails.class);
    
        lenient().when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
        lenient().when(userDetails.getUsername()).thenReturn("userTest");
        lenient().when(userRepository.findByUsername("userTest")).thenReturn(Optional.of(user));
        lenient().when(modelMapper.map(any(User.class), any(Class.class))).thenReturn(new AuthResponseDto());
    }
    
    @Test
    public void testLoginSuccess() {
        AuthResponseDto authResponseDto = authService.login(authRequestDto);
    
        assertNotNull(authResponseDto);
        assertNotNull(authResponseDto.getToken());
        assertEquals(GifUtil.getBackgroundGifUrl(user.getBackgroundGif().getId()), authResponseDto.getBackgroundGif());
        assertEquals(GifUtil.getBudgetGifUrl(user.getBudgetGif()), authResponseDto.getBudgetGif());
    }
    
    @Test
    public void testLoginUserNotFound() {
        when(userRepository.findByUsername("errorTest")).thenReturn(Optional.empty());
    
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authRequestDto.setUsername("errorTest");
            authService.login(authRequestDto);
        });
    
        assertEquals("User not found", exception.getMessage());
    }
    
    @Test
    public void testGenerateJwtToken() {
        String jwt = authService.login(authRequestDto).getToken();
    
        assertNotNull(jwt);
    
        String username = Jwts.parser()
                .setSigningKey("yourSecretKey")
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    
        assertEquals("userTest", username);
    }
}