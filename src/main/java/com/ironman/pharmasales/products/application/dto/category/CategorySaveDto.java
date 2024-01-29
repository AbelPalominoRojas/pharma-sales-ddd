package com.ironman.pharmasales.products.application.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySaveDto {
    @NotBlank(message = "El campo nombre es requerido")
    private String name;
    private String description;
}
