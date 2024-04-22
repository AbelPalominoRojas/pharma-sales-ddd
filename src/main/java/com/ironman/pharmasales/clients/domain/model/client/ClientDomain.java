package com.ironman.pharmasales.clients.domain.model.client;

import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDomain {
    private Long id;
    private String name;
    private String lastName;
    private Long documentTypeId;
    private DocumentTypeDomain documentType;
    private String documentNumber;
    private String phoneNumber;
    private String address;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

