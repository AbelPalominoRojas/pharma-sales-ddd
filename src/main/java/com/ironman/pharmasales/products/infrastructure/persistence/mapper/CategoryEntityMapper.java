package com.ironman.pharmasales.products.infrastructure.persistence.mapper;

import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryEntityMapper {
    CategoryDomain toDomain(Category entity);

    Category toEntity(CategoryDomain domain);
}
