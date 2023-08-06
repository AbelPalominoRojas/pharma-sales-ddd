package com.ironman.pharmasales.application.service;

import com.ironman.pharmasales.application.dto.subcategory.SubcategoryDto;
import com.ironman.pharmasales.application.dto.subcategory.SubcategoryFilterDto;
import com.ironman.pharmasales.application.dto.subcategory.SubcategorySaveDto;

import java.util.List;
import java.util.Optional;

public interface SubcategoryService {
    List<SubcategoryDto> findAll();

    SubcategoryDto findById(Long id);

    SubcategoryDto create(SubcategorySaveDto subcategoryBody);

    SubcategoryDto edit(Long id, SubcategorySaveDto subcategoryBody);

    SubcategoryDto disabled(Long id);

    List<SubcategoryDto> filter(Optional<SubcategoryFilterDto> filter);
}
