package com.ironman.pharmasales.old.application.dto.client;

import lombok.Data;

@Data
public class ClientSaveDto {
    private String name;
    private String lastName;
    private Long documentTypeId;
    private String documentNumber;
    private String phoneNumber;
    private String address;
}
