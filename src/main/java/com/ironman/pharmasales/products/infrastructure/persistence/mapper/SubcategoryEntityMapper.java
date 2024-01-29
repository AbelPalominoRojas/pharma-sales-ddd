package com.ironman.pharmasales.products.infrastructure.persistence.mapper;

import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {CategoryEntityMapper.class})
public interface SubcategoryEntityMapper {
    SubcategoryDomain toDomain(Subcategory entity);

    @Mapping(target = "categoryId", source = "category.id")
    Subcategory toEntity(SubcategoryDomain domain);
}
