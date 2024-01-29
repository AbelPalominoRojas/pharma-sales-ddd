package com.ironman.pharmasales.shared.application.page;

import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public abstract class PageBuild<T> {
    public PageResponse<T> getPage(PageResponse<?> page, List<T> content) {
        return PageResponse.<T>builder()
                .content(content)
                .number(page.getNumber())
                .size(page.getSize())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
