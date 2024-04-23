package com.ironman.pharmasales.users.infrastructure.web;


import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.exception.model.ArgumentNotValidError;
import com.ironman.pharmasales.shared.domain.exception.model.GeneralError;
import com.ironman.pharmasales.shared.domain.page.PageResponse;
import com.ironman.pharmasales.shared.infrastructure.web.constant.StatusCode;
import com.ironman.pharmasales.users.application.dto.profile.ProfileDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileFilterDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSaveDto;
import com.ironman.pharmasales.users.application.dto.profile.ProfileSmallDto;
import com.ironman.pharmasales.users.application.service.ProfileService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/profiles")
@RestController
public class ProfileController {
    private final ProfileService profileService;

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping
    ResponseEntity<List<ProfileSmallDto>> findAll() {
        List<ProfileSmallDto> profiles = profileService.findAll();

        return ResponseEntity.ok(profiles);
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
    ResponseEntity<ProfileDto> findById(@PathVariable("id") Long id) throws DataNotFoundException {
        ProfileDto profile = profileService.findById(id);

        return ResponseEntity.ok(profile);
    }


    @ApiResponse(responseCode = StatusCode.CREATED)
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
    @PostMapping
    ResponseEntity<ProfileDto> create(@Valid @RequestBody ProfileSaveDto profileBody) {
        ProfileDto profile = profileService.create(profileBody);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profile);
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
    ResponseEntity<ProfileDto> edit(@PathVariable("id") Long id, @Valid @RequestBody ProfileSaveDto profileBody) throws DataNotFoundException {
        ProfileDto profile = profileService.edit(id, profileBody);

        return ResponseEntity.ok(profile);
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
    ResponseEntity<ProfileDto> disabled(@PathVariable("id") Long id) throws DataNotFoundException {
        ProfileDto profile = profileService.disabled(id);

        return ResponseEntity.ok(profile);
    }

    @ApiResponse(responseCode = StatusCode.OK)
    @GetMapping("/page-filter")
    ResponseEntity<PageResponse<ProfileDto>> pageFilter(
            @NotNull(message = "El campo page es requerido")
            @Min(value = 1, message = "El número de página debe ser positivo")
            @RequestParam(name = "page", defaultValue = "1") int page,

            @NotNull(message = "El campo size es requerido")
            @Min(value = 1, message = "El tamaño de la página debe ser positivo")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description,

            @Parameter(description = "El estado debe ser 'A' o 'E'")
            @Pattern(regexp = "^[AE]$", message = "El estado debe ser 'A' o 'E'")
            @RequestParam(name = "state", required = false) String state,

            @Parameter(description = "El campo createdAtFrom debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtFrom", required = false) LocalDate createdAtFrom,

            @Parameter(description = "El campo createdAtTo debe estar en el formato yyyy-MM-dd")
            @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato yyyy-MM-dd")
            @RequestParam(name = "createdAtTo", required = false) LocalDate createdAtTo,

            @Parameter(description = "El campo sort debe ser 'id', 'name' o 'createdAt'")
            @Pattern(regexp = "^(id|name|createdAt)$", message = "La dirección debe ser 'name' o 'createdAt'")
            @RequestParam(name = "sort", required = false) String sort,

            @Parameter(description = "El campo direction debe ser 'ASC' o 'DESC'")
            @Pattern(regexp = "^(ASC|DESC)$", message = "La dirección debe ser 'ASC' o 'DESC'")
            @RequestParam(name = "direction", required = false) String direction
    ) {
        var pageable = ProfileFilterDto.builder()
                .page(page)
                .size(size)
                .name(name)
                .description(description)
                .state(state)
                .createdAtFrom(createdAtFrom)
                .createdAtTo(createdAtTo)
                .sort(sort)
                .direction(direction)
                .build();

        PageResponse<ProfileDto> profilePage = profileService.findAll(pageable);

        return ResponseEntity.ok(profilePage);
    }

}
