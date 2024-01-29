package com.ironman.pharmasales.products.application.dto.subcategory;

import com.ironman.pharmasales.products.application.dto.category.CategorySmallDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryMediumDto {
    private Long id;
    private String name;
    private CategorySmallDto category;
}
