package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.category.CategoryDto;
import com.ironman.pharmasales.products.application.dto.category.CategoryFilterDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySaveDto;
import com.ironman.pharmasales.products.application.dto.category.CategorySmallDto;
import com.ironman.pharmasales.products.domain.model.category.CategoryDomain;
import com.ironman.pharmasales.products.domain.model.category.CategoryFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface CategoryMapper {

    CategoryDto toDto(CategoryDomain category);

    CategorySmallDto toSmallDto(CategoryDomain category);

    CategoryDomain toDomain(CategorySaveDto dto);

    void updateDomain(@MappingTarget CategoryDomain category, CategorySaveDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    CategoryFilterDomain toFilter(CategoryFilterDto filter);
}
