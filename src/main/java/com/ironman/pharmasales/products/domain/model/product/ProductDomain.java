package com.ironman.pharmasales.products.domain.model.product;

import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDomain {
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
    private SubcategoryDomain subcategory;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
