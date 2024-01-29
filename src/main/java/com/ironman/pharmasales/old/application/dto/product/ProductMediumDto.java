package com.ironman.pharmasales.old.application.dto.product;

import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryMediumDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
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

