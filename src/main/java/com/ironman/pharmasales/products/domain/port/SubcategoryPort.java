package com.ironman.pharmasales.products.domain.port;

import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.shared.domain.page.PageResponse;

import java.util.List;
import java.util.Optional;

public interface SubcategoryPort {
    List<SubcategoryDomain> findAll();

    List<SubcategoryDomain> findByState(String state);

    Optional<SubcategoryDomain> findById(Long id);

    SubcategoryDomain save(SubcategoryDomain SubcategoryDomain);

    PageResponse<SubcategoryDomain> findAll(SubcategoryFilterDomain filter);
}
