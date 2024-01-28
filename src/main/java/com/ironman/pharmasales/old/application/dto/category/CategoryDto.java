package com.ironman.pharmasales.old.application.dto.category;

import com.ironman.pharmasales.old.shared.state.enums.State;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String keyword;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
