package com.ironman.pharmasales.users.application.service;

import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.users.application.dto.profile.ProfileDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileFilterDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSaveDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSmallDto;

import java.util.List;

public interface ProfileService {
    List<ProfileSmallDto> findAll();

    ProfileDto findById(Long id) throws DataNotFoundException;

    ProfileDto create(ProfileSaveDto ProfileBody);

    ProfileDto edit(Long id, ProfileSaveDto ProfileBody) throws DataNotFoundException;

    ProfileDto disabled(Long id) throws DataNotFoundException;

    PageResponse<ProfileDto> findAll(ProfileFilterDto filter);
}
