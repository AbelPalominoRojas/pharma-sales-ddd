package com.ironman.pharmasales.invoices.domain.model;

import com.ironman.pharmasales.clients.domain.model.client.ClientDomain;
import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDomain {
    private Long id;
    private LocalDateTime invoiceDate;
    private ClientDomain client;
    private UserDomain user;
    private String state;
    private List<InvoiceDetailDomain> invoiceDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
