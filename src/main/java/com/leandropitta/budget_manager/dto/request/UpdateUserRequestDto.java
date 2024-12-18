package com.leandropitta.budget_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
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

}
