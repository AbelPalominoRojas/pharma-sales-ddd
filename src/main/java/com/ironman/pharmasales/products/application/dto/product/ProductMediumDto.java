package com.ironman.pharmasales.products.application.dto.product;

import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryMediumDto;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMediumDto {
    private Long id;
    private String name;
    private String description;
    private String presentation;
    private String unitMeasure;
    private String prescription;
    private String precaution;
    private String sideEffect;
    private BigDecimal price;
    private Long stock;
    private SubcategoryMediumDto subcategory;
}

