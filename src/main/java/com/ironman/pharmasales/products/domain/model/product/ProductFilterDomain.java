package com.ironman.pharmasales.products.domain.model.product;

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
public class ProductFilterDomain extends PageableRequest {
    private String name;
    private String description;
    private String presentation;
    private Long stock;
    private Long subcategoryId;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
