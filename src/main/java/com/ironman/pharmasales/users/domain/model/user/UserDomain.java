package com.ironman.pharmasales.users.domain.model.user;

import com.ironman.pharmasales.users.domain.model.profile.ProfileDomain;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private Long profileId;
    private ProfileDomain profile;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
