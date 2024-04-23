package com.ironman.pharmasales.users.application.service.impl;

import com.ironman.pharmasales.shared.application.page.PageBuild;
import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.web.security.JwtHelper;
import com.ironman.pharmasales.users.application.dto.user.*;
import com.ironman.pharmasales.users.application.mapper.UserMapper;
import com.ironman.pharmasales.users.application.service.UserService;
import com.ironman.pharmasales.users.domain.model.user.UserDomain;
import com.ironman.pharmasales.users.domain.port.UserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends PageBuild<UserDto> implements UserService {
    private final UserPort userPort;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    @Override
    public List<UserSmallDto> findAll() {
        return userPort.findAll()
                .stream()
                .map(userMapper::toSmallDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) throws DataNotFoundException {
        return userPort.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(getUserNotFound(id));
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) throws DataNotFoundException {
        UserDomain userSave = userMapper.toDomain(userCreateDto);

        userSave.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        userSave.setState(State.ACTIVE.getValue());
        userSave.setCreatedAt(LocalDateTime.now());

        return userMapper.toDto(userPort.save(userSave));
    }

    @Override
    public UserDto edit(Long id, UserEditDto userEditDto) throws DataNotFoundException {
        UserDomain userDomain = userPort.findById(id)
                .orElseThrow(getUserNotFound(id));

        userMapper.updateDomain(userDomain, userEditDto);
        userDomain.setUpdatedAt(LocalDateTime.now());

        return userMapper.toDto(userPort.save(userDomain));
    }

    @Override
    public UserDto disabled(Long id) throws DataNotFoundException {
        UserDomain userDomain = userPort.findById(id)
                .orElseThrow(getUserNotFound(id));

        userDomain.setState(State.DISABLE.getValue());

        return userMapper.toDto(userPort.save(userDomain));
    }

    @Override
    public UserDto findByEmail(String email) throws DataNotFoundException {
        return userPort.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new DataNotFoundException("Usuario " + email + " no se encuenta en el registro"));
    }

    @Override
    public PageResponse<UserDto> findAll(UserFilterDto filter) {
        var filterDomain = userMapper.toFilter(filter);
        var userPage = userPort.findAll(filterDomain);
        var users = userPage.getContent()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return getPage(userPage, users);
    }

    @Override
    public UserSecurityDto login(AuthDto authDto) throws DataNotFoundException {
        UserDomain user = userPort.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("Usuario " + authDto.getEmail() + " no se encuenta en el registro"));

        if (!passwordEncoder.matches(authDto.getPassword(), user.getPassword()))
            throw new DataNotFoundException("Usuario/Password no son correctos");

        if (user.getState().equalsIgnoreCase(State.DISABLE.getValue()))
            throw new DataNotFoundException("Usuario esta deshabilitado. Comuniquese con el administrador del sistema");

        UserSecurityDto userSecurity = userMapper.toSecurityDto(user);

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getProfile().getName());
        UserDetails userDetails = new User(user.getEmail(), user.getPassword(), List.of(grantedAuthority));

        userSecurity.setSecurity(jwtHelper.getSecurity(userDetails));

        return userSecurity;
    }

    private Supplier<DataNotFoundException> getUserNotFound(Long id) {
        return () -> new DataNotFoundException("Usuario no encontrado para el id: " + id);
    }
}
