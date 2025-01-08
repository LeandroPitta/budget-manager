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
public class RegisterRequestDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 12, message = "Password cannot be more than 12 characters")
    private String password;

    @NotNull
    private String title;

    @NotNull
    private Long backgroundColorId;

    @NotNull
    private Long titleColorId;

    @NotNull
    private Long fontFamilyId;

    @NotNull
    private String budgetGif;

    @NotNull
    private Long backgroundGifId;
}
