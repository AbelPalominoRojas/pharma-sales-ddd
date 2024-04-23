package com.ironman.pharmasales.users.application.service.impl;

import com.ironman.pharmasales.shared.application.page.PageBuild;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.users.application.dto.profile.ProfileDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileFilterDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSaveDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSmallDto;
import com.ironman.pharmasales.users.application.mapper.ProfileMapper;
import com.ironman.pharmasales.users.application.service.ProfileService;
import com.ironman.pharmasales.users.domain.model.profile.ProfileDomain;
import com.ironman.pharmasales.users.domain.model.profile.ProfileFilterDomain;
import com.ironman.pharmasales.users.domain.port.ProfilePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl extends PageBuild<ProfileDto> implements ProfileService {
    private final ProfilePort profilePort;
    private final ProfileMapper profileMapper;

    @Override
    public List<ProfileSmallDto> findAll() {
        return profilePort.findByState(State.ACTIVE.getValue())
                .stream()
                .map(profileMapper::toSmallDto)
                .toList();
    }

    @Override
    public ProfileDto findById(Long id) throws DataNotFoundException {
        return profilePort.findById(id)
                .map(profileMapper::toDto)
                .orElseThrow(getProfileNotFound(id));
    }

    @Override
    public ProfileDto create(ProfileSaveDto profileBody) {
        ProfileDomain profileDomain = profileMapper.toDomain(profileBody);
        profileDomain.setState(State.ACTIVE.getValue());
        profileDomain.setCreatedAt(LocalDateTime.now());

        return profileMapper.toDto(profilePort.save(profileDomain));
    }

    @Override
    public ProfileDto edit(Long id, ProfileSaveDto profileBody) throws DataNotFoundException {
        ProfileDomain profileDomain = profilePort.findById(id)
                .orElseThrow(getProfileNotFound(id));

        profileMapper.updateDomain(profileDomain, profileBody);
        profileDomain.setUpdatedAt(LocalDateTime.now());

        return profileMapper.toDto(profilePort.save(profileDomain));
    }

    @Override
    public ProfileDto disabled(Long id) throws DataNotFoundException {
        ProfileDomain profileDomain = profilePort.findById(id)
                .orElseThrow(getProfileNotFound(id));

        profileDomain.setState(State.DISABLE.getValue());

        return profileMapper.toDto(profilePort.save(profileDomain));
    }

    @Override
    public PageResponse<ProfileDto> findAll(ProfileFilterDto filter) {
        ProfileFilterDomain filterDomain = profileMapper.toFilter(filter);

        var profilePage = profilePort.findAll(filterDomain);

        var profiles = profilePage.getContent()
                .stream()
                .map(profileMapper::toDto)
                .toList();

        return getPage(profilePage, profiles);
    }

    private Supplier<DataNotFoundException> getProfileNotFound(Long id) {
        return () -> new DataNotFoundException("Perfil no encontrado para el id: " + id);
    }
}
