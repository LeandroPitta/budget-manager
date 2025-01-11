package com.leandropitta.budget_manager.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private Long id;
    private String username;
    private String token;
    private String title;
    private BigDecimal budgetValue;
    private String backgroundColor;
    private String titleColor;
    private String fontFamily;
    private String budgetGif;
    private String backgroundGif;
}
