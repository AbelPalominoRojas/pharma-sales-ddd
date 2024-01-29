package com.ironman.pharmasales.products.application.dto.category;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySimpleDto {
    private Long id;
    private String name;
}
