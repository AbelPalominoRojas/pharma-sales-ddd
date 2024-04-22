package com.ironman.pharmasales.users.application.dto.profile;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private String name;
    private String description;
    private String state;
    private String createdAt;
    private String updatedAt;
}
