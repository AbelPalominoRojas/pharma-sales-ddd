package com.ironman.pharmasales.products.application.mapper;

import com.ironman.pharmasales.products.application.dto.product.*;
import com.ironman.pharmasales.products.domain.model.product.ProductDomain;
import com.ironman.pharmasales.products.domain.model.product.ProductFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class, SubcategoryMapper.class}
)
public interface ProductMapper {

    ProductDto toDto(ProductDomain product);


    ProductSmallDto toSmallDto(ProductDomain product);

    ProductMediumDto toMediumDto(ProductDomain product);

    ProductDomain toDomain(ProductSaveDto productDto);

    void updateDomain(@MappingTarget ProductDomain productDomain, ProductSaveDto productDto);

    ProductFilterDomain toFilter(ProductFilterDto filter);

}
