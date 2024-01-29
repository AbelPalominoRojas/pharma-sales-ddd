package com.ironman.pharmasales.products.application.service;

import com.ironman.pharmasales.products.application.dto.product.*;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;

public interface ProductService {
    List<ProductSmallDto> findAll();

    ProductDto findById(Long id) throws DataNotFoundException;

    ProductDto create(ProductSaveDto productBody) throws DataNotFoundException;

    ProductDto edit(Long id, ProductSaveDto productBody) throws DataNotFoundException;

    ProductDto disabled(Long id) throws DataNotFoundException;

    PageResponse<ProductDto> findAll(ProductFilterDto filter);

    List<ProductMediumDto> search(String searchText);
}
