package com.ironman.pharmasales.shared.infrastructure.persistence.page;

import com.ironman.pharmasales.shared.domain.page.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public abstract class PageProcessor<T> {
    public PageResponse<T> getPageBuild(Page<?> page, List<T> content) {
        return PageResponse.<T>builder()
                .content(content)
                .number(page.getNumber() + 1)
                .size(page.getSize())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
