package com.ironman.pharmasales.products.application.dto.product;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto extends PageableRequest {
    private String name;
    private String description;
    private String presentation;
    private Long stock;
    private Long subcategoryId;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
