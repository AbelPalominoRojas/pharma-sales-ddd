package com.ironman.pharmasales.clients.domain.model.documenttype;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeDomain {
    private Long id;
    private String name;
    private String description;
    private String sunatCode;
    private Integer size;
    private  Integer isSizeExact;
    private Integer isNumeric;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
