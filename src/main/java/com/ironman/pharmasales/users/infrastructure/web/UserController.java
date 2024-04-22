package com.ironman.pharmasales.users.infrastructure.web;


import com.ironman.pharmasales.users.application.dto.user.UserCreateDto;
import com.ironman.pharmasales.users.application.dto.user.UserDto;
import com.ironman.pharmasales.users.application.service.UserService;
import com.ironman.pharmasales.shared.infrastructure.web.constant.StatusCode;
import com.ironman.pharmasales.shared.domain.exception.DataNotFoundException;
import com.ironman.pharmasales.shared.domain.exception.model.ArgumentNotValidError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirements(value = {})
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @ApiResponse(responseCode = StatusCode.CREATED)
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateDto userCreate) throws DataNotFoundException {
        UserDto user = userService.create(userCreate);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

}
