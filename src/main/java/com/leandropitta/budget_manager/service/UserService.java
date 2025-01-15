package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.RegisterRequestDto;
import com.leandropitta.budget_manager.dto.request.UpdateUserRequestDto;
import com.leandropitta.budget_manager.dto.response.*;
import com.leandropitta.budget_manager.entity.*;
import com.leandropitta.budget_manager.repository.*;
import com.leandropitta.budget_manager.util.GifUtil;
import com.leandropitta.budget_manager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        user.setBudgetValue(registerRequestDto.getBudgetValue());
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

        BackgroundColor backgroundColor = backgroundColorRepository.findById(updateUserRequestDto.getBackgroundColorId())
                .orElseThrow(() -> new RuntimeException("Background color not found"));
        TitleColor titleColor = titleColorRepository.findById(updateUserRequestDto.getTitleColorId())
                .orElseThrow(() -> new RuntimeException("Title color not found"));
        FontFamily fontFamily = fontFamilyRepository.findById(updateUserRequestDto.getFontFamilyId())
                .orElseThrow(() -> new RuntimeException("Font family not found"));
        BackgroundGif backgroundGif = backgroundGifRepository.findById(updateUserRequestDto.getBackgroundGifId())
                .orElseThrow(() -> new RuntimeException("Background gif not found"));

        user.setPassword(passwordEncoder.encode(updateUserRequestDto.getPassword()));
        user.setTitle(updateUserRequestDto.getTitle());
        user.setBudgetValue(updateUserRequestDto.getBudgetValue());
        user.setBackgroundColor(backgroundColor);
        user.setTitleColor(titleColor);
        user.setFontFamily(fontFamily);
        user.setBudgetGif(updateUserRequestDto.getBudgetGif());
        user.setBackgroundGif(backgroundGif);

        userRepository.save(user);
    }

    public BackgroundColorsResponseDto getAllBackgroundColors() {
        return BackgroundColorsResponseDto.builder()
                .backgroundColors(new ArrayList<>(backgroundColorRepository.findAll()))
                .build();
    }

    public BackgroundGifsResponseDto getAllBackgroundGifs() {
        List<BackgroundGif> backgroundGifs = backgroundGifRepository.findAll();
        List<BackgroundGifResponseDto> backgroundGifResponseDtos = backgroundGifs.stream()
                .map(backgroundGif -> BackgroundGifResponseDto.builder()
                        .id(backgroundGif.getId())
                        .description(backgroundGif.getDescription())
                        .url(GifUtil.getBackgroundGifUrl(backgroundGif.getId()))
                        .build())
                .collect(Collectors.toList());

        return BackgroundGifsResponseDto.builder()
                .backgroundGifs(backgroundGifResponseDtos)
                .build();
    }

    public BudgetGifsResponseDto getAllBudgetsGif() {
        List<BudgetGif> budgetGifs = budgetGifRepository.findAll();
        List<BudgetGifResponseDto> budgetGifResponseDtos = budgetGifs.stream()
                .map(budgetGif -> BudgetGifResponseDto.builder()
                        .id(budgetGif.getId())
                        .description(budgetGif.getDescription())
                        .url(GifUtil.getBudgetGifUrl(budgetGif.getId().toString()))
                        .build())
                .collect(Collectors.toList());

        return BudgetGifsResponseDto.builder()
                .budgetGifs(budgetGifResponseDtos)
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