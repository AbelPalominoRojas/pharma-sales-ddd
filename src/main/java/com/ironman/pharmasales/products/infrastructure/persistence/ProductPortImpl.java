package com.ironman.pharmasales.products.infrastructure.persistence;

import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import com.ironman.pharmasales.products.domain.model.product.ProductFilterDomain;
import com.ironman.pharmasales.products.domain.port.ProductPort;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Product;
import com.ironman.pharmasales.products.infrastructure.persistence.enums.ProductSortField;
import com.ironman.pharmasales.products.infrastructure.persistence.mapper.ProductEntityMapper;
import com.ironman.pharmasales.products.infrastructure.persistence.repository.ProductRepository;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductPortImpl extends PageProcessor<ProductDomain> implements ProductPort {

    private final ProductRepository productRepository;
    private final ProductEntityMapper productMapper;

    @Override
    public List<ProductDomain> findAll() {
        return ((List<Product>) productRepository.findAll())
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public List<ProductDomain> findByState(String state) {
        return productRepository.findByState(state)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<ProductDomain> findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public ProductDomain save(ProductDomain ProductDomain) {
        return productMapper.toDomain(
                productRepository.save(
                        productMapper.toEntity(ProductDomain)
                )
        );
    }

    @Override
    public PageResponse<ProductDomain> findAll(ProductFilterDomain filter) {
        Sort sort = getSort(filter.getSort(), filter.getDirection());
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        Page<Product> productPage = productRepository.findAllPaginated(
                filter.getName(),
                filter.getDescription(),
                filter.getPresentation(),
                filter.getStock(),
                filter.getSubcategoryId(),
                filter.getState(),
                filter.getCreatedAtFrom(),
                filter.getCreatedAtTo(),
                pageable
        );


        var products = productPage.getContent()
                .stream()
                .map(productMapper::toDomain)
                .toList();

        return getPageBuild(productPage, products);
    }

    @Override
    public List<ProductDomain> search(String searchText) {
        return productRepository.search(searchText)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    private Sort getSort(String sortField, String sortDirection) {
        String sortColumn = ProductSortField.getSqlName(sortField);

        Sort.Direction direction = Sort.Direction
                .fromOptionalString(sortDirection)
                .orElse(Sort.Direction.ASC);

        return Sort.by(direction, sortColumn);
    }
}
