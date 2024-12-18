package com.leandropitta.budget_manager.controller;

import com.leandropitta.budget_manager.dto.request.CostRequestDto;
import com.leandropitta.budget_manager.dto.response.CostResponseDto;
import com.leandropitta.budget_manager.dto.response.CostsResponseDto;
import com.leandropitta.budget_manager.dto.response.GiftResponseDto;
import com.leandropitta.budget_manager.service.CostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cost")
@AllArgsConstructor
public class CostController {

    private final CostService costService;

    @GetMapping
    public CostsResponseDto getCosts() {
        return costService.getCosts();
    }

    @GetMapping("/gift")
    public GiftResponseDto calculateGift() {
        return costService.calculateGift();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CostResponseDto createCost(@RequestBody CostRequestDto costRequestDto) {
        return costService.createCost(costRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CostResponseDto updateCost(@PathVariable Long id, @RequestBody CostRequestDto costRequestDto) {
        return costService.updateCost(id, costRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCost(@PathVariable Long id) {
        costService.deleteCost(id);
    }
}
