package com.ironman.pharmasales.products.application.dto.category;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFilterDto extends PageableRequest {
    private String name;
    private String description;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
