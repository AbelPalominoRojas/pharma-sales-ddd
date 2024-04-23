package com.ironman.pharmasales.users.domain.port;

import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.users.domain.model.profile.ProfileDomain;
import com.ironman.pharmasales.users.domain.model.profile.ProfileFilterDomain;

import java.util.List;
import java.util.Optional;

public interface ProfilePort {
    List<ProfileDomain> findAll();

    List<ProfileDomain> findByState(String state);

    Optional<ProfileDomain> findById(Long id);

    ProfileDomain save(ProfileDomain domain);

    PageResponse<ProfileDomain> findAll(ProfileFilterDomain filter);
}
