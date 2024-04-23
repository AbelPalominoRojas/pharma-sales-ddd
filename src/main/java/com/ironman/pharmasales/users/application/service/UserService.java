package com.ironman.pharmasales.users.application.service;

import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.users.application.dto.user.*;

import java.util.List;

public interface UserService {
    List<UserSmallDto> findAll();

    UserDto findById(Long id) throws DataNotFoundException;

    UserDto create(UserCreateDto userCreateDto) throws DataNotFoundException;

    UserDto edit(Long id, UserEditDto userEditDto) throws DataNotFoundException;

    UserDto disabled(Long id) throws DataNotFoundException;

    UserDto findByEmail(String email) throws DataNotFoundException;

    PageResponse<UserDto> findAll(UserFilterDto filter);

    UserSecurityDto login(AuthDto authDto) throws DataNotFoundException;
}
