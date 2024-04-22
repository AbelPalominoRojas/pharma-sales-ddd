package com.ironman.pharmasales.clients.application.dto.client;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import lombok.Data;

@Data
public class ClientMediumDto {
    private Long id;
    private String fullName;
    private DocumentTypeSmallDto documentType;
    private String documentNumber;
    private String phoneNumber;
    private String address;
}
