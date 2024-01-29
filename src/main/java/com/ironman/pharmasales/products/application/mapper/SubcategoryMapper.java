package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.subcategory.*;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryDomain;
import com.ironman.pharmasales.products.domain.model.subcategory.SubcategoryFilterDomain;
import com.ironman.pharmasales.products.infrastructure.persistence.entity.Subcategory;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CategoryMapper.class, StateMapper.class}

)
public interface SubcategoryMapper {

    SubcategoryDto toDto(SubcategoryDomain subcategory);

    SubcategorySmallDto toSmallDto(SubcategoryDomain subcategory);
    SubcategoryMediumDto toMediumDto(SubcategoryDomain subcategory);

    SubcategoryDomain toDomain(SubcategorySaveDto subcategoryDto);

    void updateDomain(@MappingTarget SubcategoryDomain subcategory, SubcategorySaveDto subcategoryDto);

    SubcategoryFilterDomain toFilter(SubcategoryFilterDto filter);

}
