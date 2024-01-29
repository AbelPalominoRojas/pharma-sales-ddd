package com.ironman.pharmasales.products.application.dto.subcategory;

import com.ironman.pharmasales.products.application.dto.category.CategorySmallDto;
import com.ironman.pharmasales.shared.application.state.enums.State;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryDto {
    private Long id;
    private String name;
    private String description;
    private String keyword;
    private CategorySmallDto category;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
