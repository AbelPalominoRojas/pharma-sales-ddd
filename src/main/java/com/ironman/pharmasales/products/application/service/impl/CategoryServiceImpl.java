package com.ironman.pharmasales.products.application.service.impl;

import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.application.string.StringHelper;
import com.ironman.pharmasales.products.application.dto.category.CategoryDto;
import com.ironman.pharmasales.products.application.dto.category.CategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySaveDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySimpleDto;
import com.ironman.pharmasales.products.application.mapper.CategoryMapper;
import com.ironman.pharmasales.products.application.service.CategoryService;
import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.port.CategoryPort;
import com.ironman.pharmasales.shared.application.page.PageBuild;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends PageBuild<CategoryDto> implements CategoryService {

    private final CategoryPort categoryPort;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryPort.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) throws DataNotFoundException {
        return categoryPort.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(getCategoryNotFound(id));
    }

    @Override
    public CategoryDto create(CategorySaveDto categoryBody) {
        CategoryDomain categorySave = categoryMapper.toDomain(categoryBody);

        categorySave.setKeyword(new StringHelper().slugsKeywords(categorySave.getName()));
        categorySave.setState(State.ACTIVE.getValue());
        categorySave.setCreatedAt(LocalDateTime.now());

        return categoryMapper.toDto(categoryPort.save(categorySave));
    }

    @Override
    public CategoryDto edit(Long id, CategorySaveDto categoryBody) throws DataNotFoundException {
        CategoryDomain categoryDb = categoryPort.findById(id)
                .orElseThrow(getCategoryNotFound(id));

        categoryMapper.update(categoryDb, categoryBody);

        categoryDb.setKeyword(new StringHelper().slugsKeywords(categoryBody.getName()));
        categoryDb.setUpdatedAt(LocalDateTime.now());

        return categoryMapper.toDto(categoryPort.save(categoryDb));
    }

    @Override
    public CategoryDto disabled(Long id) throws DataNotFoundException {
        CategoryDomain categoryDb = categoryPort.findById(id)
                .orElseThrow(getCategoryNotFound(id));

        categoryDb.setState(State.DISABLE.getValue());

        return categoryMapper.toDto(categoryPort.save(categoryDb));
    }

    @Override
    public List<CategorySimpleDto> select() {
        return categoryPort.findByState(State.ACTIVE.getValue())
                .stream()
                .map(categoryMapper::toSimpleDto)
                .toList();
    }

    @Override
    public List<CategorySimpleDto> searchByState(String state) {
        return categoryPort.searchByState(state)
                .stream()
                .map(categoryMapper::toSimpleDto)
                .toList();
    }

    @Override
    public PageResponse<CategoryDto> findAll(CategoryFilterDto pageable) {
        var categoryPage = categoryPort.findAll(categoryMapper.toFilter(pageable));

        var categories = categoryPage.getContent()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return getPage(categoryPage, categories);
    }

    private Supplier<DataNotFoundException> getCategoryNotFound(Long id) {
        return () -> new DataNotFoundException("Categoria no encontrado para el id: " + id);
    }
}
