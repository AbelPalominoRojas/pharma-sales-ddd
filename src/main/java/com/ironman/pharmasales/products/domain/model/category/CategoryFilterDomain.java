package com.ironman.pharmasales.products.domain.model.category;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;

import java.time.LocalDate;

public class CategoryFilterDomain extends PageableRequest {
    private String name;
    private String description;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
