package com.ironman.pharmasales.clients.application.mapper;

import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeFilterDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSaveDto;
import com.ironman.pharmasales.clients.application.dto.documenttype.DocumentTypeSmallDto;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface DocumentTypeMapper {
    int IS_TRUE = 1;
    boolean IS_ACTIVE = true;

    @Mapping(target = "isSizeExact", expression = "java(domain.getIsSizeExact() == IS_TRUE)")
    @Mapping(target = "isNumeric", expression = "java(domain.getIsNumeric() == IS_TRUE)")
    DocumentTypeDto toDto(DocumentTypeDomain domain);

    DocumentTypeSmallDto toSmallDto(DocumentTypeDomain domain);

    @Mapping(target = "isSizeExact", expression = "java(dto.getIsSizeExact() == IS_ACTIVE ? 1: 0)")
    @Mapping(target = "isNumeric", expression = "java(dto.getIsNumeric() == IS_ACTIVE ? 1: 0)")
    DocumentTypeDomain toDomain(DocumentTypeSaveDto dto);

    @InheritConfiguration
    void updateDomain(@MappingTarget DocumentTypeDomain domain, DocumentTypeSaveDto dto);

    @Mapping(target = "isSizeExact", source = ".", qualifiedByName = "getIsSizeExact")
    @Mapping(target = "isNumeric", source = ".", qualifiedByName = "getIsNumeric")
    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    DocumentTypeFilterDomain toFilter(DocumentTypeFilterDto filter);

    @Named("getIsSizeExact")
    default Integer getIsSizeExact(DocumentTypeFilterDto filterDto) {
        return Optional.ofNullable(filterDto)
                .map(DocumentTypeFilterDto::getIsSizeExact)
                .map(isExact -> isExact == IS_ACTIVE ? 1 : 0)
                .orElse(null);
    }

    @Named("getIsNumeric")
    default Integer getIsNumeric(DocumentTypeFilterDto filterDto) {
        return Optional.ofNullable(filterDto)
                .map(DocumentTypeFilterDto::getIsNumeric)
                .map(isNumeric -> isNumeric == IS_ACTIVE ? 1 : 0)
                .orElse(null);
    }

}
