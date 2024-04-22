package com.ironman.pharmasales.users.infrastructure.persistence.mapper;

import com.ironman.pharmasales.users.domain.model.profile.ProfileDomain;
import com.ironman.pharmasales.users.infrastructure.persistence.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileEntityMapper {
    ProfileDomain toDomain(Profile entity);

    Profile toEntity(ProfileDomain domain);
}
