package com.ironman.pharmasales.products.application.dto.category;

import com.ironman.pharmasales.shared.application.state.enums.State;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String keyword;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
