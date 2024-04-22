package com.ironman.pharmasales.clients.infrastructure.persistence.mapper;

import com.ironman.pharmasales.clients.domain.model.client.ClientDomain;
import com.ironman.pharmasales.clients.infrastructure.persistence.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {DocumentTypeEntityMapper.class})
public interface ClientEntityMapper {

    ClientDomain toDomain(Client entity);

    @Mapping(target = "documentTypeId", source = "documentType.id")
    Client toEntity(ClientDomain domain);
}
