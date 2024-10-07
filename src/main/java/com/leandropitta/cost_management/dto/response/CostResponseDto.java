package com.leandropitta.cost_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostResponseDto {
    private Integer id;
    private LocalDateTime date;
    private String buy;
    private BigDecimal cost;
}
