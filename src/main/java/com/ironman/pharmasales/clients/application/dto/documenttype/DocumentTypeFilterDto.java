package com.ironman.pharmasales.clients.application.dto.documenttype;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeFilterDto extends PageableRequest {
    private String name;
    private String description;
    private String sunatCode;
    private Integer sizeDocument;
    private Boolean isSizeExact;
    private Boolean isNumeric;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
