package com.leandropitta.budget_manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftResponseDto {
    private BigDecimal gift;
    private BigDecimal spent;
    private BigDecimal available;
}
