package com.ironman.pharmasales.users.application.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSaveDto {
    @NotBlank(message = "El campo nombre es requerido")
    private String name;
    private String description;
}
