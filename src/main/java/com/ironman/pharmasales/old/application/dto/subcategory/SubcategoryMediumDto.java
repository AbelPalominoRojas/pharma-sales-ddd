package com.ironman.pharmasales.old.application.dto.subcategory;

import com.ironman.pharmasales.products.application.dto.category.CategorySimpleDto;
import lombok.Data;

@Data
public class SubcategoryMediumDto {
    private Long id;
    private String name;
    private CategorySimpleDto category;
}
