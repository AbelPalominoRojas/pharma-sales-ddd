package com.ironman.pharmasales.products.domain.model.category;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDomain {
    private Long id;
    private String name;
    private String description;
    private String keyword;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
