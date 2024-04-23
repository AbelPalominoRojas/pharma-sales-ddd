package com.ironman.pharmasales.users.infrastructure.persistence;

import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import com.ironman.pharmasales.users.domain.model.user.UserFilterDomain;
import com.ironman.pharmasales.users.domain.port.UserPort;
import com.ironman.pharmasales.users.infrastructure.persistence.entity.UserEntity;
import com.ironman.pharmasales.users.infrastructure.persistence.enums.UserSortField;
import com.ironman.pharmasales.users.infrastructure.persistence.mapper.UserEntityMapper;
import com.ironman.pharmasales.users.infrastructure.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserPortImpl extends PageProcessor<UserDomain> implements UserPort {
    private final UserRepository userRepository;
    private final UserEntityMapper userMapper;

    @Override
    public List<UserDomain> findAll() {
        return ((List<UserEntity>) userRepository.findAll())
                .stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserDomain> findByState(String state) {
        return userRepository.findByState(state)
                .stream()
                .map(userMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UserDomain> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public UserDomain save(UserDomain domain) {
        UserEntity user = userMapper.toEntity(domain);

        return userMapper.toDomain(userRepository.save(user));
    }

    @Override
    public PageResponse<UserDomain> findAll(UserFilterDomain filter) {
        Sort sort = getSort(filter.getSort(), filter.getDirection());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        var userPage = userRepository.findAllPaginated(
                filter.getName(),
                filter.getLastName(),
                filter.getEmail(),
                filter.getProfileId(),
                filter.getState(),
                filter.getCreatedAtFrom(),
                filter.getCreatedAtTo(),
                pageable
        );

        var users = userPage
                .stream()
                .map(userMapper::toDomain)
                .toList();

        return getPageBuild(userPage, users);
    }

    private Sort getSort(String sortField, String sortDirection) {
        String sortColumn = UserSortField.getSqlName(sortField);
        Sort.Direction direction = Sort.Direction
                .fromOptionalString(sortDirection)
                .orElse(Sort.Direction.ASC);

        return Sort.by(direction, sortColumn);
    }
}
