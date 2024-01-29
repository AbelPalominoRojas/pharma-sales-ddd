package com.ironman.pharmasales.products.application.service;

import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySaveDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySmallDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface SubcategoryService {
    List<SubcategorySmallDto> findAll();

    SubcategoryDto findById(Long id) throws DataNotFoundException;

    SubcategoryDto create(SubcategorySaveDto subcategoryBody) throws DataNotFoundException;

    SubcategoryDto edit(Long id, SubcategorySaveDto subcategoryBody) throws DataNotFoundException;

    SubcategoryDto disabled(Long id) throws DataNotFoundException;

    PageResponse<SubcategoryDto> findAll(SubcategoryFilterDto filter);
}
