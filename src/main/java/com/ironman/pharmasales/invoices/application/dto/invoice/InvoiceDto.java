package com.ironman.pharmasales.invoices.application.dto.invoice;

import com.ironman.pharmasales.clients.application.dto.client.ClientMediumDto;
import com.ironman.pharmasales.invoices.application.dto.invoicedetail.InvoiceDetailDto;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.users.application.dto.user.UserMediumDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceDto {
    private Long id;
    private LocalDateTime invoiceDate;
    private ClientMediumDto client;
    private UserMediumDto user;
    private List<InvoiceDetailDto> invoiceDetails;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
