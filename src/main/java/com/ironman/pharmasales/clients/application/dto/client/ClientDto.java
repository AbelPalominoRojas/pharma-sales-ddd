package com.ironman.pharmasales.clients.application.dto.client;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import com.ironman.pharmasales.shared.application.state.enums.State;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDto {
    private Long id;
    private String name;
    private String lastName;
    private DocumentTypeSmallDto documentType;
    private String documentNumber;
    private String phoneNumber;
    private String address;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
