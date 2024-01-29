package com.ironman.pharmasales.old.application.dto.invoicedetail;

import com.ironman.pharmasales.old.application.dto.product.ProductMediumDto;
import com.ironman.pharmasales.shared.application.state.enums.State;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceDetailDto {
    private Integer quantity;
    private BigDecimal price;
    private ProductMediumDto product;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
