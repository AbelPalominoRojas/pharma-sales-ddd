package com.ironman.pharmasales.products.application.dto.subcategory;

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
public class SubcategoryFilterDto extends PageableRequest {
    private String name;
    private String description;
    private Long categoryId;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
