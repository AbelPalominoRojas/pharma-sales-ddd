package com.ironman.pharmasales.users.application.mapper;

import com.ironman.pharmasales.shared.application.state.mapper.StateMapper;
import com.ironman.pharmasales.users.application.dto.profile.ProfileDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileFilterDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSaveDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSmallDto;
import com.ironman.pharmasales.users.domain.model.profile.ProfileDomain;
import com.ironman.pharmasales.users.domain.model.profile.ProfileFilterDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {StateMapper.class},
        imports = {com.ironman.pharmasales.shared.application.date.DateHelper.class}
)
public interface ProfileMapper {
    ProfileDto toDto(ProfileDomain profile);

    ProfileSmallDto toSmallDto(ProfileDomain profile);

    ProfileDomain toDomain(ProfileSaveDto dto);

    void updateDomain(@MappingTarget ProfileDomain profile, ProfileSaveDto dto);

    @Mapping(target = "createdAtFrom", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtFrom()))")
    @Mapping(target = "createdAtTo", expression = "java(new DateHelper().localDateToString(filter.getCreatedAtTo()))")
    ProfileFilterDomain toFilter(ProfileFilterDto filter);

}
