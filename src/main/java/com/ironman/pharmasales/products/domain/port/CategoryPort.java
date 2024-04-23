package com.ironman.pharmasales.products.domain.port;

import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.model.category.CategoryFilterDomain;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryPort {
    List<CategoryDomain> findAll();
    List<CategoryDomain> findByState(String state);
    Optional<CategoryDomain> findById(Long id);
    CategoryDomain save(CategoryDomain domain);
    PageResponse<CategoryDomain> findAll(CategoryFilterDomain filter);
}
