package com.leandropitta.budget_manager.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestDto {

    @Size(max = 12, message = "Password cannot be more than 12 characters")
    private String password;

    @Size(max = 22, message = "Title cannot be more than 22 characters")
    private String title;

    private BigDecimal budgetValue;

    private Long backgroundColorId;

    private Long titleColorId;

    private Long fontFamilyId;

    private Long backgroundGifId;

    @Size(max = 14, message = "Title cannot be more than 14 characters")
    private String budgetGif;
}