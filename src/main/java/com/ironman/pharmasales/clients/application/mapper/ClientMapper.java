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

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    ClientMediumDto toMediumDto(ClientDomain client);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    ClientSmallDto toSmallDto(ClientDomain client);

    ClientDomain toDomain(ClientSaveDto clientDto);

    void updateDomain(@MappingTarget ClientDomain clientDomain, ClientSaveDto clientDto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    ClientFilterDomain toFilter(ClientFilterDto filter);


    @Named("getFullName")
    default String getFullName(ClientDomain client) {
        String fullName = client.getName() + " " + client.getLastName();

        return fullName.strip();
    }
}
