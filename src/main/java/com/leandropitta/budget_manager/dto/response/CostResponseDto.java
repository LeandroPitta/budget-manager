package com.leandropitta.budget_manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostResponseDto {
    private Long id;
    private LocalDateTime date;
    private String buy;
    private BigDecimal cost;
}
