package com.ironman.pharmasales.invoices.domain.model;


import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceFilterDomain extends PageableRequest {
    private String invoiceDateFrom;
    private String invoiceDateTo;
    private Long clientId;
    private Long userId;
    private String state;
}
