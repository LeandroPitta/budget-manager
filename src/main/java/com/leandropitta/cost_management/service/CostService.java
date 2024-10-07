package com.leandropitta.cost_management.service;

import com.leandropitta.cost_management.dto.request.CostRequestDto;
import com.leandropitta.cost_management.dto.response.CostResponseDto;
import com.leandropitta.cost_management.dto.response.CostsResponseDto;
import com.leandropitta.cost_management.entity.Cost;
import com.leandropitta.cost_management.repository.CostRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CostService {

    private final CostRepository costRepository;
    private final ModelMapper modelMapper;

    public CostsResponseDto getCosts() {
        return CostsResponseDto.builder()
                .costs(costRepository.findAll(Sort.by("date").descending())
                        .stream()
                        .map(cost -> modelMapper.map(cost, CostResponseDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    public CostResponseDto createCost(CostRequestDto costRequestDto) {
        Cost cost = modelMapper.map(costRequestDto, Cost.class);
        cost.setDate(LocalDateTime.now());
        Cost savedCost = costRepository.save(cost);
        return modelMapper.map(savedCost, CostResponseDto.class);
    }
}
