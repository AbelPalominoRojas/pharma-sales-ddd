package com.ironman.pharmasales.products.domain.port;

import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import com.ironman.pharmasales.products.domain.model.product.ProductFilterDomain;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface ProductPort {
    List<ProductDomain> findAll();

    List<ProductDomain> findByState(String state);

    Optional<ProductDomain> findById(Long id);

    ProductDomain save(ProductDomain ProductDomain);

    PageResponse<ProductDomain> findAll(ProductFilterDomain filter);

    List<ProductDomain> search(String searchText);
}
