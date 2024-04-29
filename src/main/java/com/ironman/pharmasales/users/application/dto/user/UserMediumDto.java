package com.ironman.pharmasales.users.application.dto.user;

import com.ironman.pharmasales.users.application.dto.profile.ProfileSmallDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMediumDto {
    private Long id;
    private String fullName;
    private String email;
    private ProfileSmallDto profile;
}
