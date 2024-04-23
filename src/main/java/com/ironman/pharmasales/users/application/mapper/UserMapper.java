package com.ironman.pharmasales.users.application.mapper;

import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import com.ironman.pharmasales.users.application.dto.user.*;
import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import com.ironman.pharmasales.users.domain.model.user.UserFilterDomain;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface UserMapper {

    UserDto toDto(UserDomain domain);

    @InheritConfiguration
    @Mapping(target = "security", ignore = true)
    UserSecurityDto toSecurityDto(UserDomain domain);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getUserFullName")
    UserSmallDto toSmallDto(UserDomain domain);

    UserDomain toDomain(UserCreateDto dto);

    void updateDomain(@MappingTarget UserDomain domain, UserEditDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    UserFilterDomain toFilter(UserFilterDto filter);

    @Named("getUserFullName")
    default String getUserFullName(UserDomain user) {
        String fullName = user.getName() + " " + user.getLastName();

        return fullName.strip();
    }

}
