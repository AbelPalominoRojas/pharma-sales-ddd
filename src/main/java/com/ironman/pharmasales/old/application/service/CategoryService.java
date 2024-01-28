package com.ironman.pharmasales.old.application.service;

import com.ironman.pharmasales.old.application.dto.category.CategoryDto;
import com.ironman.pharmasales.old.application.dto.category.CategorySaveDto;
import com.ironman.pharmasales.old.application.dto.category.CategorySimpleDto;
import com.ironman.pharmasales.old.shared.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto findById(Long id) throws DataNotFoundException;

    CategoryDto create(CategorySaveDto categoryBody);

    CategoryDto edit(Long id, CategorySaveDto categoryBody);

    CategoryDto disbled(Long id);

    List<CategorySimpleDto> select();

    List<CategorySimpleDto> searchByState(String state);

    Page<CategoryDto> pagination(Pageable pageable);
}
