package com.ironman.pharmasales.old.application.dto.product;

import lombok.Data;

@Data
public class ProductFilterDto {
    private String name;
    private String description;
    private String presentation;
    private Long stock;
    private Long subcategoryId;
    private String state;
}
