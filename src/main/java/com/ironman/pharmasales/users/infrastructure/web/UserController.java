package com.ironman.pharmasales.users.infrastructure.web;


import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.exception.model.ArgumentNotValidError;
import com.ironman.pharmasales.shared.domain.exception.model.GeneralError;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.web.constant.StatusCode;
import com.ironman.pharmasales.users.application.dto.user.*;
import com.ironman.pharmasales.users.application.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    ResponseEntity<List<UserSmallDto>> findAll() {
        List<UserSmallDto> users = userService.findAll();

        return ResponseEntity.ok(users);
    }


    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @GetMapping("/{id}")
    ResponseEntity<UserDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        UserDto user = userService.findById(id);

        return ResponseEntity.ok(user);
    }


    @SecurityRequirements(value = {})
    @ApiResponse(responseCode = StatusCode.CREATED)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto userBody) throws DataNotFoundException {
        UserDto user = userService.create(userBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @PutMapping("/{id}")
    ResponseEntity<UserDto> edit(@PathVariable("id") Long id, @Valid @RequestBody UserEditDto userBody) throws DataNotFoundException {
        UserDto user = userService.edit(id, userBody);

        return ResponseEntity.ok(user);
    }


    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @DeleteMapping("/{id}")
    ResponseEntity<UserDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        UserDto user = userService.disabled(id);

        return ResponseEntity.ok(user);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    ResponseEntity<PageResponse<UserDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "lastName", required = false) String lastName,
            @RequestParam(name = "email", required = false) String email,

            @Pattern(regexp = "^\\d*$", message = "El profileId debe contener solo dígitos")
            @RequestParam(name = "profileId", required = false) Long profileId,

            @Parameter(description = "El estado debe ser 'A' o 'E'")
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,

            @Parameter(description = "El campo createdAtFrom debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtFrom", required = false) LocalDate createdAtFrom,

            @Parameter(description = "El campo createdAtTo debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtTo", required = false) LocalDate createdAtTo,

            @Parameter(description = "El campo sort debe ser 'id', 'name', 'lastName', 'profileId' o 'createdAt'")
            @Pattern(regexp = "^(id|name|lastName|profileId|createdAt)$", message = "La dirección debe ser 'id', 'name', 'lastName', 'profileId' o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "La dirección debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        var filter = UserFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .lastName(lastName)
                .email(email)
                .profileId(profileId)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();

        var userPage = userService.findAll(filter);

        return ResponseEntity.ok(userPage);
    }

}
