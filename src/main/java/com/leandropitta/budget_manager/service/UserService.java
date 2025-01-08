package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.RegisterRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.BackgroundColorsResponseDto;
import com.leandropitta.budget_manager.dto.response.BackgroundGifsResponseDto;
import com.leandropitta.budget_manager.dto.response.BudgetGifsResponseDto;
import com.leandropitta.budget_manager.dto.response.FontFamiliesResponseDto;
import com.leandropitta.budget_manager.dto.response.TitleColorsResponseDto;
import com.leandropitta.budget_manager.entity.*;
import com.leandropitta.budget_manager.repository.*;
import com.leandropitta.budget_manager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BackgroundColorRepository backgroundColorRepository;
    private final TitleColorRepository titleColorRepository;
    private final FontFamilyRepository fontFamilyRepository;
    private final BackgroundGifRepository backgroundGifRepository;
    private final BudgetGifRepository budgetGifRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public void register(RegisterRequestDto registerRequestDto) {
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }

        BackgroundColor backgroundColor = backgroundColorRepository.findById(registerRequestDto.getBackgroundColorId())
                .orElseThrow(() -> new RuntimeException("Background color not found"));
        TitleColor titleColor = titleColorRepository.findById(registerRequestDto.getTitleColorId())
                .orElseThrow(() -> new RuntimeException("Title color not found"));
        FontFamily fontFamily = fontFamilyRepository.findById(registerRequestDto.getFontFamilyId())
                .orElseThrow(() -> new RuntimeException("Font family not found"));
        BackgroundGif backgroundGif = backgroundGifRepository.findById(registerRequestDto.getBackgroundGifId())
                .orElseThrow(() -> new RuntimeException("Background gif not found"));

        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        user.setTitle(registerRequestDto.getTitle());
        user.setBackgroundColor(backgroundColor);
        user.setTitleColor(titleColor);
        user.setFontFamily(fontFamily);
        user.setBudgetGif(registerRequestDto.getBudgetGif());
        user.setBackgroundGif(backgroundGif);

        userRepository.save(user);
    }

    public void updateUser(UpdateUserRequestDto updateUserRequestDto) {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(updateUserRequestDto.getPassword()));
        userRepository.save(user);
    }

    public BackgroundColorsResponseDto getAllBackgroundColors() {
        return BackgroundColorsResponseDto.builder()
                .backgroundColors(new ArrayList<>(backgroundColorRepository.findAll()))
                .build();
    }

    public BackgroundGifsResponseDto getAllBackgroundGifs() {
        return BackgroundGifsResponseDto.builder()
                .backgroundGifs(new ArrayList<>(backgroundGifRepository.findAll()))
                .build();
    }

    public BudgetGifsResponseDto getAllBudgetsGif() {
        return BudgetGifsResponseDto.builder()
                .budgetGifs(new ArrayList<>(budgetGifRepository.findAll()))
                .build();
    }

    public FontFamiliesResponseDto getAllFontFamilies() {
        return FontFamiliesResponseDto.builder()
                .fontFamilies(new ArrayList<>(fontFamilyRepository.findAll()))
                .build();
    }

    public TitleColorsResponseDto getAllTitleColors() {
        return TitleColorsResponseDto.builder()
                .titleColors(new ArrayList<>(titleColorRepository.findAll()))
                .build();
    }
}