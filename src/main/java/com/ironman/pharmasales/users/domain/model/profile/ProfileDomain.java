package com.ironman.pharmasales.users.domain.model.profile;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDomain {
    private Long id;
    private String name;
    private String description;
    private String state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
