package com.leandropitta.budget_manager.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

   
    private String title;

    private BigDecimal budgetValue;

    private Long backgroundColorId;

    private Long titleColorId;

    private Long fontFamilyId;

    private Long backgroundGifId;

    private String budgetGif;
}