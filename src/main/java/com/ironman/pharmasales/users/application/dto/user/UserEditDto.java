package com.ironman.pharmasales.users.application.dto.user;

import jakarta.validation.constraints.*;
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
public class UserEditDto {
    @NotBlank(message = "El campo nombre es requerido")
    @Size(min = 3)
    private String name;

    private String lastName;

    @NotBlank(message = "El campo email es requerido")
    @Email(message = "Ingrese un email valido")
    private String email;

    @NotNull(message = "El campo perfil es requerido")
    @Positive(message = "El campo perfil debe ser un numero positivo")
    private Long profileId;
}
