package com.ironman.pharmasales.clients.infrastructure.persistence.mapper;

import com.ironman.pharmasales.clients.domain.model.documenttype.DocumentTypeDomain;
import com.ironman.pharmasales.clients.infrastructure.persistence.entity.DocumentType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentTypeEntityMapper {

    DocumentTypeDomain toDomain(DocumentType entity);

    DocumentType toEntity(DocumentTypeDomain domain);
}
