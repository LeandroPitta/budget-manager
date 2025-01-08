package com.leandropitta.budget_manager.dto.response;

import com.leandropitta.budget_manager.entity.TitleColor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleColorsResponseDto {
    private List<TitleColor> titleColors;
}
