package com.ironman.pharmasales.old.application.service;

import com.ironman.pharmasales.old.application.dto.user.AuthDto;
import com.ironman.pharmasales.old.application.dto.user.UserCreateDto;
import com.ironman.pharmasales.old.application.dto.user.UserDto;
import com.ironman.pharmasales.old.application.dto.user.UserSecurityDto;
import com.ironman.pharmasales.old.shared.exception.DataNotFoundException;

public interface UserService {

    UserDto create(UserCreateDto userCreateDto) throws DataNotFoundException;

    UserSecurityDto login(AuthDto authDto) throws DataNotFoundException;
}
