package com.leandropitta.budget_manager.dto.response;

import com.leandropitta.budget_manager.entity.FontFamily;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FontFamiliesResponseDto {
    private List<FontFamily> fontFamilies;
}
