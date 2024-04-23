package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.subcategory.*;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, StateMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface SubcategoryMapper {

    SubcategoryDto toDto(SubcategoryDomain subcategory);

    @Mapping(target = "categoryId", source = "category.id")
    SubcategorySmallDto toSmallDto(SubcategoryDomain subcategory);

    SubcategoryMediumDto toMediumDto(SubcategoryDomain subcategory);

    SubcategoryDomain toDomain(SubcategorySaveDto dto);

    void updateDomain(@MappingTarget SubcategoryDomain subcategory, SubcategorySaveDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    SubcategoryFilterDomain toFilter(SubcategoryFilterDto filter);
}
