package com.ironman.pharmasales.old.expose.web;


import com.ironman.pharmasales.old.application.dto.user.AuthDto;
import com.ironman.pharmasales.old.application.dto.user.UserSecurityDto;
import com.ironman.pharmasales.old.application.service.UserService;
import com.ironman.pharmasales.old.shared.constant.StatusCode;
import com.ironman.pharmasales.old.shared.exception.DataNotFoundException;
import com.ironman.pharmasales.old.shared.exception.entity.ArgumentNotValidError;
import com.ironman.pharmasales.old.shared.exception.entity.GeneralError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirements(value = {})
@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @ApiResponse(responseCode = StatusCode.OK)
    @ApiResponse(
            responseCode = StatusCode.NOT_FOUND,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GeneralError.class)
            )
    )
    @ApiResponse(
            responseCode = StatusCode.BAD_REQUEST,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ArgumentNotValidError.class)
            )
    )
    @PostMapping
    public ResponseEntity<UserSecurityDto> auth(@Valid @RequestBody AuthDto authDto) throws DataNotFoundException {
        UserSecurityDto user = userService.login(authDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }


}
