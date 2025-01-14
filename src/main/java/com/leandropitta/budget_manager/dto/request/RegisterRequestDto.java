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
public class RegisterRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 12, message = "Password cannot be more than 12 characters")
    private String password;

    @NotNull(message = "Title is required")
    @Size(max = 22, message = "Title cannot be more than 22 characters")
    private String title;

    @NotNull(message = "Budget value is required")
    private BigDecimal budgetValue;

    @NotNull(message = "Background color ID is required")
    private Long backgroundColorId;

    @NotNull(message = "Title color ID is required")
    private Long titleColorId;

    @NotNull(message = "Font family ID is required")
    private Long fontFamilyId;

    @NotNull(message = "Background gif ID is required")
    private Long backgroundGifId;

    @NotBlank(message = "Budget gif is required")
    @Size(max = 14, message = "Budget gif cannot be more than 14 characters")
    private String budgetGif;
}
