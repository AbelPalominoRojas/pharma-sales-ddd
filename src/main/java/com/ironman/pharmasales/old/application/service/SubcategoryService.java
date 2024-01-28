package com.ironman.pharmasales.old.application.service;

import com.ironman.pharmasales.old.application.dto.subcategory.SubcategoryDto;
import com.ironman.pharmasales.old.application.dto.subcategory.SubcategoryFilterDto;
import com.ironman.pharmasales.old.application.dto.subcategory.SubcategorySaveDto;
import com.ironman.pharmasales.old.shared.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubcategoryService {
    List<SubcategoryDto> findAll();

    SubcategoryDto findById(Long id) throws DataNotFoundException;

    SubcategoryDto create(SubcategorySaveDto subcategoryBody) throws DataNotFoundException;

    SubcategoryDto edit(Long id, SubcategorySaveDto subcategoryBody) throws DataNotFoundException;

    SubcategoryDto disabled(Long id) throws DataNotFoundException;

    List<SubcategoryDto> filter(Optional<SubcategoryFilterDto> filter);

    Page<SubcategoryDto> paginationFilter(Pageable pageable, Optional<SubcategoryFilterDto> filter);
}
