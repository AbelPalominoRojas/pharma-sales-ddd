package com.ironman.pharmasales.invoices.domain.model;

import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailDomain {
    private InvoiceDomain invoice;
    private ProductDomain product;
    private Integer quantity;
    private BigDecimal price;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
