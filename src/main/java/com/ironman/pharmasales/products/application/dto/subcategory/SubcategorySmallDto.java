package com.ironman.pharmasales.products.application.dto.subcategory;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubcategorySmallDto {
    private Long id;
    private String name;
    private Long categoryId;
}
