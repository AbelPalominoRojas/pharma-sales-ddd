package com.ironman.pharmasales.users.domain.port;

import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import com.ironman.pharmasales.users.domain.model.user.UserFilterDomain;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    List<UserDomain> findAll();

    List<UserDomain> findByState(String state);

    Optional<UserDomain> findById(Long id);

    Optional<UserDomain> findByEmail(String email);

    UserDomain save(UserDomain domain);

    PageResponse<UserDomain> findAll(UserFilterDomain filter);
}
