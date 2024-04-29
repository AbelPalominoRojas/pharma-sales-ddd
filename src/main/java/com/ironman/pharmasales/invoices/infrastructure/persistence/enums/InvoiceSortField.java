package com.ironman.pharmasales.invoices.infrastructure.persistence.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvoiceSortField {
    ID("id", "id"),
    INVOICE_DATE("invoiceDate", "invoice_date"),
    CLIENT_ID("clientId", "client_id"),
    USER_ID("userId", "user_id"),
    STATE("state", "state");

    private final String value;
    private final String column;

    public static String getSqlName(String name) {
        try {
            return InvoiceSortField.valueOf(name).getColumn();
        } catch (Exception e) {
            return InvoiceSortField.ID.getColumn();
        }
    }
}
