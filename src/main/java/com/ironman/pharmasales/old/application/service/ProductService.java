package com.ironman.pharmasales.old.application.service;

import com.ironman.pharmasales.old.application.dto.product.ProductDto;
import com.ironman.pharmasales.old.application.dto.product.ProductFilterDto;
import com.ironman.pharmasales.old.application.dto.product.ProductMediumDto;
import com.ironman.pharmasales.old.application.dto.product.ProductSaveDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAll();

    ProductDto findById(Long id) throws DataNotFoundException;

    ProductDto create(ProductSaveDto productBody) throws DataNotFoundException;

    ProductDto edit(Long id, ProductSaveDto productBody) throws DataNotFoundException;

    ProductDto disabled(Long id) throws DataNotFoundException;

    Page<ProductDto> paginationFilter(Pageable pageable, Optional<ProductFilterDto> filter);

    List<ProductMediumDto> search(String searchText);
}
