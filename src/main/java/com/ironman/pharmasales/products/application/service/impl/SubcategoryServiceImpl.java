package com.ironman.pharmasales.products.application.service.impl;

import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySaveDto;
import com.ironman.pharmasales.products.application.dto.subcategory.SubcategorySmallDto;
import com.ironman.pharmasales.products.application.mapper.SubcategoryMapper;
import com.ironman.pharmasales.products.application.service.SubcategoryService;
import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.products.domain.port.CategoryPort;
import com.ironman.pharmasales.products.domain.port.SubcategoryPort;
import com.ironman.pharmasales.shared.application.page.PageBuild;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.application.string.StringHelper;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class SubcategoryServiceImpl extends PageBuild<SubcategoryDto> implements SubcategoryService {

    private final SubcategoryPort subcategoryPort;
    private final CategoryPort categoryPort;
    private final SubcategoryMapper subcategoryMapper;

    @Override
    public List<SubcategorySmallDto> findAll() {
        return subcategoryPort.findByState(State.ACTIVE.getValue())
                .stream()
                .map(subcategoryMapper::toSmallDto)
                .toList();
    }

    @Override
    public SubcategoryDto findById(Long id) throws DataNotFoundException {
        return subcategoryPort.findById(id)
                .map(subcategoryMapper::toDto)
                .orElseThrow(getSubcategoryNotFound(id));
    }

    @Override
    public SubcategoryDto create(SubcategorySaveDto subcategoryBody) throws DataNotFoundException {
        CategoryDomain category = categoryPort.findById(subcategoryBody.getCategoryId())
                .orElseThrow(getCategoryNotFound(subcategoryBody));

        SubcategoryDomain subcategorySave = subcategoryMapper.toDomain(subcategoryBody);

        subcategorySave.setKeyword(new StringHelper().slugsKeywords(subcategoryBody.getName()));
        subcategorySave.setCategory(category);
        subcategorySave.setState(State.ACTIVE.getValue());
        subcategorySave.setCreatedAt(LocalDateTime.now());

        return subcategoryMapper.toDto(subcategoryPort.save(subcategorySave));
    }

    @Override
    public SubcategoryDto edit(Long id, SubcategorySaveDto subcategoryBody) throws DataNotFoundException {
        SubcategoryDomain subcategoryDb = subcategoryPort.findById(id)
                .orElseThrow(getSubcategoryNotFound(id));

        CategoryDomain category = categoryPort.findById(subcategoryBody.getCategoryId())
                .orElseThrow(getCategoryNotFound(subcategoryBody));

        subcategoryMapper.updateDomain(subcategoryDb, subcategoryBody);

        subcategoryDb.setKeyword(new StringHelper().slugsKeywords(subcategoryBody.getName()));
        subcategoryDb.setCategory(category);
        subcategoryDb.setUpdatedAt(LocalDateTime.now());

        return subcategoryMapper.toDto(subcategoryPort.save(subcategoryDb));
    }

    @Override
    public SubcategoryDto disabled(Long id) throws DataNotFoundException {
        SubcategoryDomain subcategoryDb = subcategoryPort.findById(id)
                .orElseThrow(getSubcategoryNotFound(id));

        subcategoryDb.setState(State.DISABLE.getValue());

        return subcategoryMapper.toDto(subcategoryPort.save(subcategoryDb));
    }


    @Override
    public PageResponse<SubcategoryDto> findAll(SubcategoryFilterDto filter) {
        SubcategoryFilterDomain filterDomain = subcategoryMapper.toFilter(filter);

        var subcategoryPage = subcategoryPort.findAll(filterDomain);

        var subcategories = subcategoryPage.getContent()
                .stream()
                .map(subcategoryMapper::toDto)
                .toList();

        return getPage(subcategoryPage, subcategories);
    }

    private Supplier<DataNotFoundException> getCategoryNotFound(SubcategorySaveDto body) {
        return () -> new DataNotFoundException("Categoria no encontrado para el id: " + body.getCategoryId());
    }

    private Supplier<DataNotFoundException> getSubcategoryNotFound(Long id) {
        return () -> new DataNotFoundException("Subcategoria no encontrado para el id: " + id);
    }

}
