package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.subcategory.*;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, StateMapper.class}

)
public interface SubcategoryMapper {

    SubcategoryDto toDto(SubcategoryDomain subcategory);

    @Mapping(target = "categoryId", source = "subcategory.id")
    SubcategorySmallDto toSmallDto(SubcategoryDomain subcategory);
    SubcategoryMediumDto toMediumDto(SubcategoryDomain subcategory);

    SubcategoryDomain toDomain(SubcategorySaveDto subcategoryDto);

    void updateDomain(@MappingTarget SubcategoryDomain subcategory, SubcategorySaveDto subcategoryDto);

    @Mapping(target = "createdAtFrom", qualifiedByName = "localDateToString")
    @Mapping(target = "createdAtTo", qualifiedByName = "localDateToString")
    SubcategoryFilterDomain toFilter(SubcategoryFilterDto filter);

    @Named("localDateToString")
    default String localDateToString(java.time.LocalDate localDate) {
        return localDate != null ? localDate.toString() : null;
    }

}
