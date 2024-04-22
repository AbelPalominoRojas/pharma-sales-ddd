package com.ironman.pharmasales.users.application.service;

import com.ironman.pharmasales.users.application.dto.user.AuthDto;
import com.ironman.pharmasales.users.application.dto.user.UserCreateDto;
import com.ironman.pharmasales.users.application.dto.user.UserDto;
import com.ironman.pharmasales.users.application.dto.user.UserSecurityDto;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;

public interface UserService {

    UserDto create(UserCreateDto userCreateDto) throws DataNotFoundException;

    UserSecurityDto login(AuthDto authDto) throws DataNotFoundException;
}
