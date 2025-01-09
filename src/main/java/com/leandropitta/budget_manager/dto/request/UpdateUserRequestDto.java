package com.leandropitta.budget_manager.dto.request;

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

    @NotBlank(message = "Password is required")
    @Size(max = 12, message = "Password cannot be more than 12 characters")
    private String password;

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Background color ID is required")
    private Long backgroundColorId;

    @NotNull(message = "Title color ID is required")
    private Long titleColorId;

    @NotNull(message = "Font family ID is required")
    private Long fontFamilyId;

    @NotNull(message = "Background gif ID is required")
    private Long backgroundGifId;

    @NotBlank(message = "Budget gif is required")
    private String budgetGif;
}