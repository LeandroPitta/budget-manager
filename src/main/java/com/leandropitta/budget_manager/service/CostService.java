package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.CostRequestDto;
import com.leandropitta.budget_manager.dto.response.CostResponseDto;
import com.leandropitta.budget_manager.dto.response.CostsResponseDto;
import com.leandropitta.budget_manager.dto.response.GiftResponseDto;
import com.leandropitta.budget_manager.entity.Cost;
import com.leandropitta.budget_manager.entity.User;
import com.leandropitta.budget_manager.repository.CostRepository;
import com.leandropitta.budget_manager.repository.UserRepository;
import com.leandropitta.budget_manager.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public CostsResponseDto getCosts() {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return CostsResponseDto.builder()
                .costs(costRepository.findByUserId(user.getId(), Sort.by("date").descending())
                        .stream()
                        .map(cost -> modelMapper.map(cost, CostResponseDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Transactional(readOnly = true)
    public GiftResponseDto calculateGift() {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal totalCost = costRepository.findByUserId(user.getId(), Sort.by("date").descending())
                .stream()
                .map(Cost::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal budgetValue = user.getBudgetValue();
        BigDecimal available = budgetValue.subtract(totalCost);

        return GiftResponseDto.builder()
                        .gift(budgetValue)
                        .spent(totalCost)
                        .available(available)
                        .build();
    }

    @Transactional
    public CostResponseDto createCost(CostRequestDto costRequestDto) {
        String username = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cost cost = modelMapper.map(costRequestDto, Cost.class);
        cost.setDate(LocalDateTime.now());
        cost.setUser(user);
        Cost savedCost = costRepository.save(cost);
        return modelMapper.map(savedCost, CostResponseDto.class);
    }

    @Transactional
    public CostResponseDto updateCost(Long id, CostRequestDto costRequestDto) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost not found"));

        String tokenUsername = SecurityUtil.getCurrentUsername();
        if (!tokenUsername.equals(cost.getUser().getUsername())) {
            throw new RuntimeException("Unauthorized");
        }

        cost.setBuy(costRequestDto.getBuy());
        cost.setCost(costRequestDto.getCost());
        Cost updatedCost = costRepository.save(cost);
        return modelMapper.map(updatedCost, CostResponseDto.class);
    }

    @Transactional
    public void deleteCost(Long id) {
        Cost cost = costRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost not found"));

        String tokenUsername = SecurityUtil.getCurrentUsername();
        if (!tokenUsername.equals(cost.getUser().getUsername())) {
            throw new RuntimeException("Unauthorized to delete this cost");
        }

        costRepository.delete(cost);
    }
}
