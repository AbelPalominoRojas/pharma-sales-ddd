package com.ironman.pharmasales.products.application.service.impl;

import com.ironman.pharmasales.products.application.dto.product.*;
import com.ironman.pharmasales.products.application.mapper.ProductMapper;
import com.ironman.pharmasales.products.application.service.ProductService;
import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import com.ironman.pharmasales.products.domain.model.product.ProductFilterDomain;
import com.ironman.pharmasales.products.domain.port.ProductPort;
import com.ironman.pharmasales.products.domain.port.SubcategoryPort;
import com.ironman.pharmasales.shared.application.page.PageBuild;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl extends PageBuild<ProductDto> implements ProductService {

    private final ProductPort productPort;
    private final SubcategoryPort subcategoryPort;
    private final ProductMapper productMapper;

    @Override
    public List<ProductSmallDto> findAll() {
        return productPort.findByState(State.ACTIVE.getValue())
                .stream()
                .map(productMapper::toSmallDto)
                .toList();

    }

    @Override
    public ProductDto findById(Long id) throws DataNotFoundException {
        return productPort.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(getProductNotFound(id));
    }

    @Override
    public ProductDto create(ProductSaveDto productBody) throws DataNotFoundException {
        var subcategory = subcategoryPort.findById(productBody.getSubcategoryId())
                .orElseThrow(getSubcategoryNotFound(productBody));

        ProductDomain productSave = productMapper.toDomain(productBody);
        productSave.setSubcategory(subcategory);
        productSave.setState(State.ACTIVE.getValue());
        productSave.setCreatedAt(LocalDateTime.now());

        return productMapper.toDto(productPort.save(productSave));
    }

    @Override
    public ProductDto edit(Long id, ProductSaveDto productBody) throws DataNotFoundException {
        var subcategory = subcategoryPort.findById(productBody.getSubcategoryId())
                .orElseThrow(getSubcategoryNotFound(productBody));

        ProductDomain productDb = productPort.findById(id)
                .orElseThrow(getProductNotFound(id));

        productMapper.updateDomain(productDb, productBody);
        productDb.setSubcategory(subcategory);
        productDb.setUpdatedAt(LocalDateTime.now());

        return productMapper.toDto(productPort.save(productDb));
    }

    @Override
    public ProductDto disabled(Long id) throws DataNotFoundException {
        ProductDomain productDb = productPort.findById(id)
                .orElseThrow(getProductNotFound(id));

        productDb.setState(State.DISABLE.getValue());

        return productMapper.toDto(productPort.save(productDb));
    }

    @Override
    public PageResponse<ProductDto> findAll(ProductFilterDto filter) {
        ProductFilterDomain filterDomain = productMapper.toFilter(filter);

        var productPage = productPort.findAll(filterDomain);

        var products = productPage.getContent()
                .stream()
                .map(productMapper::toDto)
                .toList();

        return getPage(productPage, products);
    }

    @Override
    public List<ProductMediumDto> search(String searchText) {
        return productPort.search(searchText)
                .stream()
                .map(productMapper::toMediumDto)
                .toList();
    }

    private Supplier<DataNotFoundException> getProductNotFound(Long id) {
        return () -> new DataNotFoundException("Producto no encontrado para el id: " + id);
    }

    private Supplier<DataNotFoundException> getSubcategoryNotFound(ProductSaveDto body) {
        return () -> new DataNotFoundException("Subcategoria no encontrado para el id: " + body.getSubcategoryId());
    }
}

