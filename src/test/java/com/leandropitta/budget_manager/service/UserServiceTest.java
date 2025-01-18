package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.RegisterRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.*;
import com.leandropitta.budget_manager.entity.*;
import com.leandropitta.budget_manager.repository.*;
import com.leandropitta.budget_manager.util.GifUtil;
import com.leandropitta.budget_manager.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BackgroundColorRepository backgroundColorRepository;
    @Mock
    private TitleColorRepository titleColorRepository;
    @Mock
    private FontFamilyRepository fontFamilyRepository;
    @Mock
    private BackgroundGifRepository backgroundGifRepository;
    @Mock
    private BudgetGifRepository budgetGifRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private RegisterRequestDto registerRequestDto;
    private UpdateUserRequestDto updateUserRequestDto;
    private User user;
    private BackgroundColor backgroundColor;
    private TitleColor titleColor;
    private FontFamily fontFamily;
    private BackgroundGif backgroundGif;

    @BeforeEach
    void setUp() {
        registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("testUser");
        registerRequestDto.setPassword("password");
        registerRequestDto.setTitle("title");
        registerRequestDto.setBudgetValue(BigDecimal.valueOf(1000));
        registerRequestDto.setBackgroundColorId(1L);
        registerRequestDto.setTitleColorId(1L);
        registerRequestDto.setFontFamilyId(1L);
        registerRequestDto.setBackgroundGifId(1L);
        registerRequestDto.setBudgetGif("budgetGif");

        updateUserRequestDto = new UpdateUserRequestDto();
        updateUserRequestDto.setPassword("newPassword");
        updateUserRequestDto.setTitle("newTitle");
        updateUserRequestDto.setBudgetValue(BigDecimal.valueOf(2000));
        updateUserRequestDto.setBackgroundColorId(1L);
        updateUserRequestDto.setTitleColorId(1L);
        updateUserRequestDto.setFontFamilyId(1L);
        updateUserRequestDto.setBackgroundGifId(1L);
        updateUserRequestDto.setBudgetGif("newBudgetGif");

        user = new User();
        user.setUsername("testUser");

        backgroundColor = new BackgroundColor();
        titleColor = new TitleColor();
        fontFamily = new FontFamily();
        backgroundGif = new BackgroundGif();
    }

    @Test
    void register_shouldRegisterUser() {
        when(userRepository.findByUsername(registerRequestDto.getUsername())).thenReturn(Optional.empty());
        when(backgroundColorRepository.findById(registerRequestDto.getBackgroundColorId())).thenReturn(Optional.of(backgroundColor));
        when(titleColorRepository.findById(registerRequestDto.getTitleColorId())).thenReturn(Optional.of(titleColor));
        when(fontFamilyRepository.findById(registerRequestDto.getFontFamilyId())).thenReturn(Optional.of(fontFamily));
        when(backgroundGifRepository.findById(registerRequestDto.getBackgroundGifId())).thenReturn(Optional.of(backgroundGif));
        when(passwordEncoder.encode(registerRequestDto.getPassword())).thenReturn("encodedPassword");

        userService.register(registerRequestDto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_shouldThrowExceptionWhenUsernameIsTaken() {
        when(userRepository.findByUsername(registerRequestDto.getUsername())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.register(registerRequestDto));
        assertEquals("Username is already taken", exception.getMessage());
    }

    @Test
    void updateUser_shouldUpdateUser() {
        try (MockedStatic<SecurityUtil> mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getCurrentUsername).thenReturn("testUser");

            when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
            when(backgroundColorRepository.findById(updateUserRequestDto.getBackgroundColorId())).thenReturn(Optional.of(backgroundColor));
            when(titleColorRepository.findById(updateUserRequestDto.getTitleColorId())).thenReturn(Optional.of(titleColor));
            when(fontFamilyRepository.findById(updateUserRequestDto.getFontFamilyId())).thenReturn(Optional.of(fontFamily));
            when(backgroundGifRepository.findById(updateUserRequestDto.getBackgroundGifId())).thenReturn(Optional.of(backgroundGif));
            when(passwordEncoder.encode(updateUserRequestDto.getPassword())).thenReturn("encodedNewPassword");

            userService.updateUser(updateUserRequestDto);

            verify(userRepository).save(any(User.class));
        }
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserNotFound() {
        try (MockedStatic<SecurityUtil> mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getCurrentUsername).thenReturn("testUser");

            when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateUser(updateUserRequestDto));
            assertEquals("User not found", exception.getMessage());
        }
    }

    @Test
    void getAllBackgroundColors_shouldReturnAllBackgroundColors() {
        userService.getAllBackgroundColors();
        verify(backgroundColorRepository).findAll();
    }

    @Test
    void getAllBackgroundGifs_shouldReturnAllBackgroundGifs() {
        userService.getAllBackgroundGifs();
        verify(backgroundGifRepository).findAll();
    }

    @Test
    void getAllBudgetsGif_shouldReturnAllBudgetGifs() {
        userService.getAllBudgetsGif();
        verify(budgetGifRepository).findAll();
    }

    @Test
    void getAllFontFamilies_shouldReturnAllFontFamilies() {
        userService.getAllFontFamilies();
        verify(fontFamilyRepository).findAll();
    }

    @Test
    void getAllTitleColors_shouldReturnAllTitleColors() {
        userService.getAllTitleColors();
        verify(titleColorRepository).findAll();
    }
}