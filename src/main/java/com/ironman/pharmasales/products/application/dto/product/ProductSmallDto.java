package com.ironman.pharmasales.products.application.dto.product;

import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySmallDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSmallDto {
    private Long id;
    private String name;
    private SubcategorySmallDto subcategory;
}
