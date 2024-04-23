package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.product.*;
import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import com.ironman.pharmasales.products.domain.model.product.ProductFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class, SubcategoryMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface ProductMapper {

    ProductDto toDto(ProductDomain product);

    ProductSmallDto toSmallDto(ProductDomain product);

    ProductMediumDto toMediumDto(ProductDomain product);

    @Mapping(target = "subcategory.id", source = "subcategoryId")
    ProductDomain toDomain(ProductSaveDto dto);

    @Mapping(target = "subcategory.id", source = "subcategoryId")
    void updateDomain(@MappingTarget ProductDomain product, ProductSaveDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    ProductFilterDomain toFilter(ProductFilterDto filter);

}
