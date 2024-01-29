package com.ironman.pharmasales.products.infrastructure.persistence;

import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.model.category.CategoryFilterDomain;
import com.ironman.pharmasales.products.domain.port.CategoryPort;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Category;
import com.ironman.pharmasales.products.infrastructure.persistence.mapper.CategoryEntityMapper;
import com.ironman.pharmasales.products.infrastructure.persistence.repository.CategoryRepository;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CategoryPortImpl extends PageProcessor<CategoryDomain> implements CategoryPort{

    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryMapper;

    @Override
    public List<CategoryDomain> findAll() {
        return ((List<Category>) categoryRepository.findAll())
                .stream()
                .map(categoryMapper::toDomain)
                .toList();
    }

    @Override
    public List<CategoryDomain> findByState(String state) {
        return categoryRepository.findByState(state)
                .stream()
                .map(categoryMapper::toDomain)
                .toList();
    }

    @Override
    public List<CategoryDomain> searchByState(String state) {
        return categoryRepository.searchByState(state)
                .stream()
                .map(categoryMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<CategoryDomain> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDomain);
    }

    @Override
    public CategoryDomain save(CategoryDomain categoryDomain) {
        return categoryMapper.toDomain(
                categoryRepository.save(
                        categoryMapper.toEntity(categoryDomain)
                )
        );
    }

    @Override
    public PageResponse<CategoryDomain> findAll(CategoryFilterDomain filter) {
        Pageable pageableRequest = PageRequest.of(filter.getPage() -1, filter.getSize());

        Page<Category> categoryPage = categoryRepository.findAll(pageableRequest);

        var categories = categoryPage.getContent()
                .stream()
                .map(categoryMapper::toDomain)
                .toList();

        return getPageBuild(categoryPage, categories);
    }


}
