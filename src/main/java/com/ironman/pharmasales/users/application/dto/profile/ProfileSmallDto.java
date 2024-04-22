package com.ironman.pharmasales.users.application.dto.profile;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSmallDto {
    private Long id;
    private String name;
}
