package com.leandropitta.budget_manager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackgroundGifResponseDto {
    private Long id;
    private String description;
    private String url;
}
