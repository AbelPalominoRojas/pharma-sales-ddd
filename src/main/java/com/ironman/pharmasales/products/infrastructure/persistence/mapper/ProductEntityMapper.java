package com.ironman.pharmasales.products.infrastructure.persistence.mapper;

import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {SubcategoryEntityMapper.class})
public interface ProductEntityMapper {

    ProductDomain toDomain(Product entity);

    @Mapping(target = "subcategoryId", source = "subcategory.id")
    Product toEntity(ProductDomain domain);
}
