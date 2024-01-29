package com.ironman.pharmasales.shared.domain.page;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse <T> {
    private List<T> content;
    private Integer number;
    private Integer numberOfElements;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}
