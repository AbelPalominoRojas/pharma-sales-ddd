package com.ironman.pharmasales.clients.domain.model.client;

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
public class ClientFilterDomain extends PageableRequest {
    private String name;
    private String lastName;
    private Long documentTypeId;
    private String documentNumber;
    private String state;
    private String createdAtFrom;
    private String createdAtTo;
}
