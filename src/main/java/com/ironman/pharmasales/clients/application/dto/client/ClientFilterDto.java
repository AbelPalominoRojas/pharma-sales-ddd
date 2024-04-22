package com.ironman.pharmasales.clients.application.dto.client;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ClientFilterDto extends PageableRequest {
    private String name;
    private String lastName;
    private Long documentTypeId;
    private String documentNumber;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
