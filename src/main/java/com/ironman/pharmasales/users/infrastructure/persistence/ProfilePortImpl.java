package com.ironman.pharmasales.users.infrastructure.persistence;

import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.persistence.page.PageProcessor;
import com.ironman.pharmasales.users.domain.model.profile.ProfileDomain;
import com.ironman.pharmasales.users.domain.model.profile.ProfileFilterDomain;
import com.ironman.pharmasales.users.domain.port.ProfilePort;
import com.ironman.pharmasales.users.infrastructure.persistence.entity.Profile;
import com.ironman.pharmasales.users.infrastructure.persistence.enums.ProfileSortField;
import com.ironman.pharmasales.users.infrastructure.persistence.mapper.ProfileEntityMapper;
import com.ironman.pharmasales.users.infrastructure.persistence.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProfilePortImpl extends PageProcessor<ProfileDomain> implements ProfilePort {
    private final ProfileRepository profileRepository;
    private final ProfileEntityMapper profileMapper;

    @Override
    public List<ProfileDomain> findAll() {
        return ((List<Profile>) profileRepository.findAll())
                .stream()
                .map(profileMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileDomain> findByState(String state) {
        return profileRepository.findByState(state)
                .stream()
                .map(profileMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProfileDomain> findById(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::toDomain);
    }

    @Override
    public ProfileDomain save(ProfileDomain domain) {
        Profile profile = profileMapper.toEntity(domain);

        return profileMapper.toDomain(profileRepository.save(profile));
    }

    @Override
    public PageResponse<ProfileDomain> findAll(ProfileFilterDomain filter) {
        Sort sort = getSort(filter.getSort(), filter.getDirection());

        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), sort);

        Page<Profile> profilePage = profileRepository.findAllPaginated(
                filter.getName(),
                filter.getDescription(),
                filter.getState(),
                filter.getCreatedAtFrom(),
                filter.getCreatedAtTo(),
                pageable
        );

        var profiles = profilePage
                .stream()
                .map(profileMapper::toDomain)
                .toList();

        return getPageBuild(profilePage, profiles);
    }

    private Sort getSort(String sortField, String sortDirection) {
        String sortColumn = ProfileSortField.getSqlName(sortField);

        Sort.Direction direction = Sort.Direction
                .fromOptionalString(sortDirection)
                .orElse(Sort.Direction.ASC);

        return Sort.by(direction, sortColumn);
    }

}
