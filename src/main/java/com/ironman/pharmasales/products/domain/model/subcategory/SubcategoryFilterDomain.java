package com.ironman.pharmasales.products.domain.model.subcategory;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SubcategoryFilterDomain extends PageableRequest {
    private String name;
    private String description;
    private Long categoryId;
    private String state;
    private String createdAtFrom;
    private String createdAtTo;
}
