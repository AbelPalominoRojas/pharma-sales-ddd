package com.ironman.pharmasales.clients.application.dto.client;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientSmallDto {
    private Long id;
    private String fullName;
    private Long documentTypeId;
}
