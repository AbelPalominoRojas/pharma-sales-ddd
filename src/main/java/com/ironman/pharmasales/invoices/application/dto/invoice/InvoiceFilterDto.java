package com.ironman.pharmasales.invoices.application.dto.invoice;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceFilterDto {
    private LocalDate invoiceDate;
    private Long clientId;
    private String state;
}

