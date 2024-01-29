package com.ironman.pharmasales.products.domain.model.subcategory;

import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryDomain {
    private Long id;
    private String name;
    private String description;
    private String keyword;
    private CategoryDomain category;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
