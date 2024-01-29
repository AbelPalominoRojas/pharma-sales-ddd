package com.ironman.pharmasales.products.infrastructure.persistence;

import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.products.domain.port.SubcategoryPort;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Subcategory;
import com.ironman.pharmasales.products.infrastructure.persistence.mapper.SubcategoryEntityMapper;
import com.ironman.pharmasales.products.infrastructure.persistence.repository.SubcategoryRepository;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class SubcategoryPortImpl extends PageProcessor<SubcategoryDomain> implements SubcategoryPort {

    private final SubcategoryRepository subcategoryRepository;
    private final SubcategoryEntityMapper subcategoryMapper;

    @Override
    public List<SubcategoryDomain> findAll() {
        return ((List<Subcategory>) subcategoryRepository.findAll())
                .stream()
                .map(subcategoryMapper::toDomain)
                .toList();
    }


    @Override
    public List<SubcategoryDomain> findByState(String state) {
        return subcategoryRepository.findByState(state)
                .stream()
                .map(subcategoryMapper::toDomain)
                .toList();
    }


    @Override
    public Optional<SubcategoryDomain> findById(Long id) {
        return subcategoryRepository.findById(id)
                .map(subcategoryMapper::toDomain);
    }


    @Override
    public SubcategoryDomain save(SubcategoryDomain SubcategoryDomain) {
        return subcategoryMapper.toDomain(
                subcategoryRepository.save(
                        subcategoryMapper.toEntity(SubcategoryDomain)
                )
        );
    }


    @Override
    public PageResponse<SubcategoryDomain> findAll(SubcategoryFilterDomain filter) {
        Pageable pageableRequest = PageRequest.of(filter.getPage(), filter.getSize());

        Page<Subcategory> subcategoryPage = subcategoryRepository.paginationFilter(pageableRequest, filter);

        var subcategories = subcategoryPage.getContent()
                .stream()
                .map(subcategoryMapper::toDomain)
                .toList();

        return getPageBuild(subcategoryPage, subcategories);
    }
}
