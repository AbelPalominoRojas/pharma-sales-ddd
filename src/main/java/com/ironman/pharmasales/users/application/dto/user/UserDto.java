package com.ironman.pharmasales.users.application.dto.user;

import com.ironman.pharmasales.shared.application.state.enums.State;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSmallDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private ProfileSmallDto profile;
    private Long profileId;
    private State state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
