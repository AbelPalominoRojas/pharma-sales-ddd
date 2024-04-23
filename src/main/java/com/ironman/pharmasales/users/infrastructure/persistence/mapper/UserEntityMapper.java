package com.ironman.pharmasales.users.infrastructure.persistence.mapper;

import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import com.ironman.pharmasales.users.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ProfileEntityMapper.class}
)
public interface UserEntityMapper {
    UserDomain toDomain(UserEntity entity);

    @Mapping(target = "profileId", source = "profile.id")
    UserEntity toEntity(UserDomain domain);
}
