package com.ironman.pharmasales.users.application.dto.user;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterDto extends PageableRequest {
    private String name;
    private String lastName;
    private String email;
    private Long profileId;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
