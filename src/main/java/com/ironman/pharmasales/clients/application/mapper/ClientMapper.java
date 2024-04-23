package com.ironman.pharmasales.clients.application.mapper;

import com.ironman.pharmasales.clients.application.dto.client.*;
import com.ironman.pharmasales.clients.domain.model.client.ClientDomain;
import com.ironman.pharmasales.clients.domain.model.client.ClientFilterDomain;
import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class, DocumentTypeMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface ClientMapper {

    ClientDto toDto(ClientDomain client);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getClientFullName")
    ClientMediumDto toMediumDto(ClientDomain client);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getClientFullName")
    @Mapping(target = "documentTypeId", source = "documentType.id")
    ClientSmallDto toSmallDto(ClientDomain client);

    @Mapping(target = "documentType.id", source = "documentTypeId")
    ClientDomain toDomain(ClientSaveDto dto);

    @Mapping(target = "documentType.id", source = "documentTypeId")
    void updateDomain(@MappingTarget ClientDomain domain, ClientSaveDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    ClientFilterDomain toFilter(ClientFilterDto filter);


    @Named("getClientFullName")
    default String getClientFullName(ClientDomain client) {
        String fullName = client.getName() + " " + client.getLastName();

        return fullName.strip();
    }
}
