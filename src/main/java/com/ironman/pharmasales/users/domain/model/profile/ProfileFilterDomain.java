package com.ironman.pharmasales.users.domain.model.profile;

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
public class ProfileFilterDomain extends PageableRequest {
    private String name;
    private String description;
    private String state;
    private String createdAtFrom;
    private String createdAtTo;
}
