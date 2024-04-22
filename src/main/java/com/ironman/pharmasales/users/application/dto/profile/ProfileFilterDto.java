package com.ironman.pharmasales.users.application.dto.profile;

import com.ironman.pharmasales.shared.domain.page.PageableRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileFilterDto extends PageableRequest {
    private String name;
    private String description;
    private String state;
    private LocalDate createdAtFrom;
    private LocalDate createdAtTo;
}
