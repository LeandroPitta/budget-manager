package com.leandropitta.cost_management.controller;

import com.leandropitta.cost_management.dto.response.CostsResponseDto;
import com.leandropitta.cost_management.service.CostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cost")
@AllArgsConstructor
public class CostController {

    private final CostService costService;

    @GetMapping("/greeting")
    public String test() {
        return "Lívia's 15th birthday";
    }

    @GetMapping
    public CostsResponseDto getCosts() {
        return costService.getCosts();
    }
}
