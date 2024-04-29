package com.ironman.pharmasales.users.application.mapper;

import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import com.ironman.pharmasales.users.application.dto.user.*;
import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import com.ironman.pharmasales.users.domain.model.user.UserFilterDomain;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class, ProfileMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface UserMapper {

    UserDto toDto(UserDomain user);

    @InheritConfiguration
    @Mapping(target = "security", ignore = true)
    UserSecurityDto toSecurityDto(UserDomain user);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getUserFullName")
    UserMediumDto toMediumDto(UserDomain user);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getUserFullName")
    @Mapping(target = "profileId", source = "profile.id")
    UserSmallDto toSmallDto(UserDomain user);

    @Mapping(target = "profile.id", source = "profileId")
    UserDomain toDomain(UserCreateDto dto);

    @Mapping(target = "profile.id", source = "profileId")
    void updateDomain(@MappingTarget UserDomain user, UserEditDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    UserFilterDomain toFilter(UserFilterDto filter);

    @Named("getUserFullName")
    default String getUserFullName(UserDomain user) {
        String fullName = user.getName() + " " + user.getLastName();

        return fullName.strip();
    }

}
