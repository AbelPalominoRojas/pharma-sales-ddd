package com.ironman.pharmasales.users.application.dto.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSmallDto {
    private Long id;
    private String fullName;
    private Long profileId;
}
