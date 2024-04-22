package com.ironman.pharmasales.clients.domain.model.documenttype;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeFilterDomain extends PageableRequest {
    private String name;
    private String description;
    private String sunatCode;
    private Integer sizeDocument;
    private Integer isSizeExact;
    private Integer isNumeric;
    private String state;
    private String createdAtFrom;
    private String createdAtTo;
}
