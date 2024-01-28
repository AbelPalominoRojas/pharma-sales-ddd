package com.ironman.pharmasales.old.application.dto.subcategory;

import com.ironman.pharmasales.old.application.dto.category.CategorySimpleDto;
import com.ironman.pharmasales.old.shared.state.enums.State;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubcategoryDto {
    private Long id;
    private String name;
    private String description;
    private String keyword;
    private CategorySimpleDto category;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
