package com.ironman.pharmasales.users.domain.model.user;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDomain extends PageableRequest {
    private String name;
    private String lastName;
    private String email;
    private Long profileId;
    private String state;
    private String createdAtFrom;
    private String createdAtTo;
}
