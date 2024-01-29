package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.category.CategoryDto;
import com.ironman.pharmasales.products.application.dto.category.CategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySaveDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySimpleDto;
import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.model.category.CategoryFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {StateMapper.class})
public interface CategoryMapper {

    CategoryDto toDto(CategoryDomain category);
    CategorySimpleDto toSimpleDto(CategoryDomain category);

    CategoryDomain toDomain(CategorySaveDto dto);
    void update(@MappingTarget CategoryDomain category, CategorySaveDto dto);

    CategoryFilterDomain toFilter(CategoryFilterDto filter);
}
