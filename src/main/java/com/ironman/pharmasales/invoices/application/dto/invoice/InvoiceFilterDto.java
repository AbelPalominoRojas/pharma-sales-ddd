package com.ironman.pharmasales.invoices.application.dto.invoice;

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
public class InvoiceFilterDto extends PageableRequest {
    private LocalDate invoiceDateFrom;
    private LocalDate invoiceDateTo;
    private Long clientId;
    private Long userId;
    private String state;
}

