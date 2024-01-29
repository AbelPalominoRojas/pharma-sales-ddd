package com.ironman.pharmasales.products.application.dto.product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveDto {
    @NotBlank(message = "El campo nombre es requerido")
    @Size(min = 3)
    private String name;
    private String description;
    private String presentation;
    private String unitMeasure;
    private String prescription;
    private String precaution;
    private String sideEffect;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private Long stock;

    @NotNull(message = "El campo subcategoria es requerido")
    @Positive(message = "El campo subcategoria debe ser un numero positivo")
    private Long subcategoryId;
}
