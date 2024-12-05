package com.leandropitta.cost_management.service;

import com.leandropitta.cost_management.dto.request.CostRequestDto;
import com.leandropitta.cost_management.dto.response.CostResponseDto;
import com.leandropitta.cost_management.dto.response.CostsResponseDto;
import com.leandropitta.cost_management.dto.response.GiftResponseDto;
import com.leandropitta.cost_management.entity.Cost;
import com.leandropitta.cost_management.entity.User;
import com.leandropitta.cost_management.repository.CostRepository;
import com.leandropitta.cost_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CostsResponseDto getCosts() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return CostsResponseDto.builder()
                .costs(costRepository.findByUserId(user.getId(), Sort.by("date").descending())
                        .stream()
                        .map(cost -> modelMapper.map(cost, CostResponseDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    public GiftResponseDto calculateGift() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal totalCost = costRepository.findByUserId(user.getId(), Sort.by("date").descending())
                .stream()
                .map(Cost::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal gift = new BigDecimal("1500.00");
        BigDecimal available = gift.subtract(totalCost);

        return GiftResponseDto.builder()
                .gift(gift)
                .spent(totalCost)
                .available(available)
                .build();
    }

    public CostResponseDto createCost(CostRequestDto costRequestDto) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cost cost = modelMapper.map(costRequestDto, Cost.class);
        cost.setDate(LocalDateTime.now());
        cost.setUserId(user.getId());
        Cost savedCost = costRepository.save(cost);
        return modelMapper.map(savedCost, CostResponseDto.class);
    }

    public CostResponseDto updateCost(Long id, CostRequestDto costRequestDto) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost not found"));
        cost.setBuy(costRequestDto.getBuy());
        cost.setCost(costRequestDto.getCost());
        Cost updatedCost = costRepository.save(cost);
        return modelMapper.map(updatedCost, CostResponseDto.class);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
