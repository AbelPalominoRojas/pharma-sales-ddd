package com.ironman.pharmasales.products.application.service;

import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.products.application.dto.category.*;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface CategoryService {
    List<CategorySmallDto> findAll();

    CategoryDto findById(Long id) throws DataNotFoundException;

    CategoryDto create(CategorySaveDto categoryBody);

    CategoryDto edit(Long id, CategorySaveDto categoryBody) throws DataNotFoundException;

    CategoryDto disabled(Long id) throws DataNotFoundException;

    PageResponse<CategoryDto> findAll(CategoryFilterDto filter);
}
